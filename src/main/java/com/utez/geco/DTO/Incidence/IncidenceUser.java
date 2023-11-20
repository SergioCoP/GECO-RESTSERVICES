package com.utez.geco.DTO.Incidence;

public interface IncidenceUser {
    Long getIdIncidence();
    String getDescription();
    String getImage();
    int getStatus();

    Long getIdRoom();
    String getIdentifier();
    String getUserName();
    Long getIdUser();
}
