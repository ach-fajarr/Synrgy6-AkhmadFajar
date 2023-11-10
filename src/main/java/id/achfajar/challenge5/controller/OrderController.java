package id.achfajar.challenge5.controller;

import id.achfajar.challenge5.model.dto.OrderDTO;
import id.achfajar.challenge5.service.InvoiceService;
import id.achfajar.challenge5.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private InvoiceService invoiceService;


    @PostMapping("/create")
    public OrderDTO createOrder(@RequestParam UUID userId,
                                @RequestParam String destinationAddress) {
        return orderService.createOrder(userId, destinationAddress);
    }

    @GetMapping("/get-by-user-id/{userId}")
    public List<OrderDTO> getOrdersByUserId(@PathVariable UUID userId) {
        return orderService.getOrdersByUserId(userId);
    }

    @PutMapping("/update/{orderId}")
    public OrderDTO updateOrder(@PathVariable UUID orderId,
                                @RequestParam String destinationAddress,
                                @RequestParam boolean completed) {
        return orderService.updateOrder(orderId, destinationAddress, completed);
    }
    @PostMapping("/{orderId}/generate-invoice")
    public ResponseEntity<byte[]> generateInvoice(@PathVariable UUID orderId, @RequestParam UUID userId) {
        byte[] invoiceBytes = invoiceService.generateInvoice(orderId, userId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "invoice.pdf");

        return new ResponseEntity<>(invoiceBytes, headers, HttpStatus.OK);
    }
}
