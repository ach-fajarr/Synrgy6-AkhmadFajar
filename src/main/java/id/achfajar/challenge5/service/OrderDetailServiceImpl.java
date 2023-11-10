package id.achfajar.challenge5.service;

import id.achfajar.challenge5.exception.ResourceNotFoundException;
import id.achfajar.challenge5.model.dto.OrderDetailDTO;
import id.achfajar.challenge5.model.OrderDetail;
import id.achfajar.challenge5.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDetailDTO createOrderDetail(UUID orderId, UUID productId, int quantity) {
        productService.getProductById(productId);
        orderService.getOrderById(orderId);

        OrderDetail orderDetail = new OrderDetail()
                .setOrders(orderService.getOrderById(orderId))
                .setProduct(productService.getProductById(productId))
                .setQuantity(quantity)
                .setTotal_price(productService.getProductDTOById(productId).getPrice() * quantity);

        orderDetail = orderDetailRepository.save(orderDetail);
        return convertToDTO(orderDetail);
    }

    @Override
    public List<OrderDetailDTO> getOrderDetailsByOrderId(UUID orderId) {
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrders(orderService.getOrderById(orderId));
        return orderDetails.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDetailDTO updateOrderDetail(UUID orderDetailId, int newQuantity) {
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderDetail tidak ditemukan"));

        orderDetail.setQuantity(newQuantity);
        orderDetail.setTotal_price(orderDetail.getProduct().getPrice() * newQuantity);

        orderDetail = orderDetailRepository.save(orderDetail);
        return convertToDTO(orderDetail);
    }

    @Override
    public void deleteOrderDetail(UUID orderDetailId) {
        orderDetailRepository.deleteById(orderDetailId);
    }

    private OrderDetailDTO convertToDTO(OrderDetail orderDetail) {
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        orderDetailDTO.setId(orderDetail.getId());
        orderDetailDTO.setQuantity(orderDetail.getQuantity());
        orderDetailDTO.setTotalPrice(orderDetail.getTotal_price());
        orderDetailDTO.setOrderId(orderDetail.getOrders().getId());
        orderDetailDTO.setProductId(orderDetail.getProduct().getId());

        return orderDetailDTO;
    }
}
