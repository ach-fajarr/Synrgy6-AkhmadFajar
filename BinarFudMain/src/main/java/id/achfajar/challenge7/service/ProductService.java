package id.achfajar.challenge7.service;

import id.achfajar.challenge7.model.Product;
import id.achfajar.challenge7.dto.ProductDTO;

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

