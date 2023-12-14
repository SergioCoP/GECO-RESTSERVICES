package geco.app.service.repository;

import geco.app.service.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findAll();
    Hotel findByIdHotel(long id);
    Hotel save(Hotel hotel);
    @Query(value = "SELECT id_hotel FROM hotel ORDER BY id_hotel DESC LIMIT 1", nativeQuery = true)
    long findLastId();
}
