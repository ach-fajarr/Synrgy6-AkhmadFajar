package id.achfajar.challenge4.repository;

import id.achfajar.challenge4.model.Merchant;
import id.achfajar.challenge4.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, UUID> {
    List<Merchant> findByOpen(boolean isOpen);

    List<Merchant> findByUsers(Users user);
}

