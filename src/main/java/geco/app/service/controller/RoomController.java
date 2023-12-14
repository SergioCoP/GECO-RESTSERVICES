package geco.app.service.controller;

import geco.app.service.DTO.RmChgSttsDTO;
import geco.app.service.model.Room;
import geco.app.service.service.HotelService;
import geco.app.service.service.RoomService;
import geco.app.service.service.TypeRoomService;
import geco.app.service.service.UserService;
import geco.app.service.utils.CustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/room")
public class RoomController {
    @Autowired
    private RoomService rs;
    @Autowired
    private UserService us;
    @Autowired
    private HotelService hs;
    @Autowired
    private TypeRoomService trs;
    private final CustomService cs = new CustomService();
    private HashMap<String, Object> response;

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        response = new HashMap<>();
        List<Room> roomList = rs.findAll();

        if(roomList.isEmpty()) {
            response.put("status", HttpStatus.OK);
            response.put("message", "Aún no hay registros");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        response.put("status", HttpStatus.OK);
        response.put("message", "Operación exitosa");
        response.put("data", roomList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(name = "id") long id) {
        response = new HashMap<>();
        Room found = rs.findById(id);

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
    public ResponseEntity<?> findByHotel(@PathVariable(name = "id") long id) {
        response = new HashMap<>();
        List<Room> roomList = null;
        if(hs.findById(id) != null) {
            roomList = rs.findByHotel(id);
            if(roomList.isEmpty()) {
                response.put("status", HttpStatus.OK);
                response.put("message", "Aún no hay registros");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } else {
            response.put("status", HttpStatus.NOT_FOUND);
            response.put("message", "No se ecnontró el hotel");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("status", HttpStatus.OK);
        response.put("message", "Operación exitosa");
        response.put("data", roomList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Room room) {
        response = new HashMap<>();
        if(rs.findAll().isEmpty()) {
            room.setRoomNumber(1);
        } else {
            room.setRoomNumber(rs.findByHotel(room.getIdHotel().getIdHotel()).size() + 1);
        }
        room.setStatus(1);

        if(!cs.checkBlacklists(room.toString())) {
            if(rs.save(room)) {
                long idRoom = rs.returnLastId();
                response.put("status", HttpStatus.CREATED);
                response.put("idRoom", idRoom);
                response.put("message", "Se creó el registro");
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
        }

        response.put("status", HttpStatus.BAD_REQUEST);
        response.put("message", "No se creó el registro");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("")
    public ResponseEntity<?> update(@RequestBody Room room) {
        response = new HashMap<>();
        boolean u1 = false, u2 = false, tr = false, htl = false, rm = false;

        //Checa que existan los usuarios
        if(room.getFirstIdUser() != null) {
            u1 = us.findById(room.getFirstIdUser().getIdUser()) == null;
        }

        if(room.getSecondIdUser() != null) {
            u2 = us.findById(room.getSecondIdUser().getIdUser()) == null;
        }

        //Checa que exista el hotel y el tipo de cuarto
        tr = trs.findById(room.getIdTypeRoom().getIdTypeRoom()) == null;
        htl = hs.findById(room.getIdHotel().getIdHotel()) == null;
        rm = rs.findById(room.getIdRoom()) == null;

        if(rm || u1 || u2 || tr || htl) {
            response.put("status", HttpStatus.NOT_FOUND);
            response.put("message", "No se encontró " + (rm ? "el cuarto" : u1 || u2 ? "a uno de los usuarios" : tr ? "el tipo de cuarto" : "el hotel"));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            if(rs.update(room)) {
                response.put("status", HttpStatus.OK);
                response.put("message", "Se actualizó el registro");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("status", HttpStatus.BAD_REQUEST);
                response.put("message", "No se actualizó el registro");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        }
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") long id, @RequestBody RmChgSttsDTO status) {
        response = new HashMap<>();
        if(rs.findById(id) == null) {
            response.put("status", HttpStatus.NOT_FOUND);
            response.put("message", "No se encontró el cuarto");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        if(rs.changeStatus(status.getStatus(), id)) {
            response.put("status", HttpStatus.OK);
            response.put("message", "Se actualizó el estatus del cuarto");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        response.put("status", HttpStatus.BAD_REQUEST);
        response.put("message", "No se actualizó el registro");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
