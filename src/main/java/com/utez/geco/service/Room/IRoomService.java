package com.utez.geco.service.Room;

import com.utez.geco.model.Room;

import java.util.List;

public interface IRoomService {
    Room findById(Long id);
    List<Room> findAll();
}
