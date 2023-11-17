package com.utez.geco.repository.Role;

import com.utez.geco.model.Role;
import com.utez.geco.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    @Query(value =  "select r.name FROM rol r\n" +
            "join user_rol ur on r.id_rol = ur.id_rol\n" +
            "join user u on ur.id_user = u.id_user\n" +
            "where u.id_user = :idUser;",nativeQuery = true)
    String findRoleByIdUser(@Param("idUser")Long idUser);
}
