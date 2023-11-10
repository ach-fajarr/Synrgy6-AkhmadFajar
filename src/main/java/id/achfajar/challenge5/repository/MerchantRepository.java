package id.achfajar.challenge5.repository;

import id.achfajar.challenge5.model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, UUID> {
    @Query("SELECT m FROM Merchant m WHERE m.merchant_name ILIKE %:filter% or m.merchant_location ILIKE %:filter%")
    List<Merchant> findAllByMerchantNameOrLocationContaining(@Param("filter") String filter);

    List<Merchant> findAllByOpen(boolean b);

    @Query("SELECT m FROM Merchant m WHERE m.merchant_name LIKE %:name%")
    Optional<Merchant> findByMerchantName(@Param("name") String filter);
}
