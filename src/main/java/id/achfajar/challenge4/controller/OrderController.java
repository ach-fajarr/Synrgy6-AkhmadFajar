package id.achfajar.challenge4.controller;

import id.achfajar.challenge4.model.*;
import id.achfajar.challenge4.service.OrderService;
import id.achfajar.challenge4.view.ErrorView;
import id.achfajar.challenge4.view.OrderView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class OrderController {

    @Autowired
    OrderService orderService;
    BinarFudController c = new BinarFudController();

    public void order(Users activeUser) {
        boolean active=true;
        while(active){
            try {
                OrderView.orderHeader();
                orderService.mapAllProduct();
                orderService.printProduct();
                OrderView.orderOption();
                int option = c.inputInt();
                switch (option){
                    case 99 -> confirmOrder(activeUser);
                    case 0 -> active=false;
                    default -> orderService.createOrderDetail(option);
                }
            } catch (InputMismatchException e) {ErrorView.wrongInput();}
        }
    }

    private void confirmOrder(Users activeUser) {
        boolean active=true;
        while (active){
            try {
                OrderView.confirmHeader();
                orderService.printOrderDetail();
                OrderView.confirmOption();
                int option = c.inputInt();
                switch (option){
                    case 1 -> orderService.confirmThisOrder(activeUser);
                    case 2 -> cancelOrder(activeUser);
                    case 0 -> active=false;
                    default -> ErrorView.wrongInput();
                }
            } catch (InputMismatchException e) {ErrorView.wrongInput();}
        }
    }
    public void cancelOrder(Users activeUser) {
        OrderView.cancel();
        String input = c.inputLine();
        if (input.equals("y")) {
            orderService.cancelOrder(activeUser);
        } else confirmOrder(activeUser);
    }
    public void historyOrder(Users user) {
        OrderView.headerInfo("Daftar riwayat pesanan anda");
        List<Order> orders = orderService.getAllByUser(user);
        if (orders.isEmpty()){
            System.out.println("Maaf anda belum pernah memesan makanan");
        } else {
            orderService.printOrderHistory(orders);
        }
        OrderView.backOption();
        c.inputLine();
    }
}
