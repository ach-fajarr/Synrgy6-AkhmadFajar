package id.achfajar.challenge6.controller;

import id.achfajar.challenge6.dto.*;
import id.achfajar.challenge6.dto.response.ResponseHandler;
import id.achfajar.challenge6.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/data")
public class AdminController {
    private final UserService userService;
    private final MerchantService merchantService;
    private final ProductService productService;
    private final OrderService orderService;
    private final OrderDetailService orderDetailService;

    @Autowired
    public AdminController(
            UserService userService, MerchantService merchantService, ProductService productService,
            OrderService orderService, OrderDetailService orderDetailService){
        this.userService = userService;
        this.merchantService = merchantService;
        this.productService =  productService;
        this.orderService = orderService;
        this.orderDetailService = orderDetailService;
    }

    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }
    @GetMapping("/merchants")
    public List<MerchantDTO> getAllMerchants() {
        return merchantService.getAllMerchants();
    }
    @GetMapping("/products")
    public List<ProductDTO> getAllProducts(){
        return productService.getAllProducts();
    }
    @GetMapping("/orders")
    public List<OrderDTO> getAllOrders() {
        return orderService.getAll();
    }
    @GetMapping("/order-details")
    public List<OrderDetailDTO> getAllOrderDetails(){
        return orderDetailService.getAll();
    }
    @GetMapping("/extractID")
    public ResponseEntity<Object> getAllOrderDetailID(){
        return ResponseHandler.generateResponseSuccess(orderDetailService.getAllID());
    }

}
