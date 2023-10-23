package id.achfajar.challenge4.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Accessors(chain = true)
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private LocalDateTime order_time;
    private String destination_address;
    private boolean completed;

    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "users_id", insertable = false, updatable = false)
    private Users users;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orders", fetch = FetchType.EAGER)
    public List<OrderDetail> orderDetailList;

    public String getStatus() {
        return completed ? "Selesai" : "Dibatalkan";
    }


}
