package com.utez.geco.SecurityConfig;


import com.utez.geco.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final String[] PATHS = new String[]{"/api/user/login", "/api/user/hotel", "/api/image-upload/hotel","/api/image-upload"};

    private final JWTAuthorizationFilter jwtAuthorizationFilter;
    private final UserDetailsServiceImpl userService;

    //Validar al usuario   *,
    private static final String AUTHENTICATE_USER = """
            select email as username, password, true as enabled from user
            where ? in (substring_index(concat(email),'~', 1),
            substring_index(concat(email),'~', -1));""";
    //Obtener  los permisos del usuario
    private  static final  String  AUTHORIZATION_USER = """
            select u.email ,r.name as authority from user u
            join user_rol ur on u.id_user = ur.id_user
            join rol r on ur.id_rol = r.id_rol where ?
            in (substring_index(concat(email),'~', 1),
            substring_index(concat(email),'~', -1));""";

    private final DataSource dataSource;


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.jdbcAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .dataSource(dataSource)
                .usersByUsernameQuery(AUTHENTICATE_USER)
                .authoritiesByUsernameQuery(AUTHORIZATION_USER);
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,AuthenticationManager authenticationManager) throws Exception{
        JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter();
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers(PATHS).permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(
                        jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class
                ).addFilter(jwtAuthenticationFilter);
        return http.build();

    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }


}
