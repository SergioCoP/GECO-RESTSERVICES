package com.utez.geco.service.Rubro;

import com.utez.geco.model.Rubro;

import java.util.List;

public interface IRubroService {
    Rubro findById(Long id);
    List<Rubro> findAll();
}
