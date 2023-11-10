package id.achfajar.challenge5.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class OrderDTO {
    private UUID id;
    private LocalDateTime orderTime;
    private String destinationAddress;
    private boolean completed;
    private UUID userId;
    private List<OrderDetailDTO> orderDetailList;
}
