package id.achfajar.challenge6.controller;

import id.achfajar.challenge6.dto.ProductDTO;
import id.achfajar.challenge6.dto.response.ResponseHandler;
import id.achfajar.challenge6.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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

    /** ==================== {@code @SECURED BY SELLER} ====================*/
    @PostMapping("/add")
    @Secured("ROLE_SELLER")
    public ResponseEntity<Object> createProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO product = productService.createProduct(productDTO);
        return ResponseHandler.generateResponseSuccess(product);
    }

    @PatchMapping("/update")
    @Secured("ROLE_SELLER")
    public ResponseEntity<Object> updateProduct(@RequestParam UUID id, @RequestBody ProductDTO productDTO) {
        ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
        return ResponseHandler.generateResponseSuccess(updatedProduct);
    }

    @DeleteMapping("/delete")
    @Secured("ROLE_SELLER")
    public ResponseEntity<Object> deleteProduct(@RequestParam UUID id) {
        ProductDTO deletedProduct = productService.deleteProduct(id);
        return ResponseHandler.generateResponse(
                "data produk berhasil dihapus", HttpStatus.ACCEPTED, deletedProduct);
    }


    /** ========================= {@code @PUBLIC} =========================*/
    @GetMapping("/show-all")
    public ResponseEntity<?> getAllProducts() {
        List<ProductDTO> productDTOs = productService.getAllProducts();
        return ResponseHandler.generateResponseSuccess(productDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable UUID id) {
        ProductDTO product = productService.getProductDTOById(id);
        return ResponseHandler.generateResponseSuccess(product);
    }

    @GetMapping("/by-merchant/{merchantId}")
    public ResponseEntity<Object> getProductsByMerchant(@PathVariable UUID merchantId) {
        List<ProductDTO> productList = productService.getProductsByMerchant(merchantId);
        return ResponseHandler.generateResponseSuccess(productList);
    }

    @GetMapping("/filter")
    public ResponseEntity<Object> filterProducts(@RequestParam String by) {
        List<ProductDTO> productList = productService.filterProducts(by);
        return ResponseHandler.generateResponseSuccess(productList);
    }
}

