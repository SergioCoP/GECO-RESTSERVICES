package com.utez.geco.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "rubro")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "idRubro",
        scope = Rubro.class
)
public class Rubro {
    @Id
    @Column(name = "idRubro",nullable = false,unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRubro;

    @Column(name = "description",length = 200)
    private String description;

    @Column(name = "status")
    private int status = 1;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "category_rubro",
            joinColumns = @JoinColumn(name ="idRubro"),
            inverseJoinColumns = @JoinColumn(name = "idCategory")
    )
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private List<RoomCategory> idRoomCategory;

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

    public List<RoomCategory> getIdRoomCategory() {
        return idRoomCategory;
    }

    public void setIdRoomCategory(List<RoomCategory> idRoomCategory) {
        this.idRoomCategory = idRoomCategory;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
