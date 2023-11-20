package com.utez.geco.repository.User;

import com.utez.geco.DTO.User.UsersDTO;
import com.utez.geco.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;


import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByEmailAndPassword(String email, String password);


    @Query(value = "select u.id_user as idUser,u.email,concat(p.name, ' ', p.surname, ' ', p.lastname) as userName,p.turn from user u " +
                      "join person p on p.id_person = u.id_person where u.email = :email",nativeQuery = true)
    UsersDTO findByEmail(@Param("email") String email);

    @Query(value = "select u.id_user as idUser,u.email,concat(p.name, ' ', p.surname, ' ', p.lastname) as userName,p.turn from user u" +
            "join person p on p.id_person = u.id_person where u.id_user = :idUser",nativeQuery = true)
    UsersDTO findByIdUser(@Param("idUser") Long idUser);
    @Query(value = "select u.id_user as idUser,u.email,concat(p.name, ' ', p.surname, ' ', p.lastname) as userName,p.turn from user u" +
            "join person p on p.id_person = u.id_person;;", nativeQuery = true)
    List<UsersDTO> findAllUsers();

}
