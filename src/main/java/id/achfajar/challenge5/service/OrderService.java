package id.achfajar.challenge5.service;

import id.achfajar.challenge5.model.Order;
import id.achfajar.challenge5.model.dto.OrderDTO;
import java.util.List;
import java.util.UUID;

public interface OrderService {

    OrderDTO createOrder(UUID userId, String destinationAddress);

    List<OrderDTO> getOrdersByUserId(UUID userId);

    OrderDTO updateOrder(UUID orderId, String destinationAddress, boolean completed);
    Order getOrderById(UUID orderId);
}
