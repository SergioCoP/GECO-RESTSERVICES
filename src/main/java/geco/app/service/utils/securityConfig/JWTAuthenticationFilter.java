package geco.app.service.utils.securityConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import geco.app.service.DTO.AuthCredentialsDTO;
import geco.app.service.utils.userDetails.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        AuthCredentialsDTO auth = new AuthCredentialsDTO();
        try {
            auth = new ObjectMapper().readValue(request.getReader(), AuthCredentialsDTO.class);
        } catch(IOException e) {

        }

        UsernamePasswordAuthenticationToken usernameApi = new UsernamePasswordAuthenticationToken(
                auth.getUser(),
                auth.getPassword(),
                Collections.emptyList()
        );

        return getAuthenticationManager().authenticate(usernameApi);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        String token = TokenUtils.createToken(userDetails.getUsername(), userDetails.getEmail());

        response.addHeader("Authorzation", "Bearer " + token);
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }
}
