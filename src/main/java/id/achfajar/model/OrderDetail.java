package id.achfajar.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class OrderDetail {
    private UUID id;
    private UUID order_id;
    private String product_name;
    private int quantity;
    private double total_price;
}
