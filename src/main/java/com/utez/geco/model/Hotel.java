package com.utez.geco.model;

import jakarta.persistence.*;

@Entity
@Table(name = "hotel")
public class Hotel {

    @Id
    @Column(name = "idHotel",nullable = false,unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHotel;
    @Column(nullable = false,length = 50)
    private String name;
    @Column(nullable = false,length = 190)
    private String description;
    @Column(nullable = false,length = 200)
    private String address;
    @Column(nullable = false,length = 12)
    private String phone;
    @Column(nullable = false,length = 15)
    private String rfc;
    @Column(nullable = false,length = 255)
    private String mission;
    @Column(nullable = false,length = 255)
    private String vission;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getVission() {
        return vission;
    }

    public void setVission(String vission) {
        this.vission = vission;
    }
}
