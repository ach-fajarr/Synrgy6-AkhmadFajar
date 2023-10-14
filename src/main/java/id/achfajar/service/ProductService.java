package id.achfajar.service;

import id.achfajar.model.Merchant;
import id.achfajar.model.Product;
import id.achfajar.utils.Utils;

import static id.achfajar.model.Data.foodMap;

public class ProductService {
    public static void printProduct(){
        foodMap.entrySet().stream()
                .forEach(entry -> {
                    int foodKey = entry.getKey();
                    Product product = entry.getValue();
                    Merchant merchant = product.getMerchant();
                    var price = Utils.setCurrency(product.getPrice());

                    System.out.println(foodKey +" "+product.getProduct_name()+" "+price+ "\n"
                    +"\t \t" +merchant.getMerchant_name()+"\t"+merchant.getMerchant_location()+"\t"+merchant.getStatus()+"\n");
                });
    }
}
