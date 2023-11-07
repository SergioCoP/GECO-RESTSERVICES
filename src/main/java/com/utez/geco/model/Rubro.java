package com.utez.geco.model;

import jakarta.persistence.*;

@Entity
@Table(name = "rubro")
public class Rubro {
    @Id
    @Column(nullable = false,unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRubro;

    @Column(length = 200)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idRoom",nullable = false)
    private Room idRoom;

    public Rubro() {
    }

    public Long getIdRubro() {
        return idRubro;
    }

    public void setIdRubro(Long idRubro) {
        this.idRubro = idRubro;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Room getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(Room idRoom) {
        this.idRoom = idRoom;
    }
}
