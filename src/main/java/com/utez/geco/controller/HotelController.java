package com.utez.geco.controller;

import com.google.api.client.json.Json;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.utez.geco.DTO.UserHotel;
import com.utez.geco.model.Hotel;
import com.utez.geco.model.User;
import com.utez.geco.service.Hotel.HotelServiceImpl;
import com.utez.geco.service.Image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hotel")
@CrossOrigin(origins = "*")
public class HotelController {
    String msg = "";
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
    @Autowired
    private HotelServiceImpl hotelService;

    @GetMapping("/getAllHotels")
    @ResponseBody
    public ResponseEntity<?> getAllHotels(){
        Map<String, Object> map = new HashMap<>();
        map.put("msg","OK");
        map.put("data",hotelService.findAll());
        return new ResponseEntity<>(map,HttpStatus.OK);
    }


    @GetMapping("/getHotel")
    @ResponseBody
    public ResponseEntity<?> getHotelById(@RequestParam("idHotel")Long id){
        Map<String, Object> map = new HashMap<>();
        Hotel hotel = hotelService.findById(id);
        if(hotel != null){
            map.put("msg","OK");
            map.put("data",hotel);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }else{
            map.put("msg","NotFound");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/saveHotel")
    @ResponseBody
    public ResponseEntity<?> saveHotel(@RequestBody Hotel nhotel, @RequestParam("idUser")Long id){
        Map<String, Object> map = new HashMap<>();
        if(!containsMaliciusWord(nhotel.toString())){
            if(!new Gson().toJson(nhotel).equals("null")){
                if(new Gson().toJson(hotelService.findByName(nhotel.getName())).equals("null") ){
                    if(hotelService.register(nhotel)  != null){
                        Hotel sHot = hotelService.findByName(nhotel.getName());
                        if(sHot != null){
                            if(hotelService.assignHotelUser(sHot.getIdHotel(),id) >= 1){
                                map.put("msg","Register");
                                return new ResponseEntity<>(map, HttpStatus.CREATED);
                            }else{
                                map.put("msg","NotRegister");
                                return new ResponseEntity<>(map, HttpStatus.CONFLICT);
                            }
                        }else{
                            map.put("msg","NotFound");
                            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
                        }
                    }else{
                        map.put("msg","NotRegister");
                        return new ResponseEntity<>(map, HttpStatus.CONFLICT);
                    }
                }else{
                    map.put("msg","Exist");
                    return new ResponseEntity<>(map, HttpStatus.OK);
                }
            }else {
                map.put("msg","Empty");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
        }else{
            map.put("msg","BadWord");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }


    @PutMapping("/updateHotel")
    @ResponseBody
    public ResponseEntity<?> updateHotel(@RequestBody Hotel nhotel){
        Map<String, Object> map = new HashMap<>();
        if(!containsMaliciusWord(nhotel.toString())){
            if(!new Gson().toJson(nhotel).equals("null")){
                if(!new Gson().toJson(hotelService.findById(nhotel.getIdHotel())).equals("null")){
                     if(hotelService.updateAll(nhotel) != null){
                         map.put("msg", "Update");
                         return new ResponseEntity<>(map, HttpStatus.OK);
                     }else{
                         map.put("msg", "NotUpdate");
                         return new ResponseEntity<>(map, HttpStatus.CONFLICT);
                     }
                    //                    msg = hotelService.updateAll(nhotel) != null ? "Update" : "NotUpdated";
                }else{
                    map.put("msg", "NotFound");
                    return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
                }
            }else{
                map.put("msg", "Empty");
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            }
        }else{
            map.put("msg", "BadWord");
            return new ResponseEntity<>(map, HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/deleteHotel")
    @ResponseBody
    public ResponseEntity<?> deleteHotel(@RequestParam("idRoom") Long id){
        Map<String, Object> map = new HashMap<>();
        if(!new Gson().toJson(hotelService.findById(id)).equals("null")){
            hotelService.delete(id);
            map.put("msg","Deleted");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }else{
            map.put("msg","NotFound");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }
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
