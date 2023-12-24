package id.achfajar.challenge8.service;

import id.achfajar.challenge8.dto.request.OrderRequestDTO;
import id.achfajar.challenge8.exception.ExistingResourceFoundException;
import id.achfajar.challenge8.exception.ResourceNotFoundException;
import id.achfajar.challenge8.model.OrderDetail;
import id.achfajar.challenge8.dto.OrderDTO;
import id.achfajar.challenge8.model.Order;
import id.achfajar.challenge8.dto.OrderDetailDTO;
import id.achfajar.challenge8.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    ProductService productService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderDTO createOrder(String username, OrderRequestDTO orderRequestDTO) {
        List<OrderDetail> orderDetailList = new ArrayList<>();
        orderRequestDTO.getOrderDetailID().forEach(orderDetailID ->
                        orderDetailList.add(orderDetailService.findByID(orderDetailID)));
        if (orderDetailList.stream().allMatch(orderDetail -> orderDetail.getOrders() != null)) {
            throw new ExistingResourceFoundException("Pesanan sudah tersimpan");
        } else {
            orderDetailList.forEach(orderDetail -> productService.updateStock2(
                            orderDetail.getProduct().getId(), null,
                            orderDetail.getQuantity()));
            Order order = new Order();
            order.setOrder_time(LocalDateTime.now());
            order.setUsers(userService.getUsersByUsername(username));
            order.setDestination_address(orderRequestDTO.getDestinationAddress());
            order.setOrderDetailList(orderDetailList);
            order.setCompleted(orderRequestDTO.getCompleted());
            orderRepository.save(order);
            orderDetailList.forEach(orderDetail -> orderDetail.setOrders(order));
            System.out.println("\nSTOCK dari Order");
            order.getOrderDetailList().forEach(orderDetail -> System.out.println("STOCKK  :  "+orderDetail.getProduct().getStock()));

//            Order order1 = null;
//            order1.setOrderDetailList(orderDetailList);
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
