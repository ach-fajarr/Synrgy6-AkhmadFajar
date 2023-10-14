package id.achfajar.controller;

import id.achfajar.service.*;
import id.achfajar.utils.Utils;
import id.achfajar.view.ErrorView;
import id.achfajar.view.MenuView;
import id.achfajar.view.ReceiptView;

import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import static id.achfajar.model.Data.orderDetails;
import static id.achfajar.service.OrderService.getAllOrderItems;
import static id.achfajar.service.OrderService.printGrandTotal;

public class Controller {
    public static void home(){
        String userName = UsersService.getLoggedInUser().getUsername();
        MenuView.welcome(userName);
        ProductService.printProduct();
        MenuView.menu();
        MenuView.inputOption();
        selectMenu();
    }
    private static void selectMenu(){
        while (AppService.continueApp) {
            try {
                byte input = inputOption();
                switch (input){
                    case 0 -> AppService.continueApp=false;
                    case 98 -> handleOrder();
                    case 99 -> OrderService.orderHistory();
                    default -> OrderDetailService.processOrder(input);
                }
            } catch (InputMismatchException e) {errorView();}
        }
    }
    public static void handleOrder(){
        if (orderDetails.isEmpty()) {
            ErrorView.showOrderIsEmpty();
            ErrorView.reInputOption();
        }
        else {
            OrderService.session();}
    }
    public static void confirmOrderSelectMenu(){

        while (AppService.continueApp) {
            try {
                int selectedMenu = inputOption();
                switch (selectedMenu) {
                    case 1 -> checkOrderToPrint();
                    case 2 -> home();
                    case 3 -> cancelOrder();
                    case 0 -> AppService.continueApp=false;
                    default -> errorView();
                }
            } catch (InputMismatchException e){errorView();}
        }
    }

    protected static void checkOrderToPrint(){
        if (orderDetails.isEmpty()) {
            ErrorView.failedToPrintReceipt();
        }
        else {
            saveOrderAsReceipt();
            OrderService.sessionCompleted();
            }
    }
    private static void cancelOrder(){
        MenuView.cancel();
        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;
        while (!validInput) {
            try {
                String a = scanner.nextLine();
                if (a.equals("y")) {
                    OrderService.sessionCanceled();
                    break;
                } else if (a.equals("n")) {
                    OrderService.confirmOrder();
                    break;
                } else {
                    errorView();
                }
            } catch (InputMismatchException e){errorView();}
        }
    }
    protected static void saveOrderAsReceipt(){
        String receiptName = "Receipt"+Utils.getTimeNow()+".txt";
        try (FileWriter writer = new FileWriter(receiptName);){
            writer.write(ReceiptView.receiptHeader());
            writer.write(getAllOrderItems()+"\n");
            writer.write(printGrandTotal()+"\n");
            writer.write(NoteService.getAllNotesItems()+"\n \n");
            writer.write(ReceiptView.receiptFooter());
            writer.flush();
            ReceiptView.receiptSuccess();
        } catch (IOException e) {
            e.printStackTrace();
            ErrorView.receiptError();
        }
    }

    public static void clearOrderItems() {
        orderDetails.clear();
    }
    public static void errorView(){
        ErrorView.wrongInputOption();
        ErrorView.reInputOption();
    }
    public static byte inputOption() {
        Scanner scanner = new Scanner(System.in);
        return (byte) scanner.nextInt();
    }

}