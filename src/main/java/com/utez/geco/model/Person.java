package com.utez.geco.model;


import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

@Entity
@Table(name = "person")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "idPerson",
        scope = Person.class
)
public class Person {
    @Id
    @Column(name = "idPerson",nullable = false,unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPerson;

    @Column(name = "name",nullable = false,length = 60)
    private String name;
    @Column(name = "surname",nullable = false,length = 60)
    private String surname;
    @Column(name = "lastname",nullable = true,length = 60)
    private String lastname;
    @Column(name = "turn",nullable = true,length = 60)
    private String turn;


    @OneToOne(mappedBy = "idPerson")
    private User idUser;



    public Person() {
    }

    public Long getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(Long idPerson) {
        this.idPerson = idPerson;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }
}
