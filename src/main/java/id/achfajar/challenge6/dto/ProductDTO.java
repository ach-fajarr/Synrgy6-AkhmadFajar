package id.achfajar.challenge6.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductDTO {
    private UUID merchantID;
    private String merchantName;
    private UUID productId;
    private String productName;
    private Integer price;
//    private List<ProductTypeDTO> productTypes;
}
