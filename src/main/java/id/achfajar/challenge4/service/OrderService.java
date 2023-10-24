package id.achfajar.challenge4.service;

import id.achfajar.challenge4.model.*;
import id.achfajar.challenge4.repository.OrderDetailRepository;
import id.achfajar.challenge4.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    OrderRepository orderRepository;

    public void saveOrder(Order order){
        orderRepository.save(order);
    }
    public void saveOrderDetails(OrderDetail orderDetail){
        orderDetailRepository.save(orderDetail);
    }
    public List<Order> getAllByUser(Users user){
        return orderRepository.findAllByUsers(user);
    }
}
