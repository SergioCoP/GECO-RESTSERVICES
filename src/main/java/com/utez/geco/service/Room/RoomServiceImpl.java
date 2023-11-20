package com.utez.geco.service.Room;

import com.google.gson.Gson;
import com.utez.geco.DTO.Room.RoomWithUserById;
import com.utez.geco.DTO.Room.RoomsDTO;
import com.utez.geco.DTO.Room.RoomsWithUser;
import com.utez.geco.model.Room;
import com.utez.geco.repository.Room.RoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RoomServiceImpl extends Room {
    String msg = "";
    @Autowired
    private RoomRepository roomRepository;

    public List<RoomsDTO> findAll(){return roomRepository.getAllRooms();}
    public RoomsDTO findById(Long id){return roomRepository.findByIdRoom(id);}

    public int register(Room room){return roomRepository.registerRoom(room.getIdentifier(),room.getDescription(),room.getStatus());}
    public Room update(Room room){return roomRepository.save(room);}
    public void delete(Long id){
        roomRepository.deleteById(id);
    }

    public List<RoomsWithUser> getRoomsWithUser(){

        return roomRepository.getRoomsWithUsers();}

    public RoomWithUserById getRoomWithUserById(Long idRoom){
        RoomWithUserById ro = new RoomWithUserById();
        Room rom = roomRepository.getRoomWithUsersById(idRoom);
        ro.setIdRoom(rom.getIdRoom());
        ro.setIdentifier(rom.getIdentifier());
        ro.setUsers(roomRepository.getUsersByIdRoom(idRoom));
        return ro;
    }
    public String assignUserToRoom(Long idUser,Long idRoom){

        RoomsDTO sRom = roomRepository.findByIdRoom(idRoom);
        if(sRom != null){
            if(new Gson().toJson(roomRepository.validateRomUser(idUser,idRoom)).equals("null")){
                if(roomRepository.assignRoomToUser(idUser, idRoom) >= 1){
                    msg = "Assigned";
                }else{
                    msg = "NotAssigned";
                }
            }else{
                msg = "RoomWasAsigned";
            }
        }else{
            msg = "RoomNotFound";
        }
        return msg;
    }

    public String reviewRoom(int status,Long idRoom){

       if(roomRepository.reviewRooom(status,idRoom) >= 1){
           msg = "Done";
       }else{
           msg = "NotReview";
       }
        return msg;
    }



}
