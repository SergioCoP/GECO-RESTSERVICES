package com.utez.geco.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "room")
public class Room {
    @Id
    @Column(name = "idRoom",nullable = false,unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRoom;
    @Column(length = 20)
    private String identifier;
    @Column(columnDefinition = "int default 1")
    private int status;
    //rrrom-user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idUser")
    private User idUser;
   //room-rubro
    @OneToMany(mappedBy = "idRoom")
    private Set<Rubro> rubros;

    @OneToMany(mappedBy = "idRoom")
    private Set<Incidence> incidences;


    public Long getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(Long idRoom) {
        this.idRoom = idRoom;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    public Set<Rubro> getRubros() {
        return rubros;
    }

    public void setRubros(Set<Rubro> rubros) {
        this.rubros = rubros;
    }

    public Set<Incidence> getIncidences() {
        return incidences;
    }

    public void setIncidences(Set<Incidence> incidences) {
        this.incidences = incidences;
    }
}
