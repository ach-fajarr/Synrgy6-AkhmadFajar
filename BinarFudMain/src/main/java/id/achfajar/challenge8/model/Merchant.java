package id.achfajar.challenge8.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Accessors(chain = true)
@NoArgsConstructor
@Entity
@Table(name = "merchant")
public class Merchant extends AuditModel{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "merchant_name")
    private String merchant_name;

    @Column(name = "merchant_location")
    private String merchant_location;
    private boolean open;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "merchant", fetch = FetchType.EAGER)
    private List<Product> productList;

    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "users_id")
    private Users users;
}
