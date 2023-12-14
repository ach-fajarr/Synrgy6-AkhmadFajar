package id.achfajar.challenge7.controller;

import id.achfajar.challenge7.dto.OrderDetailDTO;
import id.achfajar.challenge7.dto.response.ResponseHandler;
import id.achfajar.challenge7.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/order-details")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping("/create")
    public ResponseEntity<Object> createOrderDetail(@RequestParam UUID productId,
                                                    @RequestParam int quantity) {
        OrderDetailDTO newOrderDetail = orderDetailService.createOrderDetail(productId, quantity);
        return ResponseHandler.generateResponseSuccess(newOrderDetail);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateOrderDetail(@RequestParam UUID id,
                                            @RequestParam int newQuantity) {
        OrderDetailDTO updateOrderDetail = orderDetailService.updateOrderDetail(id, newQuantity);
        return ResponseHandler.generateResponseSuccess(updateOrderDetail);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteOrderDetail(@RequestParam UUID id) {
        OrderDetailDTO deletedOrderDetail = orderDetailService.deleteOrderDetail(id);
        return ResponseHandler.generateResponse(
                "berhasil menghapus data", HttpStatus.ACCEPTED, deletedOrderDetail);
    }
}

