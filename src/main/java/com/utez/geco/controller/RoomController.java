package com.utez.geco.controller;


import com.utez.geco.model.Room;
import com.utez.geco.service.Room.RoomServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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

    @GetMapping("/getRoom")
    @ResponseBody
    public Room getRoomById(@RequestParam("idRoom") Long id){
        return roomService.findById(id);
    }

    @PostMapping("/saveRoom")
    @ResponseBody
    public String createRoom(@RequestBody Room nrom){
        msg =roomService.register(nrom) != null ?  "Register" : "NotRegistered";
        return msg;
    }

    @PutMapping("/updateRoom")
    @ResponseBody
    public String updateRoom(@RequestBody Room uRoom){
        msg = roomService.update(uRoom) != null ? "Update" : "NotUpdated";
        return msg;
    }

    @DeleteMapping("/deleteRoom")
    @ResponseBody
    public String deleteRoom(@RequestParam("idRoom") Long id){
        if(roomService.findById(id)!= null){
            roomService.delete(id);
            return "Deleted";
        }else{
            return "NotFound";
        }
    }

    @PostMapping("/assignUserRoom")
    @ResponseBody
    public String assignUserToRoom(@RequestParam("idUser") Long idUser,@RequestParam("idRoom") Long idRoom){
        return roomService.assignUserToRoom(idUser,idRoom);
    }

    @PutMapping("/reviewRoom")
    @ResponseBody
    public String reviewRoom(@RequestParam("status") int status,@RequestParam("idRoom") Long idRoom){
        return roomService.reviewRoom(status,idRoom);
    }
}
