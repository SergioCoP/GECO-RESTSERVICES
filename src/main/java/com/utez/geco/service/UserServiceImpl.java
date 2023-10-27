package com.utez.geco.service;

import com.utez.geco.model.User;
import com.utez.geco.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl extends User {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAll(){return userRepository.findAll();}
    public User findByEmail(String email){return userRepository.findByEmail(email);}

    public List<User> findAllUsers(){return userRepository.findAllUsers();}
    public User findByEmailAndPassword(String email, String password){return userRepository.findByEmailAndPassword(email,password);}
    public User register(User user){return userRepository.save(user);}
}
