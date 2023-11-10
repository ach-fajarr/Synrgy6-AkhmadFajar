package id.achfajar.challenge5.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
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

    public int getTotalOrderedProducts() {
        return productList.stream()
                .mapToInt(Product::getTotalOrderedQuantity)
                .sum();
    }

    public double getTotalRevenue() {
        return productList.stream()
                .mapToDouble(Product::getTotalOrderedPrice)
                .sum();
    }

    public double getTotalRevenueByDateRange(LocalDate startDate, LocalDate endDate) {
        return productList.stream()
                .mapToDouble(product -> product.getTotalOrderedPriceByDateRange(startDate, endDate))
                .sum();
    }
}
