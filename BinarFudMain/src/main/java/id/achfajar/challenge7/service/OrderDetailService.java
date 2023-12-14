package id.achfajar.challenge7.service;

import id.achfajar.challenge7.dto.OrderDetailDTO;
import id.achfajar.challenge7.model.OrderDetail;

import java.util.List;
import java.util.UUID;

public interface OrderDetailService {

    OrderDetailDTO createOrderDetail(UUID productId, int quantity);

    OrderDetailDTO updateOrderDetail(UUID orderDetailId, int newQuantity);

    OrderDetailDTO deleteOrderDetail(UUID orderDetailId);

    List<OrderDetailDTO> getAll();

    OrderDetailDTO convertToDTO(OrderDetail orderDetail);

    OrderDetail findByID(UUID id);

    List<UUID> getAllID();
}
