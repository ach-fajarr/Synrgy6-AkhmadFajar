package id.achfajar.challenge8.repository;

import id.achfajar.challenge8.model.Merchant;
import id.achfajar.challenge8.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findAllByMerchant(Merchant merchant);

    @Query("SELECT p FROM Product p WHERE p.product_name ILIKE %:filter%")
    List<Product> findAllByProductNameContaining(@Param("filter") String productName);

    List<Product> findByDiscountIsNotNull();
}
