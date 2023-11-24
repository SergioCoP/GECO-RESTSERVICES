package com.utez.geco.repository.User;

import com.utez.geco.DTO.Role.RoleDTO;
import com.utez.geco.DTO.User.AllUsersDTO;
import com.utez.geco.DTO.User.UserById;
import com.utez.geco.DTO.User.UsersByRol;
import com.utez.geco.DTO.User.UsersDTO;
import com.utez.geco.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestParam;


import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByEmailAndPassword(String email, String password);


    @Modifying
    @Transactional
    @Query(value = "call registerUser(:Uname,:Usurname,:Ulastname,:Uturn,:Uemail,:Upassword)",nativeQuery = true)
    int registerUser(@Param("Uname")String name,
                     @Param("Usurname")String surname,
                     @Param("Ulastname")String lastname,
                     @Param("Uturn")String turn,
                     @Param("Uemail")String email,
                     @Param("Upassword")String password);

    @Modifying
    @Transactional
    @Query(value ="insert into user_rol(id_rol,id_user) values(:idRol,:idUser);",nativeQuery = true)
    int assignRolToUser(@Param("idRol")Long idRol,@Param("idUser")Long idUser);

    @Query(value = "select id_rol as idRol,name as rolName from rol where id_rol = :idRol",nativeQuery = true)
    RoleDTO findRolById(@Param("idRol")Long idRol);

    @Query(value = "select u.id_user as idUser,u.email,concat(p.name, ' ', p.surname, ' ', p.lastname) as userName,p.turn,u.status from user u " +
                      "join person p on p.id_person = u.id_person where u.email = :email",nativeQuery = true)
    UsersDTO findByEmailLog(@Param("email") String email);


    Optional<User> findByEmail(String email);
    

    @Query(value = "select u.id_user as idUser,u.email,p.name, p.surname, p.lastname,\n" +
            "p.turn,u.status,r.id_rol as idRol from user u\n" +
            "join person p on p.id_person = u.id_person\n" +
            "left join user_rol ur on u.id_user = ur.id_user\n" +
            "left join rol r on r.id_rol = ur.id_rol\n" +
            "where u.id_user = :idUser",nativeQuery = true)
    UserById findByIdUser(@Param("idUser") Long idUser);
    @Query(value = "select u.id_user as idUser,u.email as userEmail,\n" +
            "       concat(p.name, ' ', p.surname, ' ', p.lastname) as userName,\n" +
            "       p.turn,r.id_rol as idRol, r.name as userRol,h.id_hotel as idHotel,u.status from user u\n" +
            "join user_rol ur on u.id_user = ur.id_user\n" +
            "left join user_hotel uh on u.id_user = uh.id_user\n" +
            "left join hotel h on uh.id_hotel = h.id_hotel\n" +
            "join rol r on ur.id_rol = r.id_rol\n" +
            "join person p on p.id_person = u.id_person;", nativeQuery = true)
    List<AllUsersDTO> findAllUsers();

    @Query(value = "select u.id_user as idUser,CONCAT(p.name,' ',p.surname, ' ',p.lastname) as userName,u.email,p.turn,r.name as rolName,u.status from user u\n" +
            "join user_rol ur on u.id_user = ur.id_user\n" +
            "join rol r on ur.id_rol = r.id_rol\n" +
            "join person p on u.id_person = p.id_person\n" +
            "where r.name = :rolName",nativeQuery = true)
    List<UsersByRol> findUsersByRol(@Param("rolName")String rolName);

    @Modifying
    @Transactional
    @Query(value = "update user set status = :status where id_user = :idUser",nativeQuery = true)
    int deactivateUser(@Param("status") int status,@Param("idUser") Long idUser);

}
