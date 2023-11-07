package com.utez.geco.controller;


import com.utez.geco.model.Room;
import com.utez.geco.service.RoomServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
@CrossOrigin(origins = "*")
public class RoomController {
    String msg = "";
    @Autowired
    private RoomServiceImpl roomService;

    @GetMapping("/getAllRooms")
    @ResponseBody
    public List<Room> getAllRooms(){
    return roomService.findAll();
    }

    @GetMapping("/getRoom/{id}")
    @ResponseBody
    public Room getRoomById(@PathVariable("id") Long id){
        return roomService.findById(id);
    }

    @PostMapping("/saveRoom")
    @ResponseBody
    public String createRooom(@RequestBody Room nrom){
        Room nRom = roomService.register(nrom);
        msg = nRom != null ?  "Register" : "NotRegistered";
        return msg;
    }

    @PutMapping("/upateRoom")
    @ResponseBody
    public String updateRoom(@RequestBody Room uRoom){
        Room uRom = roomService.update(uRoom);
        msg = uRom != null ? "Update" : "NotUpdated";
        return msg;
    }

    @DeleteMapping("/deleteRoom/{id}")
    @ResponseBody
    public String deleteRoom(@PathVariable("id") Long id){
        Room rom = roomService.findById(id);
        if(rom != null){
            roomService.delete(id);
            return "Deleted";
        }else{
            return "NotFound";
        }
    }
}
