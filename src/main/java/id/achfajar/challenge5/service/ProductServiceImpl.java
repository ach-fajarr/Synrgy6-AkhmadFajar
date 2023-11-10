package id.achfajar.challenge5.service;

import id.achfajar.challenge5.exception.ResourceNotFoundException;
import id.achfajar.challenge5.model.Merchant;
import id.achfajar.challenge5.model.Product;
import id.achfajar.challenge5.model.dto.ProductDTO;
import id.achfajar.challenge5.repository.MerchantRepository;
import id.achfajar.challenge5.repository.ProductRepository;
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
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOs = new ArrayList<>();

        products.forEach(p-> productDTOs.add(mapToDTO(p)));

        return productDTOs;
    }

    @Override
    public ProductDTO getProductDTOById(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product dengan id: " + id + "tidak ditemukan"));
        return mapToDTO(product);
    }
    @Override
    public Product getProductById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product dengan id: " + id + "tidak ditemukan"));
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
                .orElseThrow(() -> new ResourceNotFoundException("Product dengan id: " + id + "tidak ditemukan"));

        Product updatedProduct = mapToEntity(productDTO);
        updatedProduct.setId(existingProduct.getId());
        updatedProduct = productRepository.save(updatedProduct);
        return mapToDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product dengan id: " + id + "tidak ditemukan"));
        productRepository.delete(product);
    }

    @Override
    public List<ProductDTO> getProductsByMerchant(UUID merchantId) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant dengan id: " + merchantId + "tidak ditemukan"));

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
        productDTO.setId(product.getId());
        productDTO.setProductName(product.getProduct_name());
        productDTO.setPrice(product.getPrice());
//        productDTO.setProductTypes(mapProductTypesToDTO(product.getProductTypes()));
        if (product.getMerchant() != null) {
            productDTO.setMerchantName(product.getMerchant().getMerchant_name());
        }

        return productDTO;
    }

    private Product mapToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setProduct_name(productDTO.getProductName());
        product.setPrice(productDTO.getPrice());
        Merchant merchant = merchantRepository.findByMerchantName(productDTO.getMerchantName())
                .orElseThrow(() -> new ResourceNotFoundException("Merchant tidak ditemukan"));
        product.setMerchant(merchant);
        return product;
    }

    //    private List<ProductTypeDTO> mapProductTypesToDTO(List<ProductType> productTypes) {
//        List<ProductTypeDTO> productTypeDTOs = new ArrayList<>();
//        for (ProductType productType : productTypes) {
//            ProductTypeDTO productTypeDTO = new ProductTypeDTO();
//            productTypeDTO.setId(productType.getId());
//            productTypeDTO.setType(productType.getType());
//            productTypeDTOs.add(productTypeDTO);
//        }
//        return productTypeDTOs;
//    }
}

