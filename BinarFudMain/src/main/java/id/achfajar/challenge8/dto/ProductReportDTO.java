package id.achfajar.challenge8.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductReportDTO {

    private UUID productId;
    private String productName;
    private double totalSales;

}
