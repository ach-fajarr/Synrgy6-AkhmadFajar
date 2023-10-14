package id.achfajar;

import id.achfajar.model.Merchant;
import id.achfajar.model.OrderDetail;
import id.achfajar.model.Product;
import id.achfajar.service.OrderDetailService;
import id.achfajar.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static id.achfajar.model.Data.orderDetails;

public class ControllerTest {
    private OrderDetail od;

    @BeforeEach
    void setUser(){
        od.setId(UUID.randomUUID());
        od.setProduct_name("Kopi");
        od.setTotal_price(12_000);
        od.setQuantity(2);
        orderDetails.add(od);
    }
    @Test
    @DisplayName("Tes ambil data menu")
    void checkOrderByName(){
        OrderDetail odt = OrderDetailService.findOrderItemByName("kopi");
        Assertions.assertTrue(odt instanceof OrderDetail);
    }
}
