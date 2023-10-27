package com.utez.geco.controller;

import com.utez.geco.model.Person;
import com.utez.geco.model.User;
import com.utez.geco.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/registerUser")
    @ResponseBody
    public User registerUser(@RequestBody User newUser){
        return userService.register(newUser);
    }

    @GetMapping("/getUserByEmail")
    @ResponseBody
    public User findUserByEmail(@RequestParam(name = "email") String email){
        return userService.findByEmail(email);
    }

    @GetMapping("/findByEmailAndPassword")
    @ResponseBody
    public User findByEmailAndPassword(@RequestParam(name = "email")String email,@RequestParam(name = "password") String password){
        System.out.println(userService.findByEmailAndPassword(email,password));
        return userService.findByEmailAndPassword(email,password);
    }

    @GetMapping("/getUsers")
    @ResponseBody
    public List<User> getAllUsers(){
        return userService.findAll();
    }
}
