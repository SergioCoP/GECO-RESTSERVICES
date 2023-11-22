package com.utez.geco.controller;

import com.utez.geco.DTO.Incidence.IncidenceUser;
import com.utez.geco.model.Incidence;
import com.utez.geco.service.Image.ImageService;
import com.utez.geco.service.Incidence.IncidenceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/incidence")
@CrossOrigin(origins = "*")
public class IncidenceController {
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
    private IncidenceServiceImpl incidenceService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/getAllIncidences")
    @ResponseBody
    public ResponseEntity<?> getAllIncidences(){
        Map<String, Object> map = new HashMap<>();
        map.put("msg","OK");
        map.put("data",incidenceService.findAll());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/getIncidenceByRoomIdentifier")
    @ResponseBody
    public ResponseEntity<?> getIncidenceByRoomIdentifer(@RequestParam("roomIdentifier")String roomIdentifier){
        Map<String, Object> map = new HashMap<>();
        if(!containsMaliciusWord(roomIdentifier)){
            List<IncidenceUser> incidenceUser = incidenceService.findByRoomIndentifier(roomIdentifier);
            if(incidenceUser.size() >= 1){
                map.put("msg","OK");
                map.put("data",incidenceUser);
                return new ResponseEntity<>(map, HttpStatus.OK);
            }else{
                map.put("msg","NotFound");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
        }else{
            map.put("msg","BadWord");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }

    }

    @GetMapping("/getIncidenceByRoomId")
    @ResponseBody
    public ResponseEntity<?> getIncidenceByRoomId(@RequestParam("idRoom")Long idRoom){
        Map<String, Object> map = new HashMap<>();
        List<IncidenceUser> incidenceUser = incidenceService.findByIdRoom(idRoom);
        if(incidenceUser.size() >= 1){
            map.put("msg","OK");
            map.put("data",incidenceUser);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }else{
            map.put("msg","NotFound");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    @GetMapping("/getIncidenceByIdIncidence")
    @ResponseBody
    public ResponseEntity<?> getIncidenceByIdIncidence(@RequestParam("idIncidence")Long idIncidence){
        Map<String, Object> map = new HashMap<>();
        IncidenceUser incidenceUser = incidenceService.findByidIncidence(idIncidence);
        if(incidenceUser != null){
            map.put("msg","OK");
            map.put("data",incidenceUser);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }else{
            map.put("msg","NotFound");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }



    @PutMapping("/updateStatus")
    @ResponseBody
    public ResponseEntity<?> updateStatusIncidence(@RequestParam("idIncidence")Long idIncidence,@RequestParam("status")int status){
        Map<String, Object> map = new HashMap<>();
            map.put("msg",incidenceService.updateStatus(idIncidence,status));
            return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping(value = "/saveIncidence")
    @ResponseBody
    public ResponseEntity<?> saveIncidence(@RequestPart("incidence") Incidence incidence,
                                           @RequestParam("file") MultipartFile multipartFile,
                                           @RequestParam("idUser")Long idUser,
                                           @RequestParam("idRoom")Long idRoom){
        System.out.println(incidence.getDescription() +  " -> " + idUser + " -> " + idRoom);
        Map<String, Object> map = new HashMap<>();
        if(!containsMaliciusWord(incidence.toString())){
            incidence.setImage(imageService.upload(multipartFile));
            incidence.setCreatedAt(new Date());
            Incidence sIn = incidenceService.save(incidence);
            if( sIn != null){
                if(incidenceService.assignIncidenceUser(idUser,sIn.getIdIncidence()) >= 1){
                    if(incidenceService.assignIncidenceRoom(idRoom,sIn.getIdIncidence()) >= 1){
                        map.put("msg","Register");
                        return new ResponseEntity<>(map, HttpStatus.OK);
                    }else{
                        map.put("msg","RoomNotAssigned");
                        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
                    }
                }else{
                    map.put("msg","IncidenceNotAssigned");
                    return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
                }
            }else {
                map.put("msg", "NotRegister");
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            }
        }else{
            map.put("msg","BadWord");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
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
