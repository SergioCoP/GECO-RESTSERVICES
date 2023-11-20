package com.utez.geco.service.User;

import com.utez.geco.DTO.User.UsersDTO;
import com.utez.geco.model.User;
import com.utez.geco.repository.User.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements IUserService{
    @Autowired
    private UserRepository userRepository;

    public List<User> findAll(){return userRepository.findAll();}

    public Optional<User> findByEmail(String email){return userRepository.findByEmail(email);}
    public UsersDTO findById(Long id){return userRepository.findByIdUser(id);}
    public List<UsersDTO> findAllUsers(){return userRepository.findAllUsers();}

    public User findByEmailAndPassword(String email, String password){return userRepository.findByEmailAndPassword(email,password);}
    public User register(User user){
        return userRepository.save(user);}

    @Override
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("User not found")) ;
            }
        };
    }

}
