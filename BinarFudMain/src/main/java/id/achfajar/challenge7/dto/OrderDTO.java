package id.achfajar.challenge7.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class OrderDTO {
    private UUID id;
    private String orderTime;
    private String destinationAddress;
    private Boolean completed;
    private List<OrderDetailDTO> orderDetailList;
}
