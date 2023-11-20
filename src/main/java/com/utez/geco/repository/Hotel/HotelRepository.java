package com.utez.geco.repository.Hotel;

<<<<<<< Updated upstream
=======
import com.utez.geco.DTO.User.UserHotel;
>>>>>>> Stashed changes
import com.utez.geco.model.Hotel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository  extends JpaRepository<Hotel,Long>{
    Hotel findByIdHotel(Long id);
    Hotel findByName(String name);
    @Modifying
    @Transactional
    @Query(value = "update hotel set color_font = :colorfont,color_primary = :colorprimary," +
            "color_secondary = :colorsecondary ,color_tertiary = :colortertiary,name = :name where id_hotel = :idHotel",nativeQuery = true)
    int updateHotel(@Param("colorfont")String colorf,@Param("colorprimary")String colorprimary,  @Param("colorsecondary")String colorsecondary,
                    @Param("colortertiary")String colortertiary,@Param("name")String name,@Param("idHotel")Long id);
//    @Query(value = "select h.id_hotel,h.color_font,h.color_primary,h.color_secondary,h.color_tertiary,h.name,u.* from hotel h\n" +
//            "join user u on u.id_user = h.id_user\n" +
//            "where h.id_hotel = :idHotel",nativeQuery = true)
//    UserHotel findHotelWithUser(@Param("idHotel")Long id);


    @Modifying
    @Transactional
    @Query(value = "insert into user_hotel values(:idUser,:idHotel)",nativeQuery = true)
    int asisgnHotelUser(@Param("idUser")Long idUser,@Param("idHotel")Long idHotel);

}
