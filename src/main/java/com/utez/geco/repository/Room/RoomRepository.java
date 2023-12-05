package com.utez.geco.repository.Room;

import com.utez.geco.DTO.Room.RoomWithUserById;
import com.utez.geco.DTO.Room.RoomsDTO;
import com.utez.geco.DTO.Room.RoomsWithUser;
import com.utez.geco.DTO.Room.UsersByRoom;
import com.utez.geco.model.Room;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends CrudRepository<Room,Long> {
    @Query(value = "select r.id_room as idRoom,r.identifier,r.status,r.description,u.id_user as idUser,CONCAT(p.name, ' ',p.surname, ' ', p.lastname) as userName from room r\n" +
            "left join room_user ru on r.id_room = ru.id_room\n" +
            "left join user u on ru.id_user = u.id_user\n" +
            "left join person p on u.id_person = p.id_person where r.id_room = :idRoom",nativeQuery = true)
    RoomsDTO findByIdRoom(@Param("idRoom")Long idRoom);


    @Modifying
    @Transactional
    @Query(value = "INSERT INTO room(identifier,description,status) values(:identifier,:description,:status)",nativeQuery = true)
    int registerRoom(@Param("identifier")String roomId,@Param("description")String roomDesc,@Param("status")int status);

    @Query(value = "select r.id_room as idRoom,r.identifier as identifier ,u.id_user as idUser,CONCAT(p.name,' ',p.surname,' ',p.lastname) as userName\n" +
            "       from user u\n" +
            "join person p on u.id_person = p.id_person\n" +
            "join room_user ru on u.id_user = ru.id_user\n" +
            "join room r on ru.id_room = r.id_room;",nativeQuery = true)
    List<RoomsWithUser> getRoomsWithUsers();

    @Query(value = "select r.id_room ,r.identifier as identifier,r.description as description,r.status as status\n" +
            "                   from room r  where r.id_room = :idRoom",nativeQuery = true)
    Room getRoomWithUsersById(@Param("idRoom")Long idRoom);
    //
    @Query(value = "select CONCAT(p.name,' ',p.surname, ' ',p.lastname) as userName,u.id_user as idUser  from user u\n" +
            "left join room_user ru on u.id_user = ru.id_user\n" +
            "left join room r on ru.id_room = r.id_room\n" +
            "left join person p on u.id_person = p.id_person\n" +
            "where r.id_room = :idRoom",nativeQuery = true)
    List<UsersByRoom> getUsersByIdRoom(@Param("idRoom")Long idRoom);

    @Query(value = "select r.id_room as idRoom,r.identifier,r.status,r.description,u.id_user as idUser,CONCAT(p.name, ' ',p.surname, ' ', p.lastname) as userName from room r\n" +
            "left join room_user ru on r.id_room = ru.id_room\n" +
            "left join user u on ru.id_user = u.id_user\n" +
            "left join person p on u.id_person = p.id_person;",nativeQuery = true)
    List<RoomsDTO> getAllRooms();
    @Modifying
    @Transactional
    @Query(value = "insert into room_user(id_user,id_room) values(:idUser,:idRoom)" ,nativeQuery = true)
    int assignRoomToUser(@Param("idUser") Long idUser,@Param("idRoom") Long idRoom);

    @Modifying
    @Transactional
    @Query(value = "delete from room_user where id_room = :idRoom AND id_user = :idUser" ,nativeQuery = true)
    int unsignRoomToUser(@Param("idUser") Long idUser,@Param("idRoom") Long idRoom);

    @Query(value = "SELECT * FROM room_user where id_user = :idUser AND id_room = :idRoom",nativeQuery = true)
    Object validateRomUser (@Param("idUser") Long idUser,@Param("idRoom") Long idRoom);

    //1-Pendiente || 2-Revisado || 3-En revisión
    @Modifying
    @Transactional
    @Query(value = "UPDATE room set status = :status where id_room = :idRoom",nativeQuery = true)
    int reviewRooom(@Param("status") int status,@Param("idRoom") Long idRoom);



    @Modifying
    @Transactional
    @Query(value = "delete from room_user where  id_user = :idUser" ,nativeQuery = true)
    int deleteRoomDownUser(@Param("idUser") Long idUser);

}
