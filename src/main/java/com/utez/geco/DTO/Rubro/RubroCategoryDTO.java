package com.utez.geco.DTO.Rubro;

import java.util.List;

public class RubroCategoryDTO {
    Long idCategory;
    List<idRubro> idRubro;

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    public List<idRubro> getIdRubro() {
        return idRubro;
    }

    public void setIdRubro(List<idRubro> idRubro) {
        this.idRubro = idRubro;
    }

}
