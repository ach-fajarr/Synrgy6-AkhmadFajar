package id.achfajar.challenge7.repository;

import id.achfajar.challenge7.model.Merchant;
import id.achfajar.challenge7.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, UUID> {
    @Query(value = "SELECT * FROM Merchant m WHERE m.merchant_name ILIKE %:keyword% " +
                    "OR m.merchant_location ILIKE %:keyword%", nativeQuery = true)
    List<Merchant> findAllByMerchantNameOrLocationContaining(@Param("keyword") String keyword);

    List<Merchant> findAllByOpen(boolean b);

    @Query("SELECT m FROM Merchant m WHERE m.merchant_name = %:name%")
    Optional<Merchant> findByMerchantName(@Param("name") String filter);

    List<Merchant> findAllByUsers(Users userByUsername);

}
