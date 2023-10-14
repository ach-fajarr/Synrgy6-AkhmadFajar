package id.achfajar.service;

public class AppService {
    public static boolean continueApp = true;
    public void run(){
        UsersService.userLogin("user1@gmail.com","123");
    }
}
