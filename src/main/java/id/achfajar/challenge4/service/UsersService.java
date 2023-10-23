package id.achfajar.challenge4.service;

import id.achfajar.challenge4.controller.BinarFudController;
import id.achfajar.challenge4.model.Users;
import id.achfajar.challenge4.repository.UsersRepository;
import id.achfajar.challenge4.view.ErrorView;
import id.achfajar.challenge4.view.UsersView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {
    @Autowired
    UsersRepository usersRepository;
    UsersView viewU = new UsersView();
    BinarFudController c = new BinarFudController();


    public void editNameByMail(String mail, String newName){
        usersRepository.updateNameByMail(mail, newName);
    }
    public void editMailByMail(String mail, String newMail){
        usersRepository.updateMailByMail(mail, newMail);
    }
    public void editPassByMail(String mail, String newPass){
        usersRepository.updateMailByMail(mail, newPass);
    }
    public void addUser(){
        viewU.headerReg();
        viewU.fieldEmail();
        String email = c.inputLine();
        viewU.fieldUserName();
        String uName = c.inputLine();
        viewU.fieldPassword();
        String pass = c.inputLine();

        //save repo
        Users user = new Users();
        user.setEmail(email);
        user.setUsername(uName);
        user.setPassword(pass);
        usersRepository.save(user);
    }
    public void deleteUser(Users user){
        usersRepository.delete(user);
    }
    public void updateUser(Users user) {
        viewU.fieldEmail();
        String email = c.inputLine();
        viewU.fieldUserName();
        String uName = c.inputLine();
        viewU.fieldPassword();
        String pass = c.inputLine();

        user.setEmail(email);
        user.setUsername(uName);
        user.setPassword(pass);
        usersRepository.save(user);
    }
    public Users loginUser(){
        viewU.headerLogin();
        viewU.fieldEmail();
        String mail = c.inputLine();
        viewU.fieldPassword();
        String pass = c.inputLine();
        Users user = getUserByMailAndPass(mail, pass);
        return user;
    }
    public List<Users> getUsers(){
        return usersRepository.findAll();
    }
    Users getUserByName(String name){
        return usersRepository.findByUsername(name);
    }
    Users getUserByMailAndPass(String mail, String pass){
        return usersRepository.findByEmailAndPassword(mail, pass);
    }

}
