package com.utez.geco.model;

import jakarta.persistence.*;

@Entity
@Table(name = "rol")
public class Role {
    @Id
    @Column(name = "idRol",nullable = false,unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRol;

    private String name;
    private String description;

    //many to many xd
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idUser",nullable = false)
    private User idUser;

    public Role() {
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }
}
