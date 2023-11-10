package id.achfajar.challenge5.controller;

import id.achfajar.challenge5.model.dto.ProductDTO;
import id.achfajar.challenge5.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        List<ProductDTO> productDTOs = productService.getAllProducts();
        return productDTOs;
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable UUID id) {
        return productService.getProductDTOById(id);
    }

    @PostMapping
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO) {
        return productService.createProduct(productDTO);
    }

    @PutMapping("/{id}")
    public ProductDTO updateProduct(@PathVariable UUID id, @RequestBody ProductDTO productDTO) {
        return productService.updateProduct(id, productDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/by-merchant/{merchantId}")
    public List<ProductDTO> getProductsByMerchant(@PathVariable UUID merchantId) {
        return productService.getProductsByMerchant(merchantId);
    }

    @GetMapping("/filter")
    public List<ProductDTO> filterProducts(@RequestParam String filterCriteria) {
        return productService.filterProducts(filterCriteria);
    }
}

