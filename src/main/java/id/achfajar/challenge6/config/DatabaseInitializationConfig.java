package id.achfajar.challenge6.config;

import id.achfajar.challenge6.model.Role;
import id.achfajar.challenge6.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseInitializationConfig {

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void initializeDatabase() {
        Role.initializeRoles(roleRepository);
    }
}
