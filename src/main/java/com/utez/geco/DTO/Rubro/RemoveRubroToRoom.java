package com.utez.geco.DTO.Rubro;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.JsonArray;

import java.util.List;
import java.util.Map;


public class RemoveRubroToRoom {
    private Long idRoom;
    private List<idRubro> idRubro;

    public RemoveRubroToRoom() {
    }

    public Long getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(Long idRoom) {
        this.idRoom = idRoom;
    }

    public List<com.utez.geco.DTO.Rubro.idRubro> getIdRubro() {
        return idRubro;
    }

    public void setIdRubro(List<com.utez.geco.DTO.Rubro.idRubro> idRubro) {
        this.idRubro = idRubro;
    }
}