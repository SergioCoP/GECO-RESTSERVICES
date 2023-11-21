package com.utez.geco.service.Rubro;

import com.utez.geco.DTO.Rubro.RubroGetDTO;
import com.utez.geco.model.Rubro;
import com.utez.geco.repository.Rubro.RubroRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RubroServiceImpl {
    @Autowired
    private RubroRepository rubroRepository;

    public List<Rubro> findAll(){return rubroRepository.findAll();}
    public Rubro findById(Long id) {return rubroRepository.findByIdRubro(id);}
    public Rubro register(Rubro rubro){return rubroRepository.save(rubro);}
    public Rubro update(Rubro rubro){return rubroRepository.save(rubro);}
    public void delete(Long id){rubroRepository.deleteById(id);}
    public RubroGetDTO findByName(String name){return rubroRepository.findByName(name);}
}
