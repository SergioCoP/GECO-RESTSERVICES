package com.utez.geco.controller;


import com.google.gson.JsonObject;
import com.utez.geco.DTO.Room.RoomsDTO;
import com.utez.geco.DTO.Room.RoomCategoryDTO;
import com.utez.geco.DTO.Rubro.RubroCategoryDTO;
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

    @PostMapping("/assignRubroToCategory")
    @ResponseBody
    public ResponseEntity<?> assignRubroToCategory(@RequestBody RubroCategoryDTO rubCat){
        Map<String, Object> map = new HashMap<>();
        int nAsigns = 0;
        int rubSize = rubCat.getIdRubro().size();
        int rubsAss = 0;

        if(roomService.findCategoryById(rubCat.getIdCategory()) != null) {
            if (rubCat.getIdRubro().size() > 0) {
                for (idRubro rubro : rubCat.getIdRubro()) {
                    if (rubroService.assignRubroCategory(rubro.getIdRubro(), rubCat.getIdCategory()) >= 1) {
                        rubsAss++;
                    }
                }
                if (rubsAss == rubSize) {
                    map.put("msg", "Assigned");
                }
            } else {
                map.put("msg", "EmptyRubros");
            }
        }else{
            map.put("msg","CategoryNotFound");
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
