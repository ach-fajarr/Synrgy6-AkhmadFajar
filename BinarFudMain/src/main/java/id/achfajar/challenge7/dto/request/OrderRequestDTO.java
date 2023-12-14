package id.achfajar.challenge7.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class OrderRequestDTO {
    private String destinationAddress;
    private Boolean completed;
    private List<UUID> orderDetailID;
}
