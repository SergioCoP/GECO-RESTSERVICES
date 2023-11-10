package com.utez.geco.repository.Room;

import com.google.gson.JsonObject;
import com.utez.geco.model.Room;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {
    Room findByIdRoom(Long id);

    @Modifying
    @Transactional
    @Query(value = "insert into room_user values(:idUser,:idRoom)" ,nativeQuery = true)
    int assignRoomToUser(@Param("idUser") Long idUser,@Param("idRoom") Long idRoom);

    @Query(value = "SELECT * FROM room_user where id_user = :idUser AND id_room = :idRoom",nativeQuery = true)
    Object validateRomUser (@Param("idUser") Long idUser,@Param("idRoom") Long idRoom);

    //1-Pendiente || 2-Revisado || 3-En revisión
    @Modifying
    @Transactional
    @Query(value = "UPDATE room set status = :status where id_room = :idRoom",nativeQuery = true)
    int reviewRooom(@Param("status") int status,@Param("idRoom") Long idRoom);
}
