package id.achfajar.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
public class Product {
    private UUID id;
    private String product_name;
    private int price;
    private Merchant merchant;
}
