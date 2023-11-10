package id.achfajar.challenge5.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class MerchantDTO {
    private UUID id;
    private String merchantName;
    private String merchantLocation;
    private boolean open;
    private int totalOrderedProducts;
    private double totalRevenue;
}
