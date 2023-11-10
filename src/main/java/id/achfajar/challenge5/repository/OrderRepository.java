package id.achfajar.challenge5.repository;

import id.achfajar.challenge5.model.Order;
import id.achfajar.challenge5.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findAllByUsers(Users user);
}
