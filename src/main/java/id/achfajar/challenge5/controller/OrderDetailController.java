package id.achfajar.challenge5.controller;

import id.achfajar.challenge5.model.dto.OrderDetailDTO;
import id.achfajar.challenge5.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/order-details")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping("/create")
    public OrderDetailDTO createOrderDetail(@RequestParam UUID orderId,
                                            @RequestParam UUID productId,
                                            @RequestParam int quantity) {
        return orderDetailService.createOrderDetail(orderId, productId, quantity);
    }

    @GetMapping("/get-by-order-id/{orderId}")
    public List<OrderDetailDTO> getOrderDetailsByOrderId(@PathVariable UUID orderId) {
        return orderDetailService.getOrderDetailsByOrderId(orderId);
    }

    @PutMapping("/update/{orderDetailId}")
    public OrderDetailDTO updateOrderDetail(@PathVariable UUID orderDetailId,
                                            @RequestParam int newQuantity) {
        return orderDetailService.updateOrderDetail(orderDetailId, newQuantity);
    }

    @DeleteMapping("/delete/{orderDetailId}")
    public void deleteOrderDetail(@PathVariable UUID orderDetailId) {
        orderDetailService.deleteOrderDetail(orderDetailId);
    }
}

