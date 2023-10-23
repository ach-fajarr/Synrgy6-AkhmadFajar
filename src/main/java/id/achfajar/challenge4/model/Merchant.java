package id.achfajar.challenge4.model;

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
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String merchant_name;
    private String merchant_location;
    private boolean open;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "merchant", fetch = FetchType.EAGER)
    private List<Product> productList;

    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "users_id")
    private Users users;

    public String getStatus() {
        return open ? "Buka" : "Tutup";
    }
}
