package id.achfajar.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FoodItem {
    private int id;
    private String name;
    private int price;
    public FoodItem(int id, String name, int price){
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
