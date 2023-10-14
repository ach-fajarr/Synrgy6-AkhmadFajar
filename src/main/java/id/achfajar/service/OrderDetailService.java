package id.achfajar.service;

import id.achfajar.controller.Controller;
import id.achfajar.model.Merchant;
import id.achfajar.model.OrderDetail;
import id.achfajar.model.Product;
import id.achfajar.utils.Utils;
import id.achfajar.view.ErrorView;
import id.achfajar.view.MenuView;

import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

import static id.achfajar.model.Data.*;

public class OrderDetailService {
    public static void processOrder(int input) {
        foodMap.keySet().stream()
                .filter(key -> key == input)
                .findFirst()
                .ifPresentOrElse(
                        productKey -> {
                            if (foodMap.get(input).getMerchant().isOpen()) {
                                OrderDetailService.addToOrder(input);
                            } else {
                                ErrorView.merchantClose();
                                ErrorView.reInputOption();
                            }
                        },
                        Controller::errorView
                );
    }
    private static void addToOrder(int productKey) {
        Product p = foodMap.get(productKey);
        Merchant q = foodMap.get(productKey).getMerchant();

        MenuView.confirmQty();
        System.out.println(q.getMerchant_name()+"\t"+q.getMerchant_location()+"\t"+q.getStatus());
        System.out.println(p.getProduct_name()+ Utils.TAB_PIPELINE+ p.getPrice());
        MenuView.backOption();
        MenuView.inputOption();

        String name = p.getProduct_name();
        byte qtyOK = (byte) checkQuantity();
        checkOrder(name,qtyOK,productKey);
        NoteService.handleNotes(name, qtyOK);
        Controller.home();
    }
    private static void checkOrder(String name, byte quantity, int key){
        if (findOrderItemByName(name) == null){
            newOrderDetails(name, quantity, key);
        } else {
            updateOrder(name,quantity, key);
        }
    }
    public static void newOrderDetails(String name, byte qty, int key){
        OrderDetail newOrder = new OrderDetail();
        newOrder.setId(UUID.randomUUID());
        newOrder.setProduct_name(name);
        newOrder.setQuantity(qty);
        int totalPrice = qty*foodMap.get(key).getPrice();
        newOrder.setTotal_price(totalPrice);
        orderDetails.add(newOrder);
    }
    public static void updateOrder(String name, byte quantity, int key){
        OrderDetail getItem = findOrderItemByName(name);
        int qty = getItem.getQuantity();
        if (qty == 0){
        } else {
            int q = getItem.getQuantity();
            int updateQty = q+quantity;
            int totalPrice = updateQty*foodMap.get(key).getPrice();
            getItem.setQuantity(updateQty);
            getItem.setTotal_price(totalPrice);
        }
    }
    public static OrderDetail findOrderItemByName(String name) {
        for (OrderDetail orderDetail : orderDetails) {
            if (orderDetail.getProduct_name().equals(name)) {
                return orderDetail;
            }
        }
        return null;
    }
    private static int checkQuantity() {
        Scanner scanner = new Scanner(System.in);

        Optional<Integer> optionalNumber = Optional.empty();

        boolean validInput = false;
        while (!validInput){
            try {
                int number = scanner.nextInt();
                if (number > 0) {
                    validInput = true;
                    optionalNumber = Optional.of(number);
                } else if (number == 0) {
                    break;
                } else {
                    ErrorView.minimumOrder();
                    MenuView.inputOption();
                }
            } catch (NumberFormatException e) {
                ErrorView.minimumOrder();
                MenuView.inputOption();
            }
        }
        return optionalNumber.orElse(0);
    }
}
