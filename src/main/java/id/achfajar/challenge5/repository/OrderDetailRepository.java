package id.achfajar.challenge5.repository;

import id.achfajar.challenge5.model.Order;
import id.achfajar.challenge5.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, UUID> {
    List<OrderDetail> findByOrders(Order orderId);
}
