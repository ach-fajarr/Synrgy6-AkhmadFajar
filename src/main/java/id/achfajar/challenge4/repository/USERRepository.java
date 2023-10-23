package id.achfajar.challenge4.repository;

import id.achfajar.challenge4.model.USERR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface USERRepository extends JpaRepository<USERR, Long> {
}