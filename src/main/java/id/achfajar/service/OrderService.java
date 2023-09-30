package id.achfajar.service;

import id.achfajar.model.Data;
import id.achfajar.model.FoodItem;
import id.achfajar.model.Order;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static id.achfajar.model.Data.orderItems;

public class OrderService {
    public static void newOrderItem(String foodName, byte quantity){
        FoodItem orderItem = FoodService.findFoodItemByName(foodName);
        Order orderItemData = new Order();
        orderItemData.setFoodItem(orderItem);
        orderItemData.setQuantity(quantity);
        int totalPrice = orderItem.getPrice() * orderItemData.getQuantity();
        orderItemData.setTotalPrice(totalPrice);
        Data.orderItems.add(orderItemData);
    }
    public static void removeAllOrderItemsWithZeroQuantity() {
        List<Order> orderItemsToRemove = new ArrayList<>();
        for (Order order : orderItems) {
            if (order.getQuantity() == 0) {
                orderItemsToRemove.add(order);
            }
        }
        for (Order order : orderItemsToRemove) {
            removeOrderItem(order.getFoodItem().getName());
        }
    }
    public static void removeOrderItem(String foodName) {
        Iterator<Order> iterator = Data.orderItems.iterator();
        while (iterator.hasNext()) {
            Order orderItem = iterator.next();
            if (orderItem.getFoodItem().getName().equals(foodName) && orderItem.getQuantity() == 0) {
                iterator.remove(); // Hapus item jika nama makanannya cocok dan quantity-nya adalah 0
            }
        }
    }
}
