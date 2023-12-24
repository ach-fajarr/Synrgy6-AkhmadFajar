package id.achfajar.challenge8.repository;

import id.achfajar.challenge8.model.OrderDetail;
import id.achfajar.challenge8.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, UUID> {
    List<OrderDetail> findByProduct(Product product);

    @Query(value = "SELECT od.* FROM order_detail od " +
            "JOIN orders o ON od.order_id = o.id " +
            "JOIN product p ON od.product_id = p.id " +
            "JOIN merchant m ON p.merchant_id = m.id " +
            "JOIN users u ON m.users_id = u.id " +
            "WHERE EXTRACT(MONTH FROM o.order_time) = :month " +
            "AND EXTRACT(YEAR FROM o.order_time) = :year " +
            "AND m.id = :merchantId " +
            "AND u.id = :userId", nativeQuery = true)
    List<OrderDetail> findOrderDetailsByMonth(@Param("merchantId") UUID merchantId,
                                              @Param("userId") UUID userId,
                                              @Param("month") int month,
                                              @Param("year") int year);
    @Query(value = "SELECT od.* FROM order_detail od " +
            "JOIN orders o ON od.order_id = o.id " +
            "JOIN product p ON od.product_id = p.id " +
            "JOIN merchant m ON p.merchant_id = m.id " +
            "JOIN users u ON m.users_id = u.id " +
            "WHERE EXTRACT(YEAR FROM o.order_time) = :year " +
            "AND m.id = :merchantId " +
            "AND u.id = :userId", nativeQuery = true)
    List<OrderDetail> findOrderDetailsByYear(@Param("merchantId") UUID merchantId,
                                              @Param("userId") UUID userId,
                                              @Param("year") int year);

    @Query(value = "SELECT od.* FROM order_detail od " +
            "JOIN orders o ON od.order_id = o.id " +
            "JOIN product p ON od.product_id = p.id " +
            "JOIN merchant m ON p.merchant_id = m.id " +
            "JOIN users u ON m.users_id = u.id " +
            "WHERE m.id = :merchantId " +
            "AND u.id = :userId " +
            "AND o.order_time BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<OrderDetail> findOrderDetailsByCustomDate(@Param("merchantId") UUID merchantId,
                                                   @Param("userId") UUID userId,
                                                   @Param("startDate") LocalDate startDate,
                                                   @Param("endDate") LocalDate endDate);
}
