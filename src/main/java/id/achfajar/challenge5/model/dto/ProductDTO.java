package id.achfajar.challenge5.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ProductDTO {
    private UUID id;
    private String productName;
    private long price;
    private String merchantName;
//    private List<ProductTypeDTO> productTypes;
}
