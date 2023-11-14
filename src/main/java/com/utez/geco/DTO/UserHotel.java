package com.utez.geco.DTO;

public class UserHotel {
    private Long id_hotel;
    private String color_font;
    private String color_primary;
    private String secondary;
    private String tertiary;
    private String name;
    private Long id_user;
    private String email;
    private Long id_person;

    public Long getId_hotel() {
        return id_hotel;
    }

    public void setId_hotel(Long id_hotel) {
        this.id_hotel = id_hotel;
    }

    public String getColor_font() {
        return color_font;
    }

    public void setColor_font(String color_font) {
        this.color_font = color_font;
    }

    public String getColor_primary() {
        return color_primary;
    }

    public void setColor_primary(String color_primary) {
        this.color_primary = color_primary;
    }

    public String getSecondary() {
        return secondary;
    }

    public void setSecondary(String secondary) {
        this.secondary = secondary;
    }

    public String getTertiary() {
        return tertiary;
    }

    public void setTertiary(String tertiary) {
        this.tertiary = tertiary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId_person() {
        return id_person;
    }

    public void setId_person(Long id_person) {
        this.id_person = id_person;
    }
}
