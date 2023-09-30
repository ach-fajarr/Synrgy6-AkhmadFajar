package id.achfajar.service;

import id.achfajar.model.Data;
import id.achfajar.model.FoodItem;
import id.achfajar.model.Order;

import static id.achfajar.model.Data.foodItems;
import static id.achfajar.model.Data.orderItems;

public class FoodService {
    public static FoodItem findFoodItemByName(String name) {
        for (FoodItem foodItem : Data.foodItems) {
            if (foodItem.getName().equals(name)) {
                return foodItem;
            }
        }
        return null;
    }
    public static Order findOrderItemByName(String name) {
        for (Order orderItem : orderItems) {
            if (orderItem.getFoodItem().getName().equals(name)) {
                return orderItem;
            }
        }
        return null;
    }
    public static FoodItem findFoodItemByID(int id) {
        for (FoodItem foodItem : foodItems) {
            if (foodItem.getId() == id) {
                return foodItem;
            }
        }
        return null;
    }
    public static void updateOrderItem(String name, byte quantity){
        Order getItem = findOrderItemByName(name);
        Byte qty = getItem.getQuantity();
        if (qty == 0){
        } else {
            byte q = getItem.getQuantity();
            byte updateQty = (byte) (q+quantity);
            int totalPrice = updateQty*getItem.getFoodItem().getPrice();
            getItem.setQuantity(updateQty);
            getItem.setTotalPrice(totalPrice);
        }
    }
}
