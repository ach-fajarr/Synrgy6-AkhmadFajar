package id.achfajar.challenge4.repository;

import id.achfajar.challenge4.model.Merchant;
import id.achfajar.challenge4.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, UUID> {

    @Procedure("update_merchant")
    void updateMerchant(@Param("id_in") UUID uuid,
                    @Param("new_name") String name,
                    @Param("new_loc") String location);
    List<Merchant> findByUsers(Users user);
}

