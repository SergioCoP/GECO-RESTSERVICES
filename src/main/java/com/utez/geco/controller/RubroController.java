package com.utez.geco.controller;


import com.utez.geco.model.Rubro;
import com.utez.geco.service.Rubro.RubroServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rubro")
@CrossOrigin(origins = "*")
public class RubroController {
    String msg = "";
    @Autowired
    private RubroServiceImpl rubroService;

    @GetMapping("/getAllRubros")
    @ResponseBody
    public List<Rubro> getAllRubros(){return rubroService.findAll();}

    @GetMapping("/getRubro")
    @ResponseBody
    public Rubro getRubroById(@RequestParam("idRubro") Long id){
        return rubroService.findById(id);
    }

    @PostMapping("/saveRubro")
    @ResponseBody
    public String createRubro(@RequestBody Rubro rubro){
        Rubro nrub = rubroService.register(rubro);
        msg = nrub != null ? "Register" : "NotRegistered";
        return msg;
    }

    @PutMapping("/updateRubro")
    @ResponseBody
    public String updateRubro(@RequestBody Rubro rubro){
        Rubro nrub = rubroService.update(rubro);
        msg = nrub != null ? "Update" : "NotUpdated";
        return msg;
    }

    @DeleteMapping("/deleteRubro")
    @ResponseBody
    public String deleteRubro(@RequestParam("idRubro") Long id){
        System.out.println(id);
        Rubro nrub = rubroService.findById(id);
        if(nrub != null){
            rubroService.delete(id);
            msg = "Deleted";
        }else{
            msg = "NotFound";
        }
        return msg;
    }



}
