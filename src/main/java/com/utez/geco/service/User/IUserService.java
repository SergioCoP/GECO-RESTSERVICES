package com.utez.geco.service.User;

import com.utez.geco.model.User;

import java.util.List;

public interface IUserService {
    User findById(Long id);
    List<User> findAll();

}
