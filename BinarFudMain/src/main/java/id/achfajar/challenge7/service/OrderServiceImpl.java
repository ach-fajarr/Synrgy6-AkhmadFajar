package id.achfajar.challenge7.service;

import id.achfajar.challenge7.dto.request.OrderRequestDTO;
import id.achfajar.challenge7.exception.ExistingResourceFoundException;
import id.achfajar.challenge7.exception.ResourceNotFoundException;
import id.achfajar.challenge7.model.OrderDetail;
import id.achfajar.challenge7.dto.OrderDTO;
import id.achfajar.challenge7.model.Order;
import id.achfajar.challenge7.dto.OrderDetailDTO;
import id.achfajar.challenge7.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    OrderDetailService orderDetailService;

    @Override
    public OrderDTO createOrder(String username, OrderRequestDTO orderRequestDTO) {
        List<OrderDetail> orderDetailList = new ArrayList<>();
        orderRequestDTO.getOrderDetailID()
                .forEach(orderDetailID -> orderDetailList.add(orderDetailService.findByID(orderDetailID)));
        if (orderDetailList.stream().allMatch(orderDetail -> orderDetail.getOrders() != null)) {
            throw new ExistingResourceFoundException("Pesanan sudah tersimpan");
        } else {
            Order order = new Order();
            order.setOrder_time(LocalDateTime.now());
            order.setUsers(userService.getUsersByUsername(username));
            order.setDestination_address(orderRequestDTO.getDestinationAddress());
            order.setOrderDetailList(orderDetailList);
            order.setCompleted(orderRequestDTO.getCompleted());
            orderDetailList.forEach(orderDetail -> orderDetail.setOrders(order));
            orderRepository.save(order);
            return convertToDTO(order);
        }
    }

    @Override
    public List<OrderDTO> getOrdersByUser(String username) {
        List<Order> orders = orderRepository.findAllByUsers(userService.getUsersByUsername(username));
        return orders.stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public List<OrderDTO> getAll() {
        return orderRepository.findAll().stream().map(this::convertToDTO).toList();
    }

    @Override
    public OrderDTO updateOrder(UUID orderId, String destinationAddress, boolean completed) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(orderId));
        order.setDestination_address(destinationAddress);
        order.setCompleted(completed);
        order = orderRepository.save(order);
        return convertToDTO(order);
    }

    private OrderDTO convertToDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setOrderTime(order.getOrder_time().toString());
        orderDTO.setDestinationAddress(order.getDestination_address());
        orderDTO.setCompleted(order.isCompleted());
        List<OrderDetailDTO> orderDetailDTOList = order.getOrderDetailList().stream()
                .map(orderDetail -> orderDetailService.convertToDTO(orderDetail))
                .toList();
        orderDTO.setOrderDetailList(orderDetailDTOList);
        return orderDTO;
    }
}
