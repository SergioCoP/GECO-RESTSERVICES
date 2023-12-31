package com.utez.geco.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "user")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "idUser",
        scope = User.class
)
public class User {
    @Id
    @Column(name = "idUser",nullable = false,unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @Column(name = "email",nullable = false)
    private String email;
    @Column(name = "password",nullable = false,length = 255)
    private String password;
    @Column(name = "status",nullable = false,columnDefinition = "int default 1")
    private int status = 1;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="idPerson")
    private Person idPerson;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_hotel",
            joinColumns = @JoinColumn(name="idUser"),
            inverseJoinColumns = @JoinColumn(name = "idHotel")
    )
    private List<Hotel> hotels;


    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinTable(
            name="user_rol",
            joinColumns = @JoinColumn(name="idUser"),
            inverseJoinColumns = @JoinColumn(name = "idRol")
    )
    private Role idRol = new Role();

    @ManyToMany(mappedBy = "idUser")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private List<Room> rooms = new ArrayList<>();

    @OneToMany(mappedBy = "idUser",orphanRemoval = true)
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

    public List<Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(List<Hotel> hotels) {
        this.hotels = hotels;
    }

    public Role getIdRol() {
        return idRol;
    }

    public void setIdRol(Role idRol) {
        this.idRol = idRol;
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
