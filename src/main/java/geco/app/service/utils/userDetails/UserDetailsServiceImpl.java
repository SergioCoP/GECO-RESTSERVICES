package geco.app.service.utils.userDetails;

import geco.app.service.model.User;
import geco.app.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository ur;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = ur.findByEmail(email);
        return new UserDetailsImpl(user);
    }
}
