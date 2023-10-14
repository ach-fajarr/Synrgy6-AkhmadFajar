package id.achfajar;

import id.achfajar.service.AppService;

public class Main {
    public static void main(String[] args) {
        AppService appService = new AppService();
        appService.run();
    }
}