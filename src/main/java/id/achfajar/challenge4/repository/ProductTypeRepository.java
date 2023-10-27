package id.achfajar.challenge4.repository;

import id.achfajar.challenge4.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTypeRepository extends JpaRepository <ProductType, Integer> {
    ProductType findByName(String nasi);
}
