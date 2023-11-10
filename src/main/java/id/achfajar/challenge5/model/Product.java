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
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String product_name;
    private long price;

    @ManyToOne(targetEntity = Merchant.class)
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<OrderDetail> orderDetailList;

    public int getTotalOrderedQuantity() {
        return orderDetailList.stream()
                .mapToInt(OrderDetail::getQuantity)
                .sum();
    }

    public double getTotalOrderedPrice() {
        return orderDetailList.stream()
                .mapToDouble(OrderDetail::getTotal_price)
                .sum();
    }

    public double getTotalOrderedPriceByDateRange(LocalDate startDate, LocalDate endDate) {
        return orderDetailList.stream()
                .filter(orderDetail -> isWithinDateRange(orderDetail.getOrders().getOrder_time(), startDate, endDate))
                .mapToDouble(OrderDetail::getTotal_price)
                .sum();
    }

    private boolean isWithinDateRange(LocalDate date, LocalDate startDate, LocalDate endDate) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }
}