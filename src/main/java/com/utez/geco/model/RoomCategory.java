package com.utez.geco.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roomCategory")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "idRoomCategory",
        scope = RoomCategory.class
)
public class RoomCategory {
    @Id
    @Column(name = "idCategory",nullable = false,unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long idRoomCategory;
    private String name;

    @OneToMany(mappedBy = "idRoomCategory",fetch = FetchType.EAGER,orphanRemoval = true)
    private List<Room> rooms = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "idRoomCategory")
    private List<Rubro> idRubro;

}