package id.achfajar.challenge4.controller;

import id.achfajar.challenge4.model.Users;
import id.achfajar.challenge4.service.UsersService;
import id.achfajar.challenge4.view.UsersView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UsersController {
    @Autowired
    UsersService usersService;
    UsersView viewU = new UsersView();
    BinarFudController c = new BinarFudController();

    public void addUser() {
        viewU.headerInfo("Pendaftaran pengguna baru");
        viewU.fieldEmail();
        String email = c.inputLine();
        viewU.fieldUserName();
        String uName = c.inputLine();
        viewU.fieldPassword();
        String pass = c.inputLine();
        try {
            usersService.addUser(email, pass, uName);
            viewU.infoSuccess();
        } catch (Exception e) {
            viewU.userExists();
        }
    }
    public Users loginUser(){
        viewU.headerInfo("Gunakan email dan password anda");
        viewU.fieldEmail();
        String mail = c.inputLine();
        viewU.fieldPassword();
        String pass = c.inputLine();
        return usersService.getUserByMailAndPass(mail, pass);
    }
    public void updateUser(Users activeU) {
        viewU.updateUser();
        viewU.fieldEmail();
        String email = c.inputLine();
        viewU.fieldUserName();
        String uName = c.inputLine();
        viewU.fieldPassword();
        String pass = c.inputLine();
        usersService.updateUser(activeU.getId(), email, pass, uName);
    }
    public void deleteUser(Users user){
        usersService.deleteUser(user);
        UsersView.delSuccess();
    }
    public Users getUserByID(UUID id){
        return usersService.getUsersById(id);
    }
}
