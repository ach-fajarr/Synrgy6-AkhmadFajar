package id.achfajar.challenge6.repository;

import id.achfajar.challenge6.model.Order;
import id.achfajar.challenge6.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findAllByUsers(Users user);
}
