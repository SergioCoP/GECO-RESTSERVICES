package com.utez.geco.controller;

import com.utez.geco.model.Incidence;
import com.utez.geco.service.IncidenceService;
import com.utez.geco.service.RoomService;
import com.utez.geco.service.UserService;
import com.utez.geco.utils.CustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
@CrossOrigin(origins = "http://52.1.80.209:3000", methods = {RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.POST},allowedHeaders = {HttpHeaders.CONTENT_TYPE,HttpHeaders.AUTHORIZATION})
@RestController
@RequestMapping("/api/incidence")
public class IncidenceController {
    @Autowired
    private IncidenceService is;
    @Autowired
    private UserService us;
    @Autowired
    private RoomService rs;
    private final CustomService cs = new CustomService();
    private HashMap<String, Object> response;

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        response = new HashMap<>();
        List<Incidence> incidenceList = is.findAll();

        if(incidenceList.isEmpty()) {
            response.put("status", HttpStatus.OK);
            response.put("message", "Aún no hay registros");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        response.put("status", HttpStatus.OK);
        response.put("message", "Operación exitosa");
        response.put("data", incidenceList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") long id) {
        response = new HashMap<>();
        Incidence found = is.findById(id);

        if(found == null) {
            response.put("status", HttpStatus.NOT_FOUND);
            response.put("message", "No se encontró el registro");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("status", HttpStatus.OK);
        response.put("message", "Se encontró el registro");
        response.put("data", found);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/room/{id}")
    public ResponseEntity<?> findAllByRoom(@PathVariable("id") long idRoom) {
        response = new HashMap<>();
        List<Incidence> incidenceList = is.findByRoom(idRoom);

        if(incidenceList.isEmpty()) {
            response.put("status", HttpStatus.OK);
            response.put("message", "Aún no hay registros");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        response.put("status", HttpStatus.OK);
        response.put("message", "Operación exitosa");
        response.put("data", incidenceList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/hotel/{id}")
    public ResponseEntity<?> findAllByHotel(@PathVariable("id") long idHotel) {
        response = new HashMap<>();
        List<Incidence> incidenceList = is.findByRoom(idHotel);

        if(incidenceList.isEmpty()) {
            response.put("status", HttpStatus.OK);
            response.put("message", "Aún no hay registros");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        response.put("status", HttpStatus.OK);
        response.put("message", "Operación exitosa");
        response.put("data", incidenceList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Incidence incidence) {
        response = new HashMap<>();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String dis = sdf.format(date);
        incidence.setDiscoveredOn(dis);
        boolean usr = false, rm = false;

        usr = us.findById(incidence.getIdUser().getIdUser()) == null;
        rm = rs.findById(incidence.getIdRoom().getIdRoom()) == null;

        if(usr || rm) {
            response.put("status", HttpStatus.NOT_FOUND);
            response.put("message", "No se encontró " + (usr ? "el usuario" : "el cuarto"));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        if(!cs.checkBlacklists(incidence.toString())) {
            if(is.save(incidence)) {
                response.put("status", HttpStatus.OK);
                response.put("message", "Se creó el registro");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }

        response.put("status", HttpStatus.BAD_REQUEST);
        response.put("message", "No se creó el registro");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long id) {
        response = new HashMap<>();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String solv = sdf.format(date);

        if(is.findById(id) != null) {
            if(is.changeStatus(solv, id)) {
                response.put("status", HttpStatus.OK);
                response.put("message", "Se modificó el registro");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("status", HttpStatus.BAD_REQUEST);
                response.put("message", "No se creó el registro");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } else {
            response.put("status", HttpStatus.NOT_FOUND);
            response.put("message", "No se encontró el registro");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
