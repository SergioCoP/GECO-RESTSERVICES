package com.utez.geco.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "incidence")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "idIncidence",
        scope = Incidence.class
)
public class Incidence {
    @Id
    @Column(name = "idInicidence",nullable = false,unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idIncidence;
    @Column(name = "createdAt")
    private Date createdAt;
    @Column(name = "description")
    private String description;
    @Column(name = "image")
    private String image;
    @Column(name = "status")
    private int status;

    //iduser
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "user_incidence",
    joinColumns = @JoinColumn(name = "idIncidence"),
    inverseJoinColumns = @JoinColumn(name = "idUser"))
    private User idUser;
    //idRoom
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idRoom")
    @JoinTable(name = "room_incidence",
    joinColumns = @JoinColumn(name = "idIncidence"),
    inverseJoinColumns = @JoinColumn(name ="idRoom")
    )
    private Room idRoom;

    public Incidence() {
    }

    public Long getIdIncidence() {
        return idIncidence;
    }

    public void setIdIncidence(Long idIncidence) {
        this.idIncidence = idIncidence;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public Room getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(Room idRoom) {
        this.idRoom = idRoom;
    }


}
