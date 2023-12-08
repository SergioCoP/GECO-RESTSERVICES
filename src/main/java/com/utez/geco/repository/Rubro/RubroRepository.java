package com.utez.geco.repository.Rubro;

import com.utez.geco.DTO.Rubro.RubroGetDTO;
import com.utez.geco.model.Rubro;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RubroRepository extends JpaRepository<Rubro,Long> {
    Rubro findByIdRubro(Long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM room_rubro where idRubro = :idRubro",nativeQuery = true)
    int delete(@Param("idRubro") Long id);




    @Modifying
    @Transactional
    @Query(value = "UPDATE rubro SET status = :state where id_rubro = :idRubro",nativeQuery = true)
    int changueState(@Param("idRubro")Long idRubro,@Param("state")int state);

    @Query(value = "INSERT INTO category_rubro(id_rubro,id_category) values(:idRubro,:idCategory)",nativeQuery = true)
    int assignRubroToCategory(@Param("idRubro")Long idRubro,@Param("idCategory")Long idCategory);
    @Query(value = "select id_rubro as idRubro, description,status from rubro where description like %:rubro%",nativeQuery = true)
    RubroGetDTO findByName(@Param("rubro")String rubro);
    @Query(value = "select COALESCE((select 'true' from room_rubro where id_room = :idRoom limit 1 ),\n" +
            "    'false') as exist;",nativeQuery = true)
    String verifyRoomWithRubro(@Param("idRoom") Long idRoom);
}
