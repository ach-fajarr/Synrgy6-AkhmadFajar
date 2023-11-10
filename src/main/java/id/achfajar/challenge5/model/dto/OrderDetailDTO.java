package id.achfajar.challenge5.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderDetailDTO {
    private UUID id;
    private int quantity;
    private double totalPrice;
    private UUID orderId;
    private UUID productId;}
