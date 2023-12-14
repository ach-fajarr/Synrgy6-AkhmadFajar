package id.achfajar.challenge7.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class MerchantDTO {
    private UUID id;
    private String merchantName;
    private String merchantLocation;
    private Boolean open;
    private int totalOrderedProducts;
    private double totalRevenue;
}
