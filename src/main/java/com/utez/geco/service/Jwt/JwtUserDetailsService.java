package com.utez.geco.service.Jwt;

import ch.qos.logback.core.net.server.Client;
import com.utez.geco.model.Jwt.JwtUserDetails;
import com.utez.geco.model.User;
import com.utez.geco.repository.Role.RoleRepository;
import com.utez.geco.repository.User.UserRepository;
import com.utez.geco.service.User.UserServiceImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.List;

public class JwtUserDetailsService implements UserDetailsService {


    private UserRepository userRepository;

    private RoleRepository roleRepository;
    // ...

    @Override
    public UserDetails loadUserByUsername(final String username) {
        final User user = userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("User " + username + " not found"));
        final List<SimpleGrantedAuthority> roles = Collections.singletonList(
                new SimpleGrantedAuthority(roleRepository.findRoleByIdUser(user.getIdUser())));
        return new JwtUserDetails(user.getIdUser(), username, user.getUsername(), roles);
    }
}
