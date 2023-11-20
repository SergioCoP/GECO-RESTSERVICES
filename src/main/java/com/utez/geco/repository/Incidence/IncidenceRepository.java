package com.utez.geco.repository.Incidence;

import com.utez.geco.DTO.Incidence.IncidenceUser;
import com.utez.geco.model.Incidence;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidenceRepository extends JpaRepository<Incidence,Long> {


    @Query(value = "select i.id_inicidence as idIncidence,i.description,i.image,i.status,r.id_room as idRoom,r.identifier,concat(p.name, ' ',p.surname, ' ',p.lastname) as userName,u.id_user as idUser from incidence i\n" +
            "            join room_incidence ri on i.id_inicidence = ri.id_incidence\n" +
            "            join user_incidence ui on i.id_inicidence = ui.id_incidence\n" +
            "            join room r on ri.id_room = r.id_room\n" +
            "            join user u on ui.id_user = u.id_user\n" +
            "            join person p on u.id_person = p.id_person;",nativeQuery = true)
    List<IncidenceUser> getIncidences();

    @Query(value = "select i.id_inicidence as idIncidence,i.description,i.image,i.status,r.id_room as idRoom,r.identifier,concat(p.name, ' ',p.surname, ' ',p.lastname) as userName,u.id_user as idUser from incidence i\n" +
            "            join room_incidence ri on i.id_inicidence = ri.id_incidence\n" +
            "            join user_incidence ui on i.id_inicidence = ui.id_incidence\n" +
            "            join room r on ri.id_room = r.id_room\n" +
            "            join user u on ui.id_user = u.id_user\n" +
            "            join person p on u.id_person = p.id_person\n" +
            "            where r.identifier = :idRoom",nativeQuery = true)
    IncidenceUser getIncidenceByRoomIdentifier(@Param("idRoom")String identifier);

    @Query(value = "select i.id_inicidence as idIncidence,i.description,i.image,i.status,r.id_room as idRoom,r.identifier,concat(p.name, ' ',p.surname, ' ',p.lastname) as userName,u.id_user as idUser from incidence i\n" +
            "            join room_incidence ri on i.id_inicidence = ri.id_incidence\n" +
            "            join user_incidence ui on i.id_inicidence = ui.id_incidence\n" +
            "            join room r on ri.id_room = r.id_room\n" +
            "            join user u on ui.id_user = u.id_user\n" +
            "            join person p on u.id_person = p.id_person\n" +
            "            where r.id_room = :idRoom",nativeQuery = true)
    IncidenceUser getIncidenceByRoomId(@Param("idRoom")Long idRoom);

    @Query(value = "select i.id_inicidence as idIncidence,i.description,i.image,i.status,r.id_room as idRoom,r.identifier,concat(p.name, ' ',p.surname, ' ',p.lastname) as userName,u.id_user as idUser from incidence i\n" +
            "            join room_incidence ri on i.id_inicidence = ri.id_incidence\n" +
            "            join user_incidence ui on i.id_inicidence = ui.id_incidence\n" +
            "            join room r on ri.id_room = r.id_room\n" +
            "            join user u on ui.id_user = u.id_user\n" +
            "            join person p on u.id_person = p.id_person\n" +
            "            where i.id_inicidence = :idIncidence",nativeQuery = true)
    IncidenceUser getIncidenceByIdIncidence(@Param("idIncidence")Long idIncidence);

    @Modifying
    @Transactional
    @Query(value = "insert into user_incidence values(:idUser,:idIncidence)",nativeQuery = true)
    int assignIncidenceUser(@Param("idUser")Long idUser,@Param("idIncidence")Long idIncidence);

    @Modifying
    @Transactional
    @Query(value = "insert into room_incidence values(:idRoom,:idIncidence)",nativeQuery = true)
    int assignIncidenceRoom(@Param("idRoom")Long idRoom,@Param("idIncidence")Long idIncidence);
    @Modifying
    @Transactional
    @Query(value = "update incidence set status = :status where id_inicidence = :idIncidence",nativeQuery = true)
    int updateStatus(@Param("status")int status,@Param("idIncidence")Long idIncidence);

    @Modifying
    @Transactional
    @Query(value = "insert into incidence(description,image,status,created_at) values(:description,:image,:status,now());", nativeQuery = true)
    Incidence saveIncidence(@Param("description")String description,@Param("image")String image,@Param("status")int status);
}
