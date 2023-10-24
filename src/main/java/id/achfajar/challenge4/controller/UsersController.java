package id.achfajar.challenge4.controller;

import id.achfajar.challenge4.model.Users;
import id.achfajar.challenge4.service.UsersService;
import id.achfajar.challenge4.view.ErrorView;
import id.achfajar.challenge4.view.UsersView;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;

@Component
public class UsersController {
    @Autowired
    UsersService usersService;

    @Getter
    @Setter
    public Users activeUser;
    UsersView viewU = new UsersView();
    BinarFudController c = new BinarFudController();


    public void userSetting() {
        boolean active = true;
        while (active){
            try {
                UsersView.userSetting(activeUser);
                int option = c.inputInt();
                switch (option){
                    case 1 -> {
                        usersService.updateUser(activeUser);
                        activeUser = usersService.getUsersById(activeUser.getId());}
                    case 2 -> usersService.deleteUser(activeUser);
                    case 3 -> active=false;
                    default -> ErrorView.wrongInput();
                }
            } catch (InputMismatchException e) {
                ErrorView.wrongInput();
            }
        }
    }
    public boolean loginUser(){
        viewU.headerInfo("Gunakan email dan password anda");
        viewU.fieldEmail();
        String mail = c.inputLine();
        viewU.fieldPassword();
        String pass = c.inputLine();
        Users user = usersService.getUserByMailAndPass(mail, pass);
        if (user == null){
            return false;
        } else {
            setActiveUser(user);
            return true;
        }
    }
    public void addUser() {
        usersService.createUser();
    }
}
