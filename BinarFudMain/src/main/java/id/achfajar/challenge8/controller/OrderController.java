package id.achfajar.challenge8.controller;

import id.achfajar.challenge8.dto.OrderDTO;
import id.achfajar.challenge8.dto.request.OrderRequestDTO;
import id.achfajar.challenge8.dto.response.ResponseHandler;
import id.achfajar.challenge8.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping("/create")
    public ResponseEntity<Object> createOrder(@RequestBody OrderRequestDTO orderRequestDTO,
                                              Principal principal) {
        OrderDTO newOrder = orderService.createOrder(principal.getName(), orderRequestDTO);
        return ResponseHandler.generateResponseSuccess(newOrder);
    }

    @GetMapping("/history")
    public List<OrderDTO> getOrdersByUser(Principal principal) {
        return orderService.getOrdersByUser(principal.getName());
    }

    @PutMapping("/update")
    public OrderDTO updateOrder(@RequestParam UUID orderId,
                                @RequestParam String destinationAddress,
                                @RequestParam boolean completed) {
        return orderService.updateOrder(orderId, destinationAddress, completed);
    }
}
