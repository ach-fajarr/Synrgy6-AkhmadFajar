package id.achfajar.challenge4.controller;

import id.achfajar.challenge4.model.Users;
import id.achfajar.challenge4.view.*;
import lombok.Setter;
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
    ErrorView viewE = new ErrorView();
    MerchantView viewM = new MerchantView();
    OrderView viewO = new OrderView();


    public void welcome(){
        GeneralView.welcome();
        try {
            int option = inputInt();
            switch (option){
                case 1 -> loginUser();
                case 2 -> {usersController.addUser();welcome();}
                default -> {
                    viewE.wrongInput();
                    welcome();
                }
            }
        } catch (InputMismatchException e) {
            viewE.wrongInput();
            welcome();
        }
    }
    private void loginUser() {
        Users user = usersController.loginUser();
        if (user == null){
            viewE.errorUserNotFound();
            welcome();
        } else {
            setActiveUser(user);
            home();
        }
    }
    //====================================================================================
    public void home(){
        GeneralView.home(activeUser);
        try {
            int option = inputInt();
            switch (option){
                case 1 -> order();
                case 2 -> {orderController.historyOrder(activeUser); home();}
                case 3 -> merchant();
                case 4 -> userSetting();
                case 5 -> {setActiveUser(null); merchantController.clearMap(); welcome();}
                default -> {
                    viewE.wrongInput();
                    home();
                }
            }
        } catch (InputMismatchException e) {
            viewE.wrongInput();
            home();
        }
    }

    //====================================================================================
    private void order() {
        viewO.orderHeader();
        orderController.mapAllProduct();
        orderController.printProduct();
        viewO.orderOption();
        try {
            int option = inputInt();
            switch (option){
                case 99 -> confirmOrder();
                case 0 -> home();
                default -> {orderController.createOrderDetail(option); order();}
            }
        } catch (InputMismatchException e) {
            viewE.wrongInput();
            order();
        }
    }

    private void confirmOrder() {
        viewO.confirmHeader();
        orderController.printOrderDetail();
        viewO.confirmOption();
        try {
            int option = inputInt();
            switch (option){
                case 1 -> {orderController.confirmThisOrder(activeUser); order();}
                case 2 -> {orderController.cancelOrder(activeUser); order();}
                case 0 -> order();
                default -> {
                    viewE.wrongInput();
                    confirmOrder();
                }
            }
        } catch (InputMismatchException e) {
            viewE.wrongInput();
            confirmOrder();
        }
    }

    //====================================================================================
    private void merchant() {
        viewM.merchantHeader();
        merchantController.printMerchant(activeUser);
        viewM.merchantOption();
        try {
            int option = inputInt();
            switch (option){
                case 1 -> {merchantController.addMerchant(activeUser);merchant();}
                case 2 -> addProduct();
                case 3 -> updateMerchant();
                case 4 -> setOpen();
                case 5 -> home();
                default -> {viewE.wrongInput(); merchant();}
            }
        } catch (InputMismatchException e) {
            viewE.wrongInput();
            merchant();
        }
    }
    private void updateMerchant() {
        viewM.merchantHeader();
        merchantController.printMerchant(activeUser);
        viewM.productOption();
        try {
            int option = inputInt();
            switch (option){
                case 0 -> merchant();
                default -> {merchantController.updateMerchant(option);updateMerchant();}
            }
        } catch (InputMismatchException e) {
            viewE.wrongInput();
            updateMerchant();
        }
    }

    private void setOpen() {
        viewM.merchantHeader();
        merchantController.printMerchant(activeUser);
        viewM.productOption();
        try {
            int option = inputInt();
            switch (option){
                case 0 -> merchant();
                default -> {merchantController.updateMerchantStatus(option); setOpen();}
            }
        } catch (InputMismatchException e) {
            viewE.wrongInput();
            setOpen();
        }
    }

    private void addProduct() {
        viewM.productHeader();
        merchantController.printMerchant(activeUser);
        viewM.productOption();
        try {
            int option = inputInt();
            switch (option){
                case 0 -> merchant();
                default -> merchantController.createProduct(option);
            }
        } catch (InputMismatchException e) {
            viewE.wrongInput();
            addProduct();
        }
    }
    //====================================================================================
    private void userSetting() {
        UsersView.userSetting(activeUser);
        try {
            int option = inputInt();
            switch (option){
                case 1 -> {
                    usersController.updateUser(activeUser);
                    activeUser = usersController.getUserByID(activeUser.getId());
                    userSetting();}
                case 2 -> {usersController.deleteUser(activeUser);welcome();}
                case 3 -> home();
                default -> {
                    viewE.wrongInput();
                    userSetting();
                }
            }
        } catch (InputMismatchException e) {
            viewE.wrongInput();
            userSetting();
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
