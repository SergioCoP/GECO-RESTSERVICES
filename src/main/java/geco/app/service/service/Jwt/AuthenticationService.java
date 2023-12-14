package geco.app.service.service.Jwt;

import com.utez.geco.DTO.JwtAuthenticationResponse;
import com.utez.geco.DTO.SigninRequest;
import com.utez.geco.DTO.SignupRequest;
import geco.app.service.model.User;

public interface AuthenticationService {

    User signup(SignupRequest signupRequest);
    JwtAuthenticationResponse signin(SigninRequest signinRequest);
}
