package id.achfajar.challenge4.service;

import id.achfajar.challenge4.controller.BinarFudController;
import id.achfajar.challenge4.model.Users;
import id.achfajar.challenge4.repository.UsersRepository;
import id.achfajar.challenge4.view.UsersView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsersService {
    @Autowired
    UsersRepository usersRepository;
    BinarFudController c = new BinarFudController();
    UsersView view = new UsersView();

    public void createUser() {
        view.headerInfo("Pendaftaran pengguna baru");
        view.fieldEmail();
        String email = c.inputLine();
        view.fieldUserName();
        String uName = c.inputLine();
        view.fieldPassword();
        String pass = c.inputLine();
        try {
            addUser(email, pass, uName);
            view.infoSuccess();
        } catch (Exception e) {
            view.userExists();
        }
    }
    public void updateUser(Users activeU) {
        view.updateUser();
        view.fieldEmail();
        String email = c.inputLine();
        view.fieldUserName();
        String uName = c.inputLine();
        view.fieldPassword();
        String pass = c.inputLine();
        updateUser(activeU.getId(), email, pass, uName);
    }
    public void deleteUser(Users user){
        usersRepository.delete(user);
        UsersView.delSuccess();
    }
    //================================================================================
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
