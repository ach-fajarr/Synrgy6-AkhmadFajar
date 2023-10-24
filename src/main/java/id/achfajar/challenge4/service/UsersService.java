package id.achfajar.challenge4.service;

import id.achfajar.challenge4.model.Users;
import id.achfajar.challenge4.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsersService {
    @Autowired
    UsersRepository usersRepository;


    public void deleteUser(Users user){
        usersRepository.delete(user);
    }
    public void addUser(String mail, String pass, String name){
        usersRepository.addUser(mail, pass, name);
    }
    public void updateUser(UUID uuid, String mail, String pass, String name){
        usersRepository.updateUser(uuid, mail, pass, name);
    }
    public Users getUserByMailAndPass(String mail, String pass){
        return usersRepository.findByEmailAndPassword(mail, pass);
    }
    public List<Users> getUsers(){
        return usersRepository.findAll();
    }
    public Users getUsersById(UUID userId) {
        Optional<Users> optionalUser = usersRepository.findById(userId);
        return optionalUser.orElse(null);
    }
}
