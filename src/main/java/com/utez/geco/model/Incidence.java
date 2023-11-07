package com.utez.geco.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "incidence")
public class Incidence {
    @Id
    @Column(nullable = false,unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idIncidence;

    private Date createdAt;

    private String description;
    private String image;
    private int status;

    //iduser
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idUser",nullable = false)
    private User idUser;
    //idRoom
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idRoom",nullable = false)
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
