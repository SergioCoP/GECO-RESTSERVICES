package com.utez.geco.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "idUser"
)
public class User {
    @Id
    @Column(name = "idUser",nullable = false,unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @Column(name = "email",nullable = false)
    private String email;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "status",nullable = false,columnDefinition = "int default 1")
    private int status;


//   @JoinTable(name = "user_person",
//   joinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "idUser")},
//   inverseJoinColumns = {@JoinColumn(name = "person_id",referencedColumnName = "idPerson")})
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="idPerson")
    private Person idPerson;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="idHotel")
    private Hotel idHotel;

    @OneToMany(mappedBy = "idUser")
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "idUser")
    private List<Room> rooms = new ArrayList<>();

    @OneToMany(mappedBy = "idUser" )
    private List<Incidence> incidences = new ArrayList<>();

    public User() {
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Person getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(Person idPerson) {
        this.idPerson = idPerson;
    }

    public Hotel getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(Hotel idHotel) {
        this.idHotel = idHotel;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Incidence> getIncidences() {
        return incidences;
    }

    public void setIncidences(List<Incidence> incidences) {
        this.incidences = incidences;
    }


}
