package id.achfajar.challenge7.security.service;

import id.achfajar.challenge7.model.Users;
import id.achfajar.challenge7.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsersRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: "+username));

        return UserDetailsImpl.build(user);
    }

    public void createUserPostLogin(String name, String email) {
        userRepository.findByUsername(email)
                .orElse(new Users().setFirstName(name).setEmail(email).setUsername(email));
    }
}
