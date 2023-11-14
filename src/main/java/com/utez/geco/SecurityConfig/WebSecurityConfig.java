package com.utez.geco.SecurityConfig;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    String[] paths = new String[]{
            "/user/login","/user/registerUser"
    };

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
            return http
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(authReq ->
                            authReq
                                    .requestMatchers(paths).permitAll()
                                    .anyRequest().authenticated()
                    )
                    .formLogin(Customizer.withDefaults())
                    .build();
    }

}
