package id.achfajar.challenge8.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InvoiceReportDTO {
    private String productName;
    private String orderTime;
    private int totalOrder;
    private int totalPrice;
}
