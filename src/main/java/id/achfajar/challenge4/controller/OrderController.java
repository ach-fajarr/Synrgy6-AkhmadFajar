package id.achfajar.challenge4.controller;

import id.achfajar.challenge4.model.*;
import id.achfajar.challenge4.service.OrderService;
import id.achfajar.challenge4.view.ErrorView;
import id.achfajar.challenge4.view.OrderView;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class OrderController {

    @Autowired
    OrderService orderService;
    BinarFudController c = new BinarFudController();
    @Setter
    Integer setFilter;


    public void filter(Integer i){
        if (i == 1){
            orderService.filterByName();
        } else if (i == 2) {
            orderService.filterByType();
        } else if (i == 3) {
            orderService.filterByOpen();
        } else {
            orderService.noFilter();}
    }
    public void order(Users activeUser) {
        boolean active=true;
        while(active){
            try {
                filter(setFilter);
                OrderView.headerInfo("Daftar Menu");
                orderService.printProduct();
                OrderView.orderOption();
                int option = c.inputInt();
                switch (option){
                    case 98 -> filterProduct();
                    case 99 -> confirmOrder(activeUser);
                    case 0 -> {setFilter= 0; active=false;}
                    default -> orderService.createOrderDetail(option);
                }
            } catch (InputMismatchException e) {ErrorView.wrongInput();}
        }
    }
    private void filterProduct() {
        while (true){
            OrderView.headerInfo("Filter produk yang ditampilkan");
            OrderView.filterOption();
            int option = c.inputInt();
            if (option == 1){
                setFilter = 1;
                break;
            } else if (option == 2) {
                setFilter = 2;
                break;
            } else if (option == 3) {
                setFilter = 3;
                break;
            } else if (option == 0) {
                break;
            } else {ErrorView.wrongInput();}
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
