package com.utez.geco.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "hotel")
public class Hotel {

    @Id
    @Column(name = "idHotel",nullable = false,unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHotel;

    @Column(length = 50)
    private String name;
    @Column(length = 20)
    private String colorFont;
    @Column(length = 20)
    private String colorPrimary;
    @Column(length = 20)
    private String colorSecondary;
    @Column(length = 20)
    private String colorTertiary;

    //idUser
    @OneToOne(mappedBy = "idHotel")
    @JsonBackReference
    private User idUser;

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

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }
}
