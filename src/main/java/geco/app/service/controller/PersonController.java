package geco.app.service.controller;

import geco.app.service.model.Person;
import geco.app.service.service.PersonService;
import geco.app.service.utils.CustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@CrossOrigin
@RequestMapping(value = "/api/person")
@RestController
public class PersonController {
    @Autowired
    private PersonService ps;
    private CustomService cs = new CustomService();
    private HashMap<String, Object> response;

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        response = new HashMap<>();
        List<Person> personList = ps.findAll();

        if(personList.isEmpty()) {
            response.put("status", HttpStatus.OK);
            response.put("message", "Aún no hay registros");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        response.put("status", HttpStatus.OK);
        response.put("message", "Operación exitosa");
        response.put("data", personList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(name = "id") long id) {
        response = new HashMap<>();
        Person found = ps.findById(id);

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

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Person person) {
        response = new HashMap<>();
        if(!cs.checkBlacklists(person.toString())) {
            if(ps.save(person)) {
                long idPerson = ps.findLastId();
                response.put("status", HttpStatus.CREATED);
                response.put("idPerson", idPerson);
                response.put("message", "Se creó el registro");
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
        }

        response.put("status", HttpStatus.BAD_REQUEST);
        response.put("message", "No se creó el registro");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("")
    public ResponseEntity<?> update(@RequestBody Person person) {
        response = new HashMap<>();
        if(!cs.checkBlacklists(person.toString())) {
            if(ps.findById(person.getIdPerson()) != null) {
                if(ps.update(person)) {
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
