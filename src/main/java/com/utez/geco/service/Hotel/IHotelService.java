package com.utez.geco.service.Hotel;

import com.utez.geco.model.Hotel;

import java.util.List;

public interface IHotelService {
    Hotel findById(Long id);
    List<Hotel> findAll();
}
