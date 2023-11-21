package com.utez.geco.service.Hotel;


import com.utez.geco.DTO.Hotel.HotelGetDTO;
import com.utez.geco.DTO.User.UserHotel;
import com.utez.geco.model.Hotel;
import com.utez.geco.repository.Hotel.HotelRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class HotelServiceImpl extends Hotel {

    @Autowired
    private HotelRepository hotelRepository;

    public List<HotelGetDTO> findAll(){return hotelRepository.findAllHotels();}
    public HotelGetDTO findById(Long id){return hotelRepository.findByIdHotel(id);}

    public Hotel findByName(String name){return hotelRepository.findByName(name);}
    public Hotel register(Hotel hotel){return hotelRepository.save(hotel);}
    public int update(String colorfont,String colorprimary,
                        String colorsecondary,String colortertiary,
                        String name,Long id){
        return hotelRepository.updateHotel(
                colorfont,colorprimary,colorsecondary,
                colortertiary,name,id
        );
    }

    public int assignHotelUser(Long idUser,Long idHotel){
        return hotelRepository.asisgnHotelUser(idUser,idHotel);
    }

    public Hotel updateAll(Hotel hotel){return  hotelRepository.save(hotel);}
    public void delete(Long id){hotelRepository.deleteById(id);}
}
