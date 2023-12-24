package id.achfajar.challenge8.model;

import id.achfajar.challenge8.repository.RoleRepository;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private ERole name;

    @ManyToMany(mappedBy = "roles")
    private Set<Users> users = new HashSet<>();

    public static void initializeRoles(RoleRepository roleRepository) {
        for (ERole eRole : ERole.values()) {
            Role role = roleRepository.findByName(eRole)
                    .orElse(new Role().setName(eRole));
            roleRepository.save(role);
        }
    }
}
