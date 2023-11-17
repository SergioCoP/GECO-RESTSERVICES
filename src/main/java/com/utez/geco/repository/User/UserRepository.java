package com.utez.geco.repository.User;

import com.utez.geco.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
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

    //User findByEmail(String email);
    Optional<User> findByEmail(String username);

    User findByIdUser(Long id);
    @Query(value = "select u.*,p.name,p.surname,p.lastname from user u\n" +
            "inner join person p on p.id_person = u.id_person;", nativeQuery = true)
    List<User> findAllUsers();
}
