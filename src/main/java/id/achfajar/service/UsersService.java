package id.achfajar.service;

import id.achfajar.controller.Controller;
import id.achfajar.model.Users;

import static id.achfajar.model.Data.users;

public class UsersService {
    private static Users loggedInUser;
    public static Users getLoggedInUser() {
        return loggedInUser;
    }
    public static void setLoggedInUser(Users user) {
        loggedInUser = user;
    }
    public static Boolean userLogin(String email, String password){
        for (Users user : users) {
            if (email.equals(user.getEmail_address()) && password.equals(user.getPassword())) {
                setLoggedInUser(user);
                Controller.home();
                return true;
            }
        }
        return false;
    }
}
