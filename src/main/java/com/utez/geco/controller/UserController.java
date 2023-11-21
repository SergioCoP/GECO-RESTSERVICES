package com.utez.geco.controller;


import com.google.gson.Gson;
import com.utez.geco.DTO.User.UsersDTO;
import com.utez.geco.model.User;
import com.utez.geco.service.User.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserController {

    String[] blacklist = {";", "@@",
            "SELECT", "select", "script>", "<script", "UPDATE",
            "update", "DELETE", "delete", "input", "button",
            "div", "html", "char", "varchar", "nvarchar", "hooks.js",
            "int", "integer", "String", "sys", "sysobjects",
            "sysobject",".js"};

    String[] blacklist2 = {"@@", "SELECT", "select", "script>", "<script", "UPDATE",
            "update", "DELETE", "delete", "input", "button",
            "div", "html", "char", "varchar", "nvarchar", "hooks.js",
            "int", "integer", "String", "sys", "sysobjects",
            "sysobject",".js"};
    String msg = "";
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/registerUser")
    @ResponseBody
    public ResponseEntity<?> registerUser(@RequestBody User user){
        Map<String, Object> map = new HashMap<>();
        if(!containsMaliciusWord(user.toString())){
            if(new Gson().toJson(userService.findByEmail(user.getEmail())).equals("null")){
                User nUser = userService.register(user);
                if(nUser != null){
                    map.put("msg","Register");
                    map.put("data",nUser);
                    return new ResponseEntity<>(map, HttpStatus.CREATED);
                }else{
                    map.put("msg", "NotExist");
                    return new ResponseEntity<>(map, HttpStatus.CONFLICT);
                }
            }else{
                map.put("msg", "ExistEmail");
                return new ResponseEntity<>(map, HttpStatus.CONFLICT);
            }
        }else{
            map.put("msg","BadWord");
            return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/getUserByEmail")
    @ResponseBody
    public ResponseEntity<?> findUserByEmail(@RequestParam(name = "email") String email) {
        Map<String, Object> map = new HashMap<>();
        if(!containsMaliciusWord(email)){

            UsersDTO nUser = userService.findByEmail(email);
                if(!new Gson().toJson(nUser).equals("null")){
                    map.put("msg", "OK");
                    map.put("data",nUser);
                    return new ResponseEntity<>(map, HttpStatus.OK);
                }else{
                    map.put("msg", "FAIL");
                    return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
                }
        }else{
            map.put("msg","BadWord");
            return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/getUserById")
    @ResponseBody
    public ResponseEntity<?> findUserById(@RequestParam(name = "idUser") Long id) {
        Map<String, Object> map = new HashMap<>();
        UsersDTO nUser = userService.findById(id);
        if(nUser != null){
            map.put("msg","OK");
            map.put("data",nUser);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }else{
            map.put("msg", "NotExist");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getUsersByRol")
    @ResponseBody
    public ResponseEntity<?> findUsersByRol(@RequestParam(name = "rolName") String rolName) {
        Map<String, Object> map = new HashMap<>();
        if(!containsMaliciusWord(rolName)){
            map.put("msg","OK");
            map.put("data",userService.findUsersByRol(rolName));
            return new ResponseEntity<>(map, HttpStatus.OK);
        }else{
            map.put("msg","BadWord");
            return new ResponseEntity<>(map, HttpStatus.CONFLICT);
        }
    }

    @PostMapping ("/login")
    @ResponseBody
    public ResponseEntity<?> findByEmailAndPassword(@RequestParam(name = "email")String email,@RequestParam(name = "password") String password){
        Map<String, Object> map = new HashMap<>();
        if(!containsMaliciusWord(email) || containsMaliciusWord(password)){
            User nUser = userService.findByEmailAndPassword(email,password);
            if(!new Gson().toJson(nUser).equals("null")){
                map.put("msg","Loged");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }else{
                map.put("msg","NotExist");
                return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
            }

        }else{
            map.put("msg","BadWord");
            return new ResponseEntity<>(map, HttpStatus.CONFLICT);
        }

    }


    @GetMapping("/getUsers")
    @ResponseBody
    public ResponseEntity<?> getAllUsers(){
        Map<String, Object> map = new HashMap<>();
        List<User> users = userService.findAll();
        map.put("msg","OK");
        map.put("data",users);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    private boolean containsMaliciusWord(String texto) {
        for (String palabra : blacklist) {
            if (texto.toLowerCase().contains(palabra.toLowerCase())) {
                return true;
            }
        }
        for (String palabra : blacklist2) {
            if (texto.toLowerCase().contains(palabra.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
