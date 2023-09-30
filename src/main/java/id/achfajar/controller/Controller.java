package id.achfajar.controller;

import id.achfajar.model.FoodItem;
import id.achfajar.model.Order;
import id.achfajar.service.AppService;
import id.achfajar.service.FoodService;
import id.achfajar.service.NoteService;
import id.achfajar.service.OrderService;
import id.achfajar.utils.Utils;
import id.achfajar.view.ErrorView;
import id.achfajar.view.MenuView;
import id.achfajar.view.ReceiptView;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import static id.achfajar.model.Data.orderItems;

public class Controller {
    public static void home(){
        MenuView.showMenu();
        MenuView.inputOption();
        selectMenu();
    }
    private static void selectMenu(){
        do {
            try {
                int selectedMenu = inputOption();
                switch (selectedMenu) {
                    case 1,2,3,4,5 -> selectFood(selectedMenu);
                    case 99 -> checkOrder();
                    case 0 -> AppService.continueApp=false;
                    default -> {
                        ErrorView.wrongInputOption();
                        ErrorView.reInputOption();
                    }
                }
            }catch (InputMismatchException e){
                ErrorView.wrongInputOption();
                ErrorView.reInputOption();
            }
        } while (AppService.continueApp);
    }
    private static void selectFood (int id){
        FoodItem foodItem = FoodService.findFoodItemByID(id);
        MenuView.confirmQty();
        System.out.println(foodItem.getName()+ Utils.TAB_PIPELINE+ foodItem.getPrice());
        MenuView.backOption();
        MenuView.inputOption();
        String name = foodItem.getName();
        byte qtyOK = (byte) checkQuantity();
        checkOrder(name,qtyOK);
        handleNotes(name, qtyOK);
        home();
    }
    protected static void confirmOrder(){
        MenuView.confirmOrder();
        System.out.println(
                getAllOrderItems() +Utils.NEW_LINE
                +printGrandTotal() +Utils.NEW_LINE
                +NoteService.getAllNotesItems());
        MenuView.confirmOrderOption();
        MenuView.inputOption();
        confirmOrderSelectMenu();
    }
    private static void confirmOrderSelectMenu(){
        do {
            try {
                int selectedMenu = inputOption();
                switch (selectedMenu) {
                    case 1 -> checkOrderToPrint();
                    case 2 -> home();
                    case 0 -> AppService.continueApp=false;
                    default -> {
                        ErrorView.wrongInputOption();
                        ErrorView.reInputOption();
                    }
                }
            }catch (InputMismatchException e){
                ErrorView.wrongInputOption();
                ErrorView.reInputOption();
            }
        } while (AppService.continueApp);
    }
    protected static void checkOrder(){
        if (orderItems.isEmpty()) {
            ErrorView.showOrderIsEmpty();
            ErrorView.reInputOption();
        }
        else {confirmOrder();}
    }
    protected static void checkOrderToPrint(){
        if (orderItems.isEmpty()) {
            ErrorView.failedToPrintReceipt();
        }
        else {
            saveOrderAsReceipt();
            clearOrderItems();
            home();}
    }
    protected static void handleNotes(String foodName, byte quantity){
        if (quantity > 0){
            NoteService.addNotes(foodName,quantity);
        } else {
            home();
        }
    }
    private static void checkOrder(String name, byte quantity){
        if (FoodService.findOrderItemByName(name) == null){
            OrderService.newOrderItem(name, quantity);
        } else {
            FoodService.updateOrderItem(name,quantity);
        }
    }
    public static int checkQuantity() {
        Scanner scanner = new Scanner(System.in);
        int number = 0;
        boolean validInput = false;
        while (!validInput) {
            try {
                if (scanner.hasNextLine()) {
                    String input = scanner.nextLine();
                    number = Integer.parseInt(input);
                    if (number > 0) {
                        validInput = true;
                    } else if (number == 0) {
                        break;
                    } else {
                        ErrorView.minimumOrder();
                        MenuView.inputOption();
                    }
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                ErrorView.minimumOrder();
                MenuView.inputOption();
            }
        }
        return number;
    }
    protected static void saveOrderAsReceipt(){
        String receiptName = "Receipt"+Utils.getTimeNow()+".txt";
        try (FileWriter writer = new FileWriter(receiptName);){
            writer.write(ReceiptView.receiptHeader());
            writer.write(getAllOrderItems());
            writer.write(Utils.NEW_LINE);
            writer.write(printGrandTotal());
            writer.write(Utils.NEW_LINE);
            writer.write(NoteService.getAllNotesItems());
            writer.write(Utils.NEW_LINE);
            writer.write(Utils.NEW_LINE);
            writer.write(ReceiptView.receiptFooter());
            writer.flush();
            writer.close();
            ReceiptView.receiptSuccess();
        } catch (IOException e) {
            e.printStackTrace();
            ErrorView.receiptError();
        }
    }
    protected static String getAllOrderItems(){
        OrderService.removeAllOrderItemsWithZeroQuantity();
        StringBuilder sb = new StringBuilder();
        for (Order order : orderItems) {
            sb.append(Utils.NEW_LINE+
                    order.getFoodItem().getName()
                    +Utils.TAB2
                    +order.getQuantity()
                    +Utils.TAB
                    +order.getTotalPrice());
        }
        return String.valueOf(sb);
    }
    protected static String printGrandTotal(){
        StringBuilder sb = new StringBuilder();
        sb.append(
                Utils.LINE2 + Utils.NEW_LINE
                        + "Total \t" + Utils.TAB2 + getTotalQty() + Utils.TAB
                        + Utils.setCurrency(getTotalPrice())
                        + Utils.NEW_LINE);
        return String.valueOf(sb);
    }
    protected static byte getTotalQty(){
        byte totalQty = 0;
        for (Order order : orderItems) {
            totalQty += order.getQuantity();
        }
        return totalQty;
    }
    protected static int getTotalPrice(){
        int totalPrice = 0;
        for (Order order : orderItems) {
            totalPrice += order.getTotalPrice();
        }
        return totalPrice;
    }

    protected static void clearOrderItems() {
        orderItems.clear();
    }
    protected static byte inputOption() {
        Scanner scanner = new Scanner(System.in);
        return (byte) scanner.nextInt();
    }
}
