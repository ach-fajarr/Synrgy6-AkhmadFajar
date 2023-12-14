package id.achfajar.challenge7.service;

import id.achfajar.challenge7.dto.request.OrderRequestDTO;
import id.achfajar.challenge7.dto.OrderDTO;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    OrderDTO createOrder(String username, OrderRequestDTO orderRequestDTO);
    OrderDTO updateOrder(UUID orderId, String destinationAddress, boolean completed);
    List<OrderDTO> getOrdersByUser(String username);

    List<OrderDTO> getAll();
}
