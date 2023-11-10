package id.achfajar.challenge5.repository;

import id.achfajar.challenge5.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsersRepository extends JpaRepository<Users, UUID> {
}
