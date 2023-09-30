package id.achfajar.service;

import id.achfajar.controller.Controller;
public class AppService {
    public static boolean continueApp = true;
    public void run(){
        Controller.home();
    }
}
