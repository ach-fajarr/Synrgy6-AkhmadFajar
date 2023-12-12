package id.achfajar.challenge6.service;

import id.achfajar.challenge6.model.Product;
import id.achfajar.challenge6.dto.ProductDTO;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO updateProduct(UUID id, ProductDTO productDTO);

    ProductDTO deleteProduct(UUID id);

    List<ProductDTO> getAllProducts();

    ProductDTO getProductDTOById(UUID id);

    Product getProductById(UUID id);

    List<ProductDTO> getProductsByMerchant(UUID merchantId);

    List<ProductDTO> filterProducts(String filterCriteria);
}

