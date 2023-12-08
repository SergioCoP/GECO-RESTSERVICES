package com.utez.geco.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "room")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "idRoom",
        scope = Room.class
)
public class Room {
    @Id
    @Column(name = "idRoom",nullable = false,unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRoom;

    @Column(name = "identifier",length = 20)
    private String identifier;

    @Column(name = "status",columnDefinition = "int default 1")
    private int status = 1;

    @Column(name = "description",length = 255)
    private String description;

    //rrrom-user
    @ManyToMany()
    @JoinTable(
            name = "room_user",
            joinColumns = @JoinColumn(name ="idRoom"),
            inverseJoinColumns = @JoinColumn(name = "idUser")
    )
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private List<User> idUser ;
   //room-rubro

    @OneToMany(mappedBy = "idRoom",fetch = FetchType.EAGER,orphanRemoval = true)
    private List<Incidence> incidences = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinTable(
            name="category_room",
            joinColumns = @JoinColumn(name="idRoom"),
            inverseJoinColumns = @JoinColumn(name = "idCategory")
    )
    private RoomCategory idRoomCategory;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getIdUser() {
        return idUser;
    }

    public void setIdUser(List<User> idUser) {
        this.idUser = idUser;
    }


    public List<Incidence> getIncidences() {
        return incidences;
    }

    public void setIncidences(List<Incidence> incidences) {
        this.incidences = incidences;
    }


}
