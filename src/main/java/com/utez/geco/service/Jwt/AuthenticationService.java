package com.utez.geco.service.Jwt;

import com.utez.geco.DTO.AuthenticationRequest;
import com.utez.geco.DTO.AuthenticationResponse;
import com.utez.geco.DTO.SignupRequest;
import com.utez.geco.model.User;
import com.utez.geco.repository.User.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.HashMap;
import java.util.Map;



public interface AuthenticationService {

    User signup(SignupRequest signupRequest);
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private JwtTokenService jwtService;
//
//    public AuthenticationResponse login(AuthenticationRequest authRequest) {
//
//        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                authRequest.getUsername(), authRequest.getPassword()
//        );
//
//        authenticationManager.authenticate(authToken);
//
//        User user = userRepository.findByEmail(authRequest.getUsername()).get();
//
//        String jwt = jwtService.generateToken(user, generateExtraClaims(user));
//
//        return new AuthenticationResponse(jwt);
//    }
//
//    private Map<String, Object> generateExtraClaims(User user) {
//
//        Map<String, Object> extraClaims = new HashMap<>();
//        extraClaims.put("name", user.getUsername());
//        extraClaims.put("role", user.getIdRol().getName());
//        extraClaims.put("permissions", user.getAuthorities());
//
//        return extraClaims;
//    }
}
