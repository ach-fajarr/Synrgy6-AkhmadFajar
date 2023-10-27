package id.achfajar.challenge4.controller;

import id.achfajar.challenge4.model.Users;
import id.achfajar.challenge4.service.UsersService;
import id.achfajar.challenge4.view.ErrorView;
import id.achfajar.challenge4.view.UsersView;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.UUID;

@Component
public class UsersController {
    @Autowired
    UsersService usersService;

    @Getter
    @Setter
    public Users activeUser;
    UsersView viewU = new UsersView();
    BinarFudController c = new BinarFudController();
    private final static Logger logger = LoggerFactory.getLogger(UsersController.class);


    public boolean loginUser(){
        viewU.headerInfo("Gunakan email dan password anda");
        viewU.fieldEmail();
        String mail = c.inputLine();
        viewU.fieldPassword();
        String pass = c.inputLine();
        try {
            UUID id = usersService.login(mail, pass);
            Users user = usersService.getUsersById(id);
            setActiveUser(user);
            return true;
        } catch (Exception e){
            return false;
        }
    }
    public boolean loginUser2(){
        viewU.headerInfo("Gunakan email dan password anda");
        viewU.fieldEmail();
        String mail = c.inputLine();
        viewU.fieldPassword();
        String pass = c.inputLine();
        Users user = usersService.getUserByMail(mail);
        if (user != null && usersService.getUserByMailAndPass(mail, pass) != null) {
            setActiveUser(user);
            return true;
        } else {
            System.out.println(user == null ? "Email belum terdaftar" : "Password yang anda masukkan salah");
            return false;
        }
    }
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
                logger.error("catch error: {}", e.getMessage());
            }
        }
    }
    public void addUser() {
        usersService.createUser();
    }

}
