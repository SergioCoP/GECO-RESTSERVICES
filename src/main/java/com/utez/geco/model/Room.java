package com.utez.geco.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "room")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "idRoom"
)
public class Room {
    @Id
    @Column(name = "idRoom",nullable = false,unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRoom;

    @Column(name = "identifier",length = 20)
    private String identifier;

    @Column(name = "status",columnDefinition = "int default 1")
    private int status;
    //rrrom-user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "room_user",
            joinColumns = @JoinColumn(name ="idRoom"),
            inverseJoinColumns = @JoinColumn(name = "idUser")
    )
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private User idUser;
   //room-rubro
    @OneToMany(mappedBy = "idRoom",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Rubro> rubros = new ArrayList<>();

    @OneToMany(mappedBy = "idRoom",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Incidence> incidences = new ArrayList<>();


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

    public List<Rubro> getRubros() {
        return rubros;
    }

    public void setRubros(List<Rubro> rubros) {
        this.rubros = rubros;
    }

    public List<Incidence> getIncidences() {
        return incidences;
    }

    public void setIncidences(List<Incidence> incidences) {
        this.incidences = incidences;
    }
}
