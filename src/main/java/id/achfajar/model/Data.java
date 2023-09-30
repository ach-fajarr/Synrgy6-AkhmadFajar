package id.achfajar.model;

import java.util.ArrayList;
import java.util.List;

public class Data {
    public static List<FoodItem> foodItems = new ArrayList<>(){{

    }};
    static {
        foodItems.add(new FoodItem(1, "Nasi Goreng", 15_000));
        foodItems.add(new FoodItem(2, "Mie Goreng", 13_000));
        foodItems.add(new FoodItem(3, "Nasi + Ayam", 18_000));
        foodItems.add(new FoodItem(4, "Es Teh Manis", 3_000));
        foodItems.add(new FoodItem(5, "Es Jeruk", 5_000));
    }
    public static List<Order> orderItems = new ArrayList<>();
    public static List<Note> notesPerItem = new ArrayList<>();

}
