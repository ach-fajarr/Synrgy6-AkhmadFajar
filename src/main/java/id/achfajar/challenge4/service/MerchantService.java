package id.achfajar.challenge4.service;

import id.achfajar.challenge4.model.Merchant;
import id.achfajar.challenge4.model.Product;
import id.achfajar.challenge4.model.Users;
import id.achfajar.challenge4.repository.MerchantRepository;
import id.achfajar.challenge4.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MerchantService {
    @Autowired
    MerchantRepository merchantRepository;
    @Autowired
    ProductRepository productRepository;


    public void updateMerchant(UUID id, String name, String loc){
        merchantRepository.updateMerchant(id, name, loc);
    }
    public void saveMerchant(Merchant merchant){
        merchantRepository.save(merchant);
    }
    public List<Merchant> getAll(){
        return merchantRepository.findAll();
    }
    public List<Merchant> getByUser(Users user){
        return merchantRepository.findByUsers(user);
    }
    //========================================================================================
    public void saveProduct(Product product){
        productRepository.save(product);
    }
    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }
}

