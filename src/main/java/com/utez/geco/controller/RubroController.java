package com.utez.geco.controller;


import com.google.gson.JsonObject;
import com.utez.geco.DTO.Room.RoomsDTO;
import com.utez.geco.DTO.Rubro.RubroGetDTO;
import com.utez.geco.model.Rubro;
import com.utez.geco.DTO.Rubro.idRubro;
import com.utez.geco.DTO.Rubro.RemoveRubroToRoom;
import com.utez.geco.service.Rubro.RubroServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import com.utez.geco.service.Room.RoomServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/rubro")
@CrossOrigin(origins = "*")
public class RubroController {
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
    private RubroServiceImpl rubroService;
    @Autowired
    private RoomServiceImpl roomService;

    @GetMapping("/getAllRubros")
    @ResponseBody
    public ResponseEntity<?> getAllRubros(){
        Map<String, Object> map = new HashMap<>();
        map.put("msg","OK");
        map.put("data",rubroService.findAll());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/getRubro")
    @ResponseBody
    public ResponseEntity<?> getRubroById(@RequestParam("idRubro") Long id){
        Map<String, Object> map = new HashMap<>();
        Rubro rubro = rubroService.findById(id);
        if(rubro != null){
            map.put("msg","OK");
            map.put("data",rubro);
            return new ResponseEntity<>(map,HttpStatus.OK);
        }else{
            map.put("msg","NotFound");
            return new ResponseEntity<>(map,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getRubroByName")
    @ResponseBody
    public ResponseEntity<?> getRubroByName(@RequestParam("nameRubro") String name){
        Map<String, Object> map = new HashMap<>();
        RubroGetDTO rubro = rubroService.findByName(name);
        if(rubro != null){
            map.put("msg","OK");
            map.put("data",rubro);
            return new ResponseEntity<>(map,HttpStatus.OK);
        }else{
            map.put("msg","NotFound");
            return new ResponseEntity<>(map,HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/saveRubro")
    @ResponseBody
    public ResponseEntity<?> createRubro(@RequestBody Rubro rubro){
        Map<String, Object> map = new HashMap<>();
        if(!containsMaliciusWord(rubro.toString())){
            if(rubroService.findByName(rubro.getDescription()) == null){
                Rubro nrub = rubroService.register(rubro);
                if(rubro != null){
                    map.put("msg","Register");
                    map.put("data",nrub);
                    return new ResponseEntity<>(map,HttpStatus.CREATED);
                }else{
                    map.put("msg","NotRegister");
                    return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
                }
            }else{
                map.put("msg","Exist");
                return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
            }
        }else{
            map.put("msg","BadWord");
            return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/updateRubro")
    @ResponseBody
    public ResponseEntity<?> updateRubro(@RequestBody Rubro rubro){
        Map<String, Object> map = new HashMap<>();
        if(containsMaliciusWord(rubro.toString())){
            if(rubroService.findByName(rubro.getDescription()) == null){
                Rubro nrub = rubroService.update(rubro);
                if(nrub != null){
                    map.put("msg","Update");
                    return new ResponseEntity<>(map,HttpStatus.CREATED);
                }else{
                    map.put("msg","NotUpdate");
                    return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
                }
            }else{
                map.put("msg","Exist");
                return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
            }
        }else{
            map.put("msg","BadWord");
            return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteRubro")
    @ResponseBody
    public ResponseEntity<?> deleteRubro(@RequestParam("idRubro") Long id){
        Map<String, Object> map = new HashMap<>();
        Rubro nrub = rubroService.findById(id);
        if(nrub != null){
            rubroService.delete(id);
            map.put("msg","Delete");
            return new ResponseEntity<>(map,HttpStatus.OK);
        }else{
            map.put("msg","NotFound");
            return new ResponseEntity<>(map,HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/assignRubroToRoom")
    @ResponseBody
    public ResponseEntity<?> assignRubroToRoom(@RequestBody RemoveRubroToRoom asRubro){
        Map<String, Object> map = new HashMap<>();
        int nAsigns = 0;
        int romsSize = 0;
        int romsAss = 0;
        if(!Objects.equals(asRubro.getCategory(), "") || asRubro.getCategory() != null){
            List<RoomsDTO> rooms = roomService.findByCategory(asRubro.getCategory());
            romsSize = rooms.size();
            if(rooms.size() > 0){
                for (RoomsDTO room : rooms) {
                    for (idRubro rubro: asRubro.getIdRubro()) {
                        if(rubroService.assignRubroToRoom(room.getIdRoom(),rubro.getIdRubro() ) >= 1){
                            nAsigns++;
                        }
                        System.out.println("Rubros asignados a room: " + nAsigns);
                        if(nAsigns == asRubro.getIdRubro().size()){
                            nAsigns = 0;
                            romsAss++;
                        }
                }
                    System.out.println("Roms asignadas: " + romsAss);
                }
                if(romsAss == romsSize ){
                    map.put("msg","Assigned");
                }else{
                    map.put("msg","NotAllAssigned");
                }
            }else{
                map.put("msg","NotFound");
            }

        }else{
            map.put("msg","EmptyData");
        }
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @DeleteMapping("/removeRubrosFromRoom")
    @ResponseBody
    public ResponseEntity<?> removeRubrosFromRoom(@RequestParam("category")String category){
        Map<String, Object> map = new HashMap<>();
        int roomsSize = 0,nRemove = 0,notRemove = 0;
        List<RoomsDTO> rooms;
        if(!Objects.equals(category,"") || !Objects.equals(category,null)){
            rooms = roomService.findByCategory(category);
            roomsSize = rooms.size();
            if(roomsSize > 0){
                for (RoomsDTO room:rooms) {
                    if(rubroService.validateRoomWithRubros(room.getIdRoom()).equals("true")){
                        if(rubroService.removeRubrosFromRoom(room.getIdRoom()) >= 1){
                            nRemove++;
                        }
                    }else{
                        notRemove ++;
                    }
                }
                if(nRemove + notRemove == roomsSize){
                    map.put("msg","removed");
                }else{
                    map.put("msg","NotAllRemoved");
                }
            }else{
                map.put("msg","RoomsNotFound");
            }
        }else{
            map.put("msg","EmptyData");
        }
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @PutMapping("/changueState")
    @ResponseBody
    public ResponseEntity<?> changeState(@RequestParam("idRubro")Long idRubro,@RequestParam("status")int state){
        Map<String, Object> map = new HashMap<>();
            if(!Objects.equals(rubroService.findById(idRubro),null) ){
                if(rubroService.changueState(idRubro,state) >=1){
                    map.put("msg","Changed");
                }else{
                    map.put("msg","NotChanged");
                }
            }else{
                map.put("msg","NotFound");
            }
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @DeleteMapping("/removeRubroFromRoom")
    @ResponseBody
    public ResponseEntity<?> removeRubroFromRoom(@RequestParam("idRoom")Long idRoom,@RequestParam("idRubro") Long idRubro){
        Map<String, Object> map = new HashMap<>();
        if(idRoom != null || idRoom != 0 && idRubro != null || idRubro != 0) {
            if (roomService.findById(idRoom) != null) {
                if(rubroService.validateRoomWithRubros(idRoom).equals("true")){
                    if (rubroService.removeRubroFromRoom(idRoom,idRubro) >= 1) {
                        map.put("msg", "Removed");
                    } else {
                        map.put("msg", "NotRemoved");
                    }
                }else{
                    map.put("msg","RoomWithoutRubros");
                }
            } else {
                map.put("msg", "RoomNotFound");
            }
        }else{
            map.put("msg", "RoomRubroEmpty");
        }
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
