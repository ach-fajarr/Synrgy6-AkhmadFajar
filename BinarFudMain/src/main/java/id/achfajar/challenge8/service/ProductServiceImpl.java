package id.achfajar.challenge8.service;

import id.achfajar.challenge8.exception.ResourceNotFoundException;
import id.achfajar.challenge8.model.Merchant;
import id.achfajar.challenge8.model.Product;
import id.achfajar.challenge8.dto.ProductDTO;
import id.achfajar.challenge8.repository.MerchantRepository;
import id.achfajar.challenge8.repository.ProductRepository;
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
        Product existingProduct = getProductById(id);
        if(productDTO.getProductName()!=null){
            existingProduct.setProduct_name(productDTO.getProductName());
        } else if (productDTO.getPrice()!=null) {
            existingProduct.setPrice(productDTO.getPrice());
        } else if (productDTO.getStock()!=null) {
            int newStock = existingProduct.getStock()+productDTO.getStock();
            existingProduct.setStock(newStock);
        }
        productRepository.save(existingProduct);
        return mapToDTO(existingProduct);
    }

    @Override
    public ProductDTO addDiscount(UUID id, Integer discount){
        Product product = getProductById(id);
        if(discount==null || discount.equals(0)){
            product.setDiscount(null);
        }
        product.setDiscount(discount);
        productRepository.save(product);
        return mapToDTO(product);
    }

    @Override
    public ProductDTO updateStock2(UUID id, Integer add, Integer min){
        Product product = getProductById(id);
        if(add!=null){
            product.setStock(product.getStock()+add);
        } else if (min!=null) {
            product.setStock(product.getStock()-min);
        }
        productRepository.save(product);
        System.out.println("UPDATE STOCK");
        System.out.println("Stock  :  "+ product.getStock());
        return mapToDTO(product);
    }

    @Override
    public void updateStock(UUID id, int quantity){
        Product product = getProductById(id);
        product.setStock(product.getStock()-quantity);
        productRepository.save(product);
        System.out.println("UPDATE STOCK");
        System.out.println("Stock  :  "+ product.getStock());
    }

    @Override
    public ProductDTO deleteProduct(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        productRepository.delete(product);
        return mapToDTO(product);
    }

    @Override
    public List<Product> getDiscountedProduct(){
        return productRepository.findByDiscountIsNotNull();
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
        productDTO.setStock(product.getStock());
        productDTO.setDiscount(product.getDiscount());
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
        product.setStock(productDTO.getStock());
        product.setDiscount(productDTO.getDiscount());
        product.setMerchant(merchantRepository
                .findById(productDTO.getMerchantID())
                .orElseThrow(()->new ResourceNotFoundException(productDTO.getMerchantID())));
        return product;
    }
}

