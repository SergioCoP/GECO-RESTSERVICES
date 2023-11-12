package com.utez.geco.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hotel")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "idHotel",
        scope = Hotel.class
)
public class Hotel {

    @Id
    @Column(name = "idHotel",nullable = false,unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHotel;

    @Column(name = "name",length = 50)
    private String name;
    @Column(name = "colorFont",length = 20)
    private String colorFont;
    @Column(name = "colorPrimary",length = 20)
    private String colorPrimary;
    @Column(name = "colorSecondary",length = 20)
    private String colorSecondary;
    @Column(name = "colorTertiary",length = 20)
    private String colorTertiary;

    //idUser

    @ManyToMany(mappedBy = "hotels",cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<>();

    public Hotel() {
    }

    public Long getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(Long idHotel) {
        this.idHotel = idHotel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColorFont() {
        return colorFont;
    }

    public void setColorFont(String colorFont) {
        this.colorFont = colorFont;
    }

    public String getColorPrimary() {
        return colorPrimary;
    }

    public void setColorPrimary(String colorPrimary) {
        this.colorPrimary = colorPrimary;
    }

    public String getColorSecondary() {
        return colorSecondary;
    }

    public void setColorSecondary(String colorSecondary) {
        this.colorSecondary = colorSecondary;
    }

    public String getColorTertiary() {
        return colorTertiary;
    }

    public void setColorTertiary(String colorTertiary) {
        this.colorTertiary = colorTertiary;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
