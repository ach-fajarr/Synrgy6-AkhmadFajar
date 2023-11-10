package id.achfajar.challenge5.service;

import id.achfajar.challenge5.exception.ResourceNotFoundException;
import id.achfajar.challenge5.model.OrderDetail;
import id.achfajar.challenge5.model.dto.OrderDTO;
import id.achfajar.challenge5.model.Order;
import id.achfajar.challenge5.model.dto.OrderDetailDTO;
import id.achfajar.challenge5.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Override
    public OrderDTO createOrder(UUID userId, String destinationAddress) {
        // Retrieve user
        userService.getUserById(userId);

        // Create and save Order
        Order order = new Order()
                .setUsers(userService.getUsersById(userId))
                .setOrder_time(LocalDate.from(LocalDateTime.now()))
                .setDestination_address(destinationAddress)
                .setCompleted(false);

        order = orderRepository.save(order);
        return convertToDTO(order);
    }

    @Override
    public List<OrderDTO> getOrdersByUserId(UUID userId) {
        List<Order> orders = orderRepository.findAllByUsers(userService.getUsersById(userId));
        return orders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO updateOrder(UUID orderId, String destinationAddress, boolean completed) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order tidak ditemukan"));

        order.setDestination_address(destinationAddress);
        order.setCompleted(completed);

        order = orderRepository.save(order);
        return convertToDTO(order);
    }
    @Override
    public Order getOrderById(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order tidak ditemukan"));
    }

    private OrderDTO convertToDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setOrderTime(LocalDateTime.from(order.getOrder_time()));
        orderDTO.setDestinationAddress(order.getDestination_address());
        orderDTO.setCompleted(order.isCompleted());
        orderDTO.setUserId(order.getUsers().getId());

        // Ubah Model OrderDetail ke OrderDetailDTOs
        List<OrderDetailDTO> orderDetailDTOList = order.getOrderDetailList().stream()
                .map(this::convertOrderDetailToDTO)
                .collect(Collectors.toList());

        orderDTO.setOrderDetailList(orderDetailDTOList);

        return orderDTO;
    }
    private OrderDetailDTO convertOrderDetailToDTO(OrderDetail orderDetail) {
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        orderDetailDTO.setId(orderDetail.getId());
        orderDetailDTO.setQuantity(orderDetail.getQuantity());
        orderDetailDTO.setTotalPrice(orderDetail.getTotal_price());
        orderDetailDTO.setOrderId(orderDetail.getOrders().getId());
        orderDetailDTO.setProductId(orderDetail.getProduct().getId());

        return orderDetailDTO;
    }
}
