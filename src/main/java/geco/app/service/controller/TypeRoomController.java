package geco.app.service.controller;

import geco.app.service.model.TypeRoom;
import geco.app.service.service.HotelService;
import geco.app.service.service.TypeRoomService;
import geco.app.service.utils.CustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/type-room")
@CrossOrigin
public class TypeRoomController {
    @Autowired
    private TypeRoomService trs;
    @Autowired
    private HotelService hs;
    private CustomService cs = new CustomService();
    private HashMap<String, Object> response;

    @GetMapping("")
    private ResponseEntity<?> findAll() {
        response = new HashMap<>();
        List<TypeRoom> typeRoomList = trs.findAll();

        if(typeRoomList.isEmpty()) {
            response.put("status", HttpStatus.OK);
            response.put("message", "Aún no hay registros");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        response.put("status", HttpStatus.OK);
        response.put("message", "Operación exitosa");
        response.put("data", typeRoomList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(name = "id") long id) {
        response = new HashMap<>();
        TypeRoom found = trs.findById(id);

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

    @GetMapping("/hotel/{id}")
    public ResponseEntity<?> findByIdHotel(@PathVariable(name = "id") long id) {
        response = new HashMap<>();
        if(hs.findById(id) == null) {
            response.put("status", HttpStatus.NOT_FOUND);
            response.put("message", "No se encontró el hotel");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        List<TypeRoom> typeRoomList = trs.findByIdHotel(id);

        if(typeRoomList.isEmpty()) {
            response.put("status", HttpStatus.OK);
            response.put("message", "Aún no hay registros");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("status", HttpStatus.OK);
            response.put("message", "Operación exitosa");
            response.put("data", typeRoomList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody TypeRoom typeRoom) {
        response = new HashMap<>();
        if(!cs.checkBlacklists(typeRoom.toString())) {
            if(hs.findById(typeRoom.getIdHotel().getIdHotel()) != null) {
                if(trs.save(typeRoom)) {
                    long idTypeRoom = trs.findLastId();
                    response.put("status", HttpStatus.CREATED);
                    response.put("idPerson", idTypeRoom);
                    response.put("message", "Se creó el registro");
                    return new ResponseEntity<>(response, HttpStatus.CREATED);
                }
            } else {
                response.put("status", HttpStatus.NOT_FOUND);
                response.put("message", "No se encontró el hotel");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }

        response.put("status", HttpStatus.BAD_REQUEST);
        response.put("message", "No se creó el registro");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("")
    public ResponseEntity<?> update(@RequestBody TypeRoom typeRoom) {
        response = new HashMap<>();
        if(!cs.checkBlacklists(typeRoom.toString())) {
            if(trs.findById(typeRoom.getIdTypeRoom()) != null) {
                if(trs.update(typeRoom)) {
                    response.put("status", HttpStatus.OK);
                    response.put("message", "Se modificó el registro");
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
            } else {
                response.put("status", HttpStatus.NOT_FOUND);
                response.put("message", "No se encontró el registro");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }

        response.put("status", HttpStatus.BAD_REQUEST);
        response.put("message", "No se modificó el registro");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
