package com.utez.geco.SecurityConfig;

import ch.qos.logback.classic.helpers.MDCInsertingServletFilter;
import com.google.common.net.HttpHeaders;
import com.utez.geco.model.Jwt.JwtUserDetails;
import com.utez.geco.service.Jwt.JwtTokenService;
import com.utez.geco.service.Jwt.JwtUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private JwtTokenService jwtTokenService;
    private JwtUserDetailsService jwtUserDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // look for Bearer auth header
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        MDCInsertingServletFilter chain;
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = header.substring(7);

        final String username = jwtTokenService.validateTokenAndGetUsername(token);
        if (username == null) {
            // validation failed or token expired
            filterChain.doFilter(request, response);
            return;
        }

        // set user details on spring security context

        final JwtUserDetails userDetails = (JwtUserDetails) jwtUserDetailsService.loadUserByUsername(username);
        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // continue with authenticated user
        filterChain.doFilter(request, response);

    }
}
