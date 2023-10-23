package id.achfajar.challenge4.repository;

import id.achfajar.challenge4.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface UsersRepository extends JpaRepository<Users, UUID> {

    @Procedure("update_name")
    void updateNameByMail(@Param("email_param") String mail, @Param("nama") String name);

    @Procedure("update_mail")
    void updateMailByMail(@Param("email_param") String mail, @Param("email_new") String newMail);

    @Procedure("update_pass")
    void updatePassByMail(@Param("email_param") String mail, @Param("pass") String newPass);

    @Procedure("")

    Users findByUsername(String name);

    Users findByEmailAndPassword(String mail, String pass);
}
