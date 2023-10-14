package id.achfajar.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class Order {
    private UUID id;
    private String order_time;
    private String destination_address;
    private Users user_id;
    private boolean session;
    private boolean completed;

    public String getStatus() {
        return completed ? "Sukses" : "Dibatalkan";
    }
    public void setOrderDetails(List<OrderDetail> orderDetails) {
        orderDetails = Data.orderDetails;
    }
}
