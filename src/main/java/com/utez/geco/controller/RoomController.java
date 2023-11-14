package com.utez.geco.controller;


import com.utez.geco.model.Room;
import com.utez.geco.service.Room.RoomServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/room")
@CrossOrigin(origins = "*")
public class RoomController {
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
    private RoomServiceImpl roomService;

    @GetMapping("/getAllRooms")
    @ResponseBody
    public ResponseEntity<?> getAllRooms(){
        Map<String, Object> map = new HashMap<>();
        map.put("msg","OK");
        map.put("data",roomService.findAll());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/getRoom")
    @ResponseBody
    public ResponseEntity<?> getRoomById(@RequestParam("idRoom") Long id){
        Map<String, Object> map = new HashMap<>();
        Room room = roomService.findById(id);
        if(room != null){
            map.put("msg","OK");
            map.put("data",room);
            return new ResponseEntity<>(map,HttpStatus.OK);
        }else{
            map.put("msg","NotFound");
            return new ResponseEntity<>(map,HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/saveRoom")
    @ResponseBody
    public ResponseEntity<?> createRoom(@RequestBody Room nrom,
            @RequestParam("nameInit")String nameInit,
            @RequestParam("numInit")int numInit,
            @RequestParam("numHab")int numHabitaciones) {
        int nrrooms =0;
        Map<String, Object> map = new HashMap<>();
        if(!containsMaliciusWord(nrom.toString())){
            for (int i = 0; i < numHabitaciones; i++) {
                nrom.setIdRoom((long) (i+1));
                nrom.setIdentifier(nameInit + (numInit+(i+1)));
                Room room = roomService.register(nrom);
                if(nrom != null){
                    nrrooms+=1;
                }
            }
            if( nrrooms == numHabitaciones){
                map.put("msg","Register");
                return new ResponseEntity<>(map,HttpStatus.OK);
            }else{
                map.put("msg","NotRegister");
                return new ResponseEntity<>(map,HttpStatus.CONFLICT);
            }
        }else{
            map.put("msg","BadWord");
            return new ResponseEntity<>(map,HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/updateRoom")
    @ResponseBody
    public ResponseEntity<?> updateRoom(@RequestBody Room uRoom){
        Map<String, Object> map = new HashMap<>();
        if(containsMaliciusWord(uRoom.toString())){
            Room room =roomService.update(uRoom);
            if(room != null){
                map.put("msg","Update");
                return new ResponseEntity<>(map,HttpStatus.OK);
            }else{
                map.put("msg","NotUpdate");
                return new ResponseEntity<>(map,HttpStatus.CONFLICT);
            }
        }else{
            map.put("msg","BadWord");
            return new ResponseEntity<>(map,HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/deleteRoom")
    @ResponseBody
    public ResponseEntity<?> deleteRoom(@RequestParam("idRoom") Long id){
        Map<String, Object> map = new HashMap<>();
        if(roomService.findById(id)!= null){
            roomService.delete(id);
            map.put("msg","Delete");
            return new ResponseEntity<>(map,HttpStatus.OK);
        }else{
            map.put("msg","NotDelete");
            return new ResponseEntity<>(map,HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/assignUserRoom")
    @ResponseBody
    public ResponseEntity<?> assignUserToRoom(@RequestParam("idUser") Long idUser,@RequestParam("idRoom") Long idRoom){
        Map<String, Object> map = new HashMap<>();
        map.put("msg",roomService.assignUserToRoom(idUser,idRoom));
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @PutMapping("/reviewRoom")
    @ResponseBody
    public ResponseEntity<?> reviewRoom(@RequestParam("status") int status,@RequestParam("idRoom") Long idRoom){
        Map<String, Object> map = new HashMap<>();
        map.put("msg",roomService.reviewRoom(status,idRoom));
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
