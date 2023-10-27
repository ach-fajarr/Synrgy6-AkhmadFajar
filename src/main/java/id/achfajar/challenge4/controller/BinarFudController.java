package id.achfajar.challenge4.controller;

import id.achfajar.challenge4.model.Users;
import id.achfajar.challenge4.view.*;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

@Component
public class BinarFudController {

    @Autowired
    UsersController usersController;
    @Autowired
    MerchantController merchantController;
    @Autowired
    OrderController orderController;


    @Setter
    protected Users activeUser;
    private final static Logger logger = LoggerFactory.getLogger(BinarFudController.class);

    public void initiateTypeProductData(){
        merchantController.initiateData();
    }
    public void welcome(){
        boolean active = true;
        while (active){
            try {
                GeneralView.welcome();
                int option = inputInt();
                switch (option){
                    case 1 -> loginUserWithPostgre();
                    case 2 -> usersController.addUser();
                    case 3 -> loginUserWithJPA();
                    case 0 -> active=false;
                    default -> ErrorView.wrongInput();
                }
            } catch (InputMismatchException e) {
                ErrorView.wrongInput();
                logger.error("catch error: {}", e.getMessage());
            }
        }
    }

    private void loginUserWithPostgre() {
        boolean login = usersController.loginUser();
        if (login){
            setActiveUser(usersController.getActiveUser());
            logger.info("User yang sedang login: {}", activeUser);
            orderController.setSetFilter(0);
            home();
        }
    }
    private void loginUserWithJPA() {
        boolean login = usersController.loginUser2();
        if (login){
            setActiveUser(usersController.getActiveUser());
            logger.info("User yang sedang login: {}", activeUser);
            orderController.setSetFilter(0);
            home();
        }
    }
    //====================================================================================
    public void home(){
        boolean active = true;
        while (active){
            try {
                GeneralView.home(usersController.activeUser);
                int option = inputInt();
                switch (option){
                    case 1 -> orderController.order(activeUser);
                    case 2 -> orderController.historyOrder(activeUser);
                    case 3 -> merchantController.merchant(activeUser);
                    case 4 -> {usersController.userSetting();setActiveUser(usersController.getActiveUser());}
                    case 5 -> {setActiveUser(null); merchantController.clearMap(); active=false; welcome();}
                    default -> ErrorView.wrongInput();
                }
            } catch (InputMismatchException e) {ErrorView.wrongInput();}
        }
    }

    //====================================================================================

    public String setCurrency (double price){
        Locale locale = new Locale("id", "ID");

        DecimalFormatSymbols sb = new DecimalFormatSymbols(locale);
        sb.setCurrencySymbol("Rp ");
        sb.setGroupingSeparator('.');
        sb.setDecimalSeparator(',');
        DecimalFormat fd = new DecimalFormat("Rp#,##0.00", sb);

        return fd.format(price);
    }
    public String formatTime(LocalDateTime localDateTime) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy/MM/dd HH.mm");
        return localDateTime.format(fmt);
    }
    public String inputLine(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    public int inputInt(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}
