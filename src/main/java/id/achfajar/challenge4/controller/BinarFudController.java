package id.achfajar.challenge4.controller;

import id.achfajar.challenge4.model.Users;
import id.achfajar.challenge4.service.MerchantService;
import id.achfajar.challenge4.service.OrderService;
import id.achfajar.challenge4.service.UsersService;
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
    UsersService usersService;
    @Autowired
    MerchantService merchantService;
    @Autowired
    OrderService orderService;

    @Setter
    protected Users activeUser;
    GeneralView viewG = new GeneralView();
    ErrorView viewE = new ErrorView();
    UsersView viewU = new UsersView();
    MerchantView viewM = new MerchantView();
    OrderView viewO = new OrderView();


    public void welcome(){
        viewG.welcome();
        try {
            int option = inputInt();
            switch (option){
                case 1 -> loginUser();
                case 2 -> register();
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
        Users user = usersService.loginUser();
        if (user == null){
            viewE.errorUserNotFound();
            welcome();
        } else {
            setActiveUser(user);
            home();
        }
    }
    private void register(){
        usersService.addUser();
        viewU.infoSuccess();
        welcome();
    }
    //====================================================================================
    public void home(){
        viewG.home(activeUser);
        try {
            int option = inputInt();
            switch (option){
                case 1 -> order();
                case 2 -> orderList();
                case 3 -> merchant();
                case 4 -> userSetting();
                case 5 -> logout();
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

    private void orderList() {
        orderService.historyOrder();
        home();
    }

    private void logout() {
        setActiveUser(null);
        merchantService.clearMap();
        welcome();
    }
    //====================================================================================
    private void order() {
        viewO.orderHeader();
        orderService.mapAllProduct();
        orderService.printProduct();
        viewO.orderOption();
        try {
            int option = inputInt();
            switch (option){
                case 99 -> confirmOrder();
                case 0 -> home();
                default -> createOrderDetail(option);
            }
        } catch (InputMismatchException e) {
            viewE.wrongInput();
            order();
        }
    }
    private void createOrderDetail(int option) {
        orderService.createOrderDetail(option);
        order();
    }
    private void confirmOrder() {
        viewO.confirmHeader();
        orderService.printOrderDetail();
        viewO.confirmOption();
        try {
            int option = inputInt();
            switch (option){
                case 1 -> confirmThisOrder();
                case 2 -> cancelThisOrder();
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

    private void confirmThisOrder() {
        orderService.confirmThisOrder(activeUser);
        order();
    }

    private void cancelThisOrder() {
        orderService.cancelOrder(activeUser);
        order();
    }

    //====================================================================================
    private void merchant() {
        viewM.merchantHeader();
        merchantService.mapAllbyUser(activeUser);
        merchantService.printMerchant();
        viewM.merchantOption();
        try {
            int option = inputInt();
            switch (option){
                case 1 -> addMerchant();
                case 2 -> addProduct();
                case 3 -> updateMerchantName();
                case 4 -> updateMerchantLoc();
                case 5 -> setOpen();
                case 6 -> home();
                default -> {
                    viewE.wrongInput();
                    merchant();
                }
            }
        } catch (InputMismatchException e) {
            viewE.wrongInput();
            merchant();
        }
    }
    private void addMerchant() {
        merchantService.addMerchant(activeUser);
        merchant();
    }
    private void updateMerchantName() {
        viewM.merchantHeader();
        merchantService.printMerchant();
        viewM.productOption();
        try {
            int option = inputInt();
            switch (option){
                case 0 -> merchant();
                default -> updateName(option);
            }
        } catch (InputMismatchException e) {
            viewE.wrongInput();
            updateMerchantName();
        }
    }
    private void updateMerchantLoc() {
        viewM.merchantHeader();
        merchantService.printMerchant();
        viewM.productOption();
        try {
            int option = inputInt();
            switch (option){
                case 0 -> merchant();
                default -> updateLoc(option);
            }
        } catch (InputMismatchException e) {
            viewE.wrongInput();
            updateMerchantLoc();
        }
    }
    private void setOpen() {
        viewM.merchantHeader();
        merchantService.printMerchant();
        viewM.productOption();
        try {
            int option = inputInt();
            switch (option){
                case 0 -> merchant();
                default -> updateOpen(option);
            }
        } catch (InputMismatchException e) {
            viewE.wrongInput();
            setOpen();
        }
    }

    private void updateOpen(int option) {
        merchantService.updateMerchantStatus(option);
        setOpen();
    }
    private void updateLoc(int option) {
        merchantService.updateMerchantLoc(option);
        updateMerchantLoc();
    }
    private void updateName(int option) {
        merchantService.updateMerchantName(option);
        updateMerchantName();
    }

    private void addProduct() {
        viewM.productHeader();
        merchantService.printMerchant();
        viewM.productOption();
        try {
            int option = inputInt();
            switch (option){
                case 0 -> merchant();
                default -> newProduct(option);
            }
        } catch (InputMismatchException e) {
            viewE.wrongInput();
            addProduct();
        }
    }
    private void newProduct(int option) {
        merchantService.createProduct(option);
        merchant();
    }
    //====================================================================================
    private void userSetting() {
        viewU.userSetting(activeUser);
        try {
            int option = inputInt();
            switch (option){
                case 1 -> updateUser();
                case 2 -> deleteUser();
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
    private void updateUser(){
        usersService.updateUser(activeUser);
        userSetting();
    }
    private void deleteUser() {
        usersService.deleteUser(activeUser);
        viewU.delSuccess();
        welcome();
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
    public LocalDateTime formatTime(LocalDateTime localDateTime) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy/MM/dd HH.mm");
        return LocalDateTime.parse(localDateTime.format(fmt));
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
