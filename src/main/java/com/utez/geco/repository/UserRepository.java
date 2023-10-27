package com.utez.geco.repository;

import com.utez.geco.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    User findByEmailAndPassword(String email, String password);
    User findByEmail(String email);

    @Query(value = "select u.*,p.age,p.name,p.surname,p.lastname from user u\n" +
            "inner join person p on p.id_person = u.id_person;", nativeQuery = true)
    List<User> findAllUsers();
}
