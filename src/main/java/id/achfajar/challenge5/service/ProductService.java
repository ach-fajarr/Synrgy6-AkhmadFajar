package id.achfajar.challenge5.service;

import id.achfajar.challenge5.model.Product;
import id.achfajar.challenge5.model.dto.ProductDTO;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<ProductDTO> getAllProducts();

    ProductDTO getProductDTOById(UUID id);
    Product getProductById(UUID id);

    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO updateProduct(UUID id, ProductDTO productDTO);

    void deleteProduct(UUID id);

    List<ProductDTO> getProductsByMerchant(UUID merchantId);

    List<ProductDTO> filterProducts(String filterCriteria);
}

