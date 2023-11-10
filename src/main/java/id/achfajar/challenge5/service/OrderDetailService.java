package id.achfajar.challenge5.service;

import id.achfajar.challenge5.model.dto.OrderDetailDTO;
import java.util.List;
import java.util.UUID;

public interface OrderDetailService {

    OrderDetailDTO createOrderDetail(UUID orderId, UUID productId, int quantity);

    List<OrderDetailDTO> getOrderDetailsByOrderId(UUID orderId);

    OrderDetailDTO updateOrderDetail(UUID orderDetailId, int newQuantity);

    void deleteOrderDetail(UUID orderDetailId);
}
