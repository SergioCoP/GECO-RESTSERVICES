package geco.app.service.controller;

import org.springframework.http.HttpHeaders;
import geco.app.service.DTO.AuthCredentialsDTO;
import geco.app.service.DTO.UpdUsrDTO;
import geco.app.service.DTO.UsrChgPassDTO;
import geco.app.service.model.Hotel;
import geco.app.service.model.Person;
import geco.app.service.model.Rol;
import geco.app.service.model.User;
import geco.app.service.service.HotelService;
import geco.app.service.service.PersonService;
import geco.app.service.service.UserService;
import geco.app.service.utils.CustomService;
import geco.app.service.utils.securityConfig.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("**")
public class UserController {
    @Autowired
    private UserService us;
    @Autowired
    private PersonService ps;
    @Autowired
    private HotelService hs;
    private final CustomService cs = new CustomService();
    private HashMap<String, Object> response;
    
    @GetMapping("")
    public ResponseEntity<?> findAll() {
        response = new HashMap<>();
        List<User> userList = us.findAll();

        if(userList.isEmpty()) {
            response.put("status", HttpStatus.OK);
            response.put("message", "Aún no hay registros");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        response.put("status", HttpStatus.OK);
        response.put("message", "Operación exitosa");
        response.put("data", userList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(name = "id") long id) {
        response = new HashMap<>();
        User found = us.findById(id);

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
    public ResponseEntity<?> findByHotel(@PathVariable("id") long idHotel) {

        response = new HashMap<>();
        List<User> userList = null;
        if(hs.findById(idHotel) != null) {
            userList = us.findByHotel(idHotel);
            if(userList.isEmpty()) {
                response.put("status", HttpStatus.OK);
                response.put("message", "Aún no hay registros");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } else {
            response.put("status", HttpStatus.NOT_FOUND);
            response.put("message", "No se encontró el hotel");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("status", HttpStatus.OK);
        response.put("message", "Operación exitosa");
        response.put("data", userList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody User user) {
        response = new HashMap<>();
        if(hs.findById(user.getIdHotel().getIdHotel()) != null) {
            Person userPerson = user.getIdPerson();
            if(!cs.checkBlacklists(userPerson.toString())) {
                if(!ps.save(userPerson)) {
                    response.put("status", HttpStatus.BAD_REQUEST);
                    response.put("message", "No se pudo registrar a la persona");
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }
            }
            long idPerson = ps.findLastId();

            user.setStatus(1);
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            user.setIdPerson(new Person(idPerson));
            if(!cs.checkBlacklists(user.toString())) {
                if(us.save(user)) {
                    long idUser = us.findLastId();
                    response.put("status", HttpStatus.CREATED);
                    response.put("idPerson", idUser);
                    response.put("message", "Se creó el registro");
                    return new ResponseEntity<>(response, HttpStatus.CREATED);
                }
            }
        } else {
            response.put("status", HttpStatus.NOT_FOUND);
            response.put("message", "No se encontró el hotel");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("status", HttpStatus.BAD_REQUEST);
        response.put("message", "No se creó el registro");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/hotel")
    public ResponseEntity<?> saveWithHotel(@RequestBody User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        response = new HashMap<>();
        Person userPerson = user.getIdPerson();
        if(!cs.checkBlacklists(userPerson.toString())) {
            if(!ps.save(userPerson)) {
                response.put("status", HttpStatus.BAD_REQUEST);
                response.put("message", "No se pudo registrar a la persona");
                return new ResponseEntity<>(response,headers, HttpStatus.BAD_REQUEST);
            }
        }
        long idPerson = ps.findLastId();

        Hotel userHotel = user.getIdHotel();
        if(!cs.checkBlacklists(userHotel.toString())) {
            if(!hs.save(userHotel)) {
                response.put("status", HttpStatus.BAD_REQUEST);
                response.put("message", "No se pudo registrar a el hotel");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        }
        long idHotel = hs.findLastId();

        user.setStatus(1);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setIdPerson(new Person(idPerson));
        user.setIdRol(new Rol(1));
        user.setIdHotel(new Hotel(idHotel));
        if(!cs.checkBlacklists(user.toString())) {
            if(us.save(user)) {
                long idUser = us.findLastId();
                response.put("status", HttpStatus.CREATED);
                response.put("message", "Se creó el registro");
                response.put("idUser", idUser);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
        }

        response.put("status", HttpStatus.BAD_REQUEST);
        response.put("message", "No se creó el registro");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    public  ResponseEntity<?> login(@RequestBody AuthCredentialsDTO auth) {
        response = new HashMap<>();
        if(!cs.checkBlacklists(auth.toString())) {
            System.out.println(auth.getUser() + " " + auth.getPassword());
            User found = us.login(auth.getUser(), auth.getPassword());
            if(found != null) {
                String token = TokenUtils.createToken(auth.getUser(), auth.getUser());
                response.put("status", HttpStatus.OK);
                response.put("message", "Inicio de sesión exitoso");
                response.put("user", found);
                response.put("token", token);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            response.put("status", HttpStatus.NOT_FOUND);
            response.put("message", "Credenciales invalidas");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("status", HttpStatus.BAD_REQUEST);
        response.put("message", "Inicio de sesión fallido");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("")
    public ResponseEntity<?> update(@RequestBody UpdUsrDTO updUsrDTO) {
        response = new HashMap<>();
        if(!cs.checkBlacklists(updUsrDTO.toString())) {
            if(us.findById(updUsrDTO.getIdUser()) != null) {
                if(us.updateEmailUsernameRol(updUsrDTO)) {
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

    @PutMapping("/newpass")
    public ResponseEntity<?> changePassword(@RequestBody UsrChgPassDTO userDTO) {
        response = new HashMap<>();
        if(!cs.checkBlacklists(userDTO.toString())) {
            if(us.findById(userDTO.getIdUser()) != null) {
                userDTO.setCurrent(us.findById(userDTO.getIdUser()).getPassword());
                if(us.changePassword(userDTO)) {
                    response.put("status", HttpStatus.OK);
                    response.put("message", "Se modificó la contraseña");
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
    
    @PutMapping("/status/{id}")
    private ResponseEntity<?> changeStatus(@PathVariable("id") long id) {
        response = new HashMap<>();
        User found = us.findById(id);
        if(found != null) {
            if(us.changeStatus(found, id)) {
                response.put("status", HttpStatus.OK);
                response.put("message", "Se cambió el estado del usuario");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } else {
            response.put("status", HttpStatus.NOT_FOUND);
            response.put("message", "No se encontró el registro");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("status", HttpStatus.BAD_REQUEST);
        response.put("message", "No se modificó el registro");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
