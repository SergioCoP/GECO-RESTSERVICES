package com.utez.geco.service.User;

import com.utez.geco.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService {
    User findById(Long id);
    List<User> findAll();

    UserDetailsService userDetailsService();
}
