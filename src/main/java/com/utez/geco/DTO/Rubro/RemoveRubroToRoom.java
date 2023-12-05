package com.utez.geco.DTO.Rubro;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.JsonArray;

import java.util.List;
import java.util.Map;


public class RemoveRubroToRoom {
    private String category;
    private List<idRubro> idRubro;

    public RemoveRubroToRoom() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<com.utez.geco.DTO.Rubro.idRubro> getIdRubro() {
        return idRubro;
    }

    public void setIdRubro(List<com.utez.geco.DTO.Rubro.idRubro> idRubro) {
        this.idRubro = idRubro;
    }
}