package com.utez.geco.service.Incidence;

import com.utez.geco.DTO.Incidence.IncidenceUser;
import com.utez.geco.model.Incidence;
import com.utez.geco.repository.Incidence.IncidenceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class IncidenceServiceImpl extends Incidence {

    String msg = "";
    @Autowired
    private IncidenceRepository incidenceRepository;

    public List<IncidenceUser> findAll(){return incidenceRepository.getIncidences();}

    public IncidenceUser findByIdRoom(Long id){return incidenceRepository.getIncidenceByRoomId(id);}

    public IncidenceUser findByRoomIndentifier(String identifier){return incidenceRepository.getIncidenceByRoomIdentifier(identifier);}
    public IncidenceUser findByidIncidence(Long idIncidence){return incidenceRepository.getIncidenceByIdIncidence(idIncidence);}

    public Incidence register(Incidence incidence){
        return incidenceRepository.saveIncidence(incidence.getDescription(),incidence.getImage(),incidence.getStatus());

    }

    public Incidence  save(Incidence incidence){
        return incidenceRepository.save(incidence);
    }

    public  int assignIncidenceUser(Long idUser,Long idIncidence){
        return incidenceRepository.assignIncidenceUser(idUser,idIncidence);
    }
    public  int assignIncidenceRoom(Long idRoom,Long idIncidence){
        return incidenceRepository.assignIncidenceRoom(idRoom,idIncidence);
    }

    public String updateStatus(Long idIncidence,
                            int status){
        if(incidenceRepository.getIncidenceByIdIncidence(idIncidence) != null){
            if(incidenceRepository.updateStatus(status,idIncidence) >= 1){
                msg = "Updated";
            }else{
                msg = "NotUpdated";
            }
        }else{
            msg = "NotFound";
        }
        return msg;
    }
}
