package id.achfajar.challenge6.repository;

import id.achfajar.challenge6.model.ERole;
import id.achfajar.challenge6.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole eRole);
}
