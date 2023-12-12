package id.achfajar.challenge6.repository;

import id.achfajar.challenge6.model.Merchant;
import id.achfajar.challenge6.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findAllByMerchant(Merchant merchant);

    @Query("SELECT p FROM Product p WHERE p.product_name ILIKE %:filter%")
    List<Product> findAllByProductNameContaining(@Param("filter") String productName);


}
