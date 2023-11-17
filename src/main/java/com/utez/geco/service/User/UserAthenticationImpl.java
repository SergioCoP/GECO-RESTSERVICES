package com.utez.geco.service.User;

import com.utez.geco.DTO.SignupRequest;
import com.utez.geco.model.User;
import com.utez.geco.repository.User.UserRepository;
import com.utez.geco.service.Jwt.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAthenticationImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User signup(SignupRequest signupRequest){
        User user = new User();

        user.setEmail(signupRequest.getEmail());
        user.setIdPerson(signupRequest.getIdPerson());
        user.setIdRol(signupRequest.getIdRol());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        return userRepository.save(user);
    }
}
