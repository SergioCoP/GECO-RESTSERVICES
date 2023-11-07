package com.utez.geco.service;

import com.utez.geco.model.Room;
import com.utez.geco.repository.RoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RoomServiceImpl extends Room {
    @Autowired
    private RoomRepository roomRepository;

    public List<Room> findAll(){return roomRepository.findAll();}
    public Room findById(Long id){return roomRepository.findByIdRoom(id);}
    public Room register(Room room){return roomRepository.save(room);}
    public Room update(Room room){return roomRepository.save(room);}
    public void delete(Long id){
        roomRepository.deleteById(id);
    }

}
