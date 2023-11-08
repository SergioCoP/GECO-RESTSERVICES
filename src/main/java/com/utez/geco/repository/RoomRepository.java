package com.utez.geco.repository;

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
    @Query(value = "UPDATE room set id_user = :idUser where id_room = :idRoom",nativeQuery = true)
    int assignRoomToUser(@Param("idUser") Long idUser,@Param("idRoom") Long idRoom);

}
