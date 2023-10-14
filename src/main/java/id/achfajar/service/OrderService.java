package id.achfajar.service;

import id.achfajar.controller.Controller;
import id.achfajar.model.Data;
import id.achfajar.model.Order;
import id.achfajar.model.OrderDetail;
import id.achfajar.model.Users;
import id.achfajar.utils.Utils;
import id.achfajar.view.MenuView;

import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import static id.achfajar.controller.Controller.clearOrderItems;
import static id.achfajar.model.Data.orderDetails;
import static id.achfajar.model.Data.orderMap;

public class OrderService {
    public static void session() {
        orderMap.values().stream()
                .filter(Order::isSession)
                .findFirst()
                .ifPresentOrElse(
                        order -> confirmOrder(),
                        () -> {
                            newOrder(UsersService.getLoggedInUser());
                            confirmOrder();
                        }
                );
    }
    public static void confirmOrder(){
        MenuView.confirmOrder();
        listOrder();
        MenuView.confirmOrderOption();
        MenuView.inputOption();
        Controller.confirmOrderSelectMenu();
    }

    public static void newOrder (Users user_id){
        MenuView.inputAddress();
        MenuView.inputOption();
        String address = inputAddress();
        int key = nextKey(Data.orderMap);
        Order newOrder = new Order();
        newOrder.setId(UUID.randomUUID());
        newOrder.setOrder_time(Utils.getTimeNow());
        newOrder.setUser_id(user_id);
        newOrder.setSession(true);
        newOrder.setDestination_address(address);
        Data.orderMap.put(key, newOrder);
    }
    protected static void listOrder(){
        System.out.println(
                getAllOrderItems() +Utils.NEW_LINE
                +printGrandTotal() +Utils.NEW_LINE
                +NoteService.getAllNotesItems());
    }
    public static String getAllOrderItems(){
        removeAllOrderItemsWithZeroQuantity();
        StringBuilder sb = new StringBuilder();
        for (OrderDetail order : orderDetails) {
            sb.append(Utils.NEW_LINE+ order.getProduct_name()
                    +Utils.TAB2 +order.getQuantity()
                    +Utils.TAB +getTotalPrice());
        }
        return String.valueOf(sb);
    }
    public static String printGrandTotal(){
        StringBuilder sb = new StringBuilder();
        sb.append(
                Utils.LINE2 + Utils.NEW_LINE
                        + "Total \t" + Utils.TAB2 + getTotalQty() + Utils.TAB + Utils.setCurrency(getTotalPrice())
                        + Utils.NEW_LINE);
        return String.valueOf(sb);
    }
    public static void setOrderStatus(boolean completeOrder) {
        orderMap.values().stream()
                .filter(Order::isSession)
                .findFirst()
                .ifPresentOrElse(
                        order -> {
                            order.setCompleted(completeOrder);
                            order.setSession(false);
                            clearOrderItems();
                            Controller.home();
                        },
                        () -> {
                            newOrder(UsersService.getLoggedInUser());
                            confirmOrder();
                        }
                );
    }
    public static void sessionCompleted() {
        setOrderStatus(true);
    }
    public static void sessionCanceled() {
        setOrderStatus(false);
    }
    public static void orderHistory(){
        orderMap.entrySet().stream()
                .filter(e -> !e.getValue().isSession())
                .forEach(e -> {
                    var a = e.getValue();
                    System.out.println(e.getKey()+" id \t: "+a.getId()
                            + "\n  waktu \t: "+a.getOrder_time()
                            +"\n  alamat \t: "+a.getDestination_address()
                            +"\n  status \t: "+a.getStatus());
                });
        MenuView.inputOption();
    }
    public static void removeAllOrderItemsWithZeroQuantity() {
        orderDetails.removeIf(order -> order.getQuantity() == 0);
    }

    protected static byte getTotalQty(){
        byte totalQty = 0;
        for (OrderDetail order : orderDetails) {
            totalQty += order.getQuantity();
        }
        return totalQty;
    }
    protected static int getTotalPrice(){
        int totalPrice = 0;
        for (OrderDetail order : orderDetails) {
            totalPrice += order.getTotal_price();
        }
        return totalPrice;
    }
    public static int nextKey(Map<Integer, Order> map) {
        int nextKey = 1;
        while (map.containsKey(nextKey)) {
            nextKey++;
        }
        return nextKey;
    }
    private static String inputAddress(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
