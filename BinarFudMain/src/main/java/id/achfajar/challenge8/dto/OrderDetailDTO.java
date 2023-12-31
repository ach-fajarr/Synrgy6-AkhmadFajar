package id.achfajar.challenge8.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderDetailDTO {
    private UUID id;
    private String productName;
    private int price;
    private int quantity;
    private int totalPrice;
    private String info;
    private UUID productId;
}
