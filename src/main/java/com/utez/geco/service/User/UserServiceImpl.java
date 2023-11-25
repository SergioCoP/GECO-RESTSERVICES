package com.utez.geco.service.User;

import com.utez.geco.DTO.Role.RoleDTO;
import com.utez.geco.DTO.User.AllUsersDTO;
import com.utez.geco.DTO.User.UserById;
import com.utez.geco.DTO.User.UsersByRol;
import com.utez.geco.DTO.User.UsersDTO;
import com.utez.geco.model.Person;
import com.utez.geco.model.User;
import com.utez.geco.repository.User.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAll(){return userRepository.findAll();}

    public UsersDTO findByEmail(String email){return userRepository.findByEmail(email);}
    public UserById findById(Long id){return userRepository.findByIdUser(id);}
    public List<AllUsersDTO> findAllUsers(){return userRepository.findAllUsers();}
    public int assignRolToUser(Long idUser,Long idRol){return userRepository.assignRolToUser(idRol,idUser);}
    public RoleDTO findRolById(Long idRol){return userRepository.findRolById(idRol);}
    public List<UsersByRol> findUsersByRol(String rolName){return  userRepository.findUsersByRol(rolName);}
    public User findByEmailAndPassword(String email, String password){return userRepository.findByEmailAndPassword(email,password);}
    public int deactivateUser(Long idUser,int status){
        return userRepository.deactivateUser(status,idUser);}
    public User register(User user){
        return userRepository.save(user);}
    public int registerWithoutRol(User user){
        return userRepository.registerUser(
                user.getIdPerson().getName(),
                user.getIdPerson().getSurname(),
                user.getIdPerson().getLastname(),
                user.getIdPerson().getTurn(),
                user.getEmail(),
                user.getPassword()
        );
    }


}
