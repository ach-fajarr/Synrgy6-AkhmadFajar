package id.achfajar.challenge4.repository;

import id.achfajar.challenge4.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface UsersRepository extends JpaRepository<Users, UUID> {

    @Procedure("update_user")
    void updateUser(@Param("id_in") UUID uuid,
                    @Param("mail") String mail,
                    @Param("pass") String newPass,
                    @Param("uname") String name);

    @Procedure("add_user")
    void addUser(@Param("mail") String mail,
                 @Param("pass") String newPass,
                 @Param("uname") String name);

    @Query(nativeQuery = true, value = "SELECT * FROM login_user(:email, :password)")
    UUID login(@Param("email") String email,
               @Param("password") String password);

    Optional<Users> findById(UUID userId);

    Users findByEmail(String mail);
    Users findByEmailAndPassword(String mail, String pass);
}
