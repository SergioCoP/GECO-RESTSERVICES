package com.utez.geco.DTO.Room;

import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.SqlResultSetMapping;

import java.util.List;



public class RoomWithUserById {
    private Long idRoom;
    private String identifier;
    private String description;

    private List<UsersByRoom> users;


    public RoomWithUserById() {
    }

    public Long getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(Long idRoom) {
        this.idRoom = idRoom;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }


    public List<UsersByRoom> getUsers() {
        return users;
    }

    public void setUsers(List<UsersByRoom> users) {
        this.users = users;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
