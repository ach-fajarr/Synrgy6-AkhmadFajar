package id.achfajar.challenge7.service;

import id.achfajar.challenge7.exception.ResourceNotFoundException;
import id.achfajar.challenge7.model.Merchant;
import id.achfajar.challenge7.model.Product;
import id.achfajar.challenge7.dto.ProductDTO;
import id.achfajar.challenge7.repository.MerchantRepository;
import id.achfajar.challenge7.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final MerchantRepository merchantRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, MerchantRepository merchantRepository) {
        this.productRepository = productRepository;
        this.merchantRepository = merchantRepository;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = mapToEntity(productDTO);
        product = productRepository.save(product);
        return mapToDTO(product);
    }

    @Override
    public ProductDTO updateProduct(UUID id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        if(productDTO.getProductName()!=null){
            existingProduct.setProduct_name(productDTO.getProductName());
        } else if (productDTO.getPrice()!=null) {
            existingProduct.setPrice(productDTO.getPrice());
        }
        productRepository.save(existingProduct);
        return mapToDTO(existingProduct);
    }

    @Override
    public ProductDTO deleteProduct(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        productRepository.delete(product);
        return mapToDTO(product);
    }

    /** ========================= {@code @PUBLIC} =========================*/
    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOs = new ArrayList<>();
        products.forEach(p-> productDTOs.add(mapToDTO(p)));
        return productDTOs;
    }

    @Override
    public ProductDTO getProductDTOById(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return mapToDTO(product);
    }

    @Override
    public Product getProductById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Override
    public List<ProductDTO> getProductsByMerchant(UUID merchantId) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException(merchantId));
        List<Product> products = productRepository.findAllByMerchant(merchant);
        List<ProductDTO> productDTOs = new ArrayList<>();
        products.forEach(p-> productDTOs.add(mapToDTO(p)));
        return productDTOs;
    }

    @Override
    public List<ProductDTO> filterProducts(String filterCriteria) {
        List<Product> products = productRepository.findAllByProductNameContaining(filterCriteria);
        List<ProductDTO> productDTOs = new ArrayList<>();
        products.forEach(p-> productDTOs.add(mapToDTO(p)));
        return productDTOs;
    }

    private ProductDTO mapToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(product.getId());
        productDTO.setProductName(product.getProduct_name());
        productDTO.setPrice(product.getPrice());
        if (product.getMerchant() != null) {
            productDTO.setMerchantName(product.getMerchant().getMerchant_name());
            productDTO.setMerchantID(product.getMerchant().getId());
        }
        return productDTO;
    }

    private Product mapToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getProductId());
        product.setProduct_name(productDTO.getProductName());
        product.setPrice(productDTO.getPrice());
        product.setMerchant(merchantRepository
                .findById(productDTO.getMerchantID())
                .orElseThrow(()->new ResourceNotFoundException(productDTO.getMerchantID())));
        return product;
    }
}

