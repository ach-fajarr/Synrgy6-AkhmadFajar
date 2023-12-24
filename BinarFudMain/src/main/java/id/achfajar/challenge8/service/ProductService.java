package id.achfajar.challenge8.service;

import id.achfajar.challenge8.model.Product;
import id.achfajar.challenge8.dto.ProductDTO;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO updateProduct(UUID id, ProductDTO productDTO);

    ProductDTO addDiscount(UUID id, Integer discount);

    ProductDTO updateStock2(UUID id, Integer add, Integer min);

    void updateStock(UUID id, int quantity);

    ProductDTO deleteProduct(UUID id);

    List<Product> getDiscountedProduct();

    List<ProductDTO> getAllProducts();

    ProductDTO getProductDTOById(UUID id);

    Product getProductById(UUID id);

    List<ProductDTO> getProductsByMerchant(UUID merchantId);

    List<ProductDTO> filterProducts(String filterCriteria);
}

