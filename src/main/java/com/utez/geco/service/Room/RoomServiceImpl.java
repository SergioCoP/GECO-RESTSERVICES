package com.utez.geco.service.Room;

import com.google.gson.Gson;
import com.utez.geco.DTO.Room.*;
import com.utez.geco.model.Room;
import com.utez.geco.repository.Room.RoomRepository;
import com.utez.geco.repository.User.UserRepository;
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
    @Autowired
    private UserRepository userRepository;

    public List<RoomsDTO> findAll(){return roomRepository.getAllRooms();}

    public RoomWithUserById findById(Long id){
        RoomWithUserById ro = new RoomWithUserById();
        Room rom = roomRepository.getRoomWithUsersById(id);
        if(rom != null){
            ro.setIdRoom(rom.getIdRoom());
            ro.setIdentifier(rom.getIdentifier());
            ro.setUsers(roomRepository.getUsersByIdRoom(id));
        }else{
            ro = null;
        }
        return ro;
    }

    public List<RoomsDTO> findByCategory(String category){
        return roomRepository.findByCategory(category);
    }

    public int registerCategory(RoomCategoryDTO category){return roomRepository.registerCategory(category.getName());}
    public int updateCategory(RoomCategoryDTO category){return roomRepository.updateCategory(category.getName(),category.getIdCategory());}


    public CategoryDTO findCategoryByName(String name){return roomRepository.findCategoryByName(name);}
    public CategoryDTO findCategoryById(Long id){return roomRepository.findCategoryById(id);}
    public List<CategoryDTO> findCategories(String name){return roomRepository.findCategories();}
    public int assignCategoryToRoom(RoomCategoryDTO romCat){return roomRepository.assignCategoryToRoom(romCat.getIdCategory(),romCat.getIdRoom());}
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

    public String unsignUserToRoom(Long idUser,Long idRoom){
       RoomsDTO dRom = roomRepository.findByIdRoom(idRoom);
       if(dRom != null){
           if(userRepository.findByIdUser(idUser) != null){
                if(roomRepository.unsignRoomToUser(idUser,idRoom) >= 1){
                    msg = "Unsigned";
                }else{
                    msg = "NotUnsigned";
               }
           }else{
               msg = "UserNotExist";
           }
       }else{
           msg = "RoomNotExist";
       }
       return msg;
    }

    public String deleteRoomUserDown(Long idUser){
            if(userRepository.findByIdUser(idUser) != null){
                if(roomRepository.deleteRoomDownUser(idUser) >= 1){
                    msg = "Deleted";
                }else{
                    msg = "NotDeleted";
                }
            }else{
                msg = "UserNotExist";
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
