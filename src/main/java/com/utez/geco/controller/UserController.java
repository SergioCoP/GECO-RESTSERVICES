package com.utez.geco.controller;

import com.utez.geco.model.User;
import com.utez.geco.service.User.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {
    String msg = "";
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/registerUser")
    @ResponseBody
    public String registerUser(@RequestBody User newUser){
        if(userService.findByEmail(newUser.getEmail()) == null){
            User nUser = userService.register(newUser);
            if(nUser != null){
                msg = "Register";
            }else{
                msg = "NotRegister";
            }
        }else{
            msg = "Exist";
        }
        return msg;
    }

    @GetMapping("/getUserByEmail")
    @ResponseBody
    public User findUserByEmail(@RequestParam(name = "email") String email) {
        return userService.findByEmail(email);
    }

    @GetMapping("/login")
    @ResponseBody
    public boolean findByEmailAndPassword(@RequestParam(name = "email")String email,@RequestParam(name = "password") String password){
        User nUser = userService.findByEmailAndPassword(email,password);
        return nUser != null;
    }

    @GetMapping("/getUsers")
    @ResponseBody
    public List<User> getAllUsers(){
        return userService.findAll();
    }


}
