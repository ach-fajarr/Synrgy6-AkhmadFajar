package id.achfajar.challenge4.repository;

import id.achfajar.challenge4.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    List<Product> findByProductTypesId(Integer productTypeId);

    @Query("SELECT p FROM Product p WHERE p.product_name LIKE %:productName%")
    List<Product> findProductByName(@Param("productName") String productName);

    @Query("SELECT p FROM Product p INNER JOIN p.merchant m WHERE m.open = true")
    List<Product> findOpenMerchantProducts();

}

