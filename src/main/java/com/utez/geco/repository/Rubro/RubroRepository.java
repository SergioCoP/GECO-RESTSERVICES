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


    @Query(value = "select id_rubro as idRubro, description as name from rubro where description like %:rubro%",nativeQuery = true)
    RubroGetDTO findByName(@Param("rubro")String rubro);
}
