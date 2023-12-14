package id.achfajar.challenge7.repository;

import id.achfajar.challenge7.model.ERole;
import id.achfajar.challenge7.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole eRole);
}
