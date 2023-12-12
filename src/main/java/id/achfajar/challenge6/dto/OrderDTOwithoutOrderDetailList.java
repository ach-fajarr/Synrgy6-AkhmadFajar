package id.achfajar.challenge6.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class OrderDTOwithoutOrderDetailList {
    private UUID id;
    private LocalDateTime orderTime;
    private String destinationAddress;
    private boolean completed;
}
