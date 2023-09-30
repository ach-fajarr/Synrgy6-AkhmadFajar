package id.achfajar.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Order {
    private FoodItem foodItem;
    private Byte quantity;
    private int totalPrice;
}
