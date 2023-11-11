package com.utez.geco.controller;

import com.google.api.client.json.Json;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.utez.geco.DTO.UserHotel;
import com.utez.geco.model.Hotel;
import com.utez.geco.model.User;
import com.utez.geco.service.Hotel.HotelServiceImpl;
import com.utez.geco.service.Image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/hotel")
@CrossOrigin(origins = "*")
public class HotelController {
    String msg = "";

    @Autowired
    private HotelServiceImpl hotelService;

    @GetMapping("/getAllHotels")
    @ResponseBody
    public List<Hotel> getAllHotels(){return hotelService.findAll();}


    @GetMapping("/getHotel")
    @ResponseBody
    public Hotel getHotelById(@RequestParam("idHotel")Long id){
        return hotelService.findById(id) != null ? hotelService.findById(id): null;
    }

    @PostMapping("/saveHotel")
    @ResponseBody
    public String saveHotel(@RequestBody Hotel nhotel,@RequestParam("idUser")Long id){
        if(!new Gson().toJson(nhotel).equals("null")){
            if(new Gson().toJson(hotelService.findByName(nhotel.getName())).equals("null") ){
                if(hotelService.register(nhotel)  != null){
                    Hotel sHot = hotelService.findByName(nhotel.getName());
                    if(sHot != null){
                        msg = hotelService.assignHotelUser(sHot.getIdHotel(),id) >= 1 ? "Register": "NotRegister";
                    }
                }
            }else{
                msg = "Exist";
            }
        }else {
            msg = "Empty";
        }
        return msg;
    }

    //Falta la insercion en table user_hotel para relacionar elhotel xd

    @PutMapping("/updateHotel")
    @ResponseBody
    public String updateHotel(@RequestBody Hotel nhotel){
        if(!new Gson().toJson(nhotel).equals("null")){
            if(!new Gson().toJson(hotelService.findById(nhotel.getIdHotel())).equals("null")){
                msg = hotelService.updateAll(nhotel) != null ? "Update" : "NotUpdated";
            }else{
                msg = "NotExist";
            }
        }else{
            msg = "Empty";
        }


        return msg;
    }

    @DeleteMapping("/deleteHotel")
    @ResponseBody
    public String deleteHotel(@RequestParam("idRoom") Long id){
        if(!new Gson().toJson(hotelService.findById(id)).equals("null")){
            hotelService.delete(id);
            return "Deleted";
        }else{
            return "NotFound";
        }
    }
}
