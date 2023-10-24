package id.achfajar.challenge4.repository;

import id.achfajar.challenge4.model.Order;
import id.achfajar.challenge4.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findAllByUsers(Users user);
}

