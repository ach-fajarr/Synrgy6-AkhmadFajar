package id.achfajar.challenge4.service;

import id.achfajar.challenge4.controller.BinarFudController;
import id.achfajar.challenge4.model.Merchant;
import id.achfajar.challenge4.model.Product;
import id.achfajar.challenge4.model.ProductType;
import id.achfajar.challenge4.repository.ProductRepository;
import id.achfajar.challenge4.repository.ProductTypeRepository;
import id.achfajar.challenge4.view.ErrorView;
import id.achfajar.challenge4.view.MerchantView;
import jakarta.persistence.EntityManager;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductTypeRepository productTypeRepository;
    @Autowired
    MerchantService merchantService;
    @Autowired
    EntityManager entityManager;
    BinarFudController c = new BinarFudController();
    private Map<Integer, Product> productMap = new HashMap<>();
    @Setter
    Product thisProduct;
    public List<ProductType> productTypesSelected = new ArrayList<>();

    public void mapProductByMerchant() {
        List<Product> products = merchantService.getThisMerchant().getProductList();
        if (products.isEmpty()) {
            System.out.println("Produk kosong di toko ini");
        } else {
            int i = 1;
            for (Product p : products) {
                productMap.put(i, p);
                i++;
            }
        }
    }
    public void printProductByMerchant(){
        mapProductByMerchant();
        Merchant m = merchantService.getThisMerchant();
        System.out.println("Toko: "+m.getMerchant_name()+" | "+m.getMerchant_location()+" | "+m.getStatus());
        for (Map.Entry<Integer, Product> entry : productMap.entrySet()) {
            Integer i = entry.getKey();
            Product p = entry.getValue();
            System.out.print("\n"+i+". "+p.getProduct_name()+"\t"+p.getPrice()+"\t");
            List<ProductType> productTypes = p.getProductTypes();
            if (productTypes.isEmpty()){
                System.out.print("Belum ditambahkan jenis produk");
            } else {
                productTypes.forEach(pt-> System.out.print(pt.getName()+", "));
            }
        }
        System.out.println();
    }
    public boolean findProduct(int input){
        return productMap.keySet().stream()
                .filter(key -> key == input)
                .findFirst()
                .map(key -> {
                    Product product = productMap.get(key);
                    setThisProduct(product);
                    return true;
                })
                .orElse(false);
    }
    public void infoProductSelected(){
        System.out.println("untuk produk => "+thisProduct.getProduct_name()+" | "
                +thisProduct.getPrice()+" | ");
    }
    public void updateProduct(int input){
        if(!findProduct(input)){
            ErrorView.wrongInput();
        } else {
            MerchantView.headerInfo("Ubah data produk");
            infoProductSelected();
            MerchantView.fieldProductName();
            String name = c.inputLine();
            MerchantView.fieldProductPrice();
            Integer price = c.inputInt();
            selectType(thisProduct);
            productRepository.save(thisProduct.setProductTypes(productTypesSelected));
            updateProduct(thisProduct.getId(),name,price);
        }
    }
    public void deleteProduct(int inputProduct) {
        if(!findProduct(inputProduct)){
            ErrorView.wrongInput();
        } else {
            MerchantView.headerInfo("Hapus produk");
            infoProductSelected();
            MerchantView.cancel("produk");
            while (true){
                String input = c.inputLine();
                if (input.equals("y")) {
                    productRepository.delete(thisProduct);
                    break;
                } else if (input.equals("t")) {
                    break;
                } else {ErrorView.wrongInput();}
            }
        }
    }
    public void createProduct(){
        MerchantView.headerInfo("Pembuatan produk baru");
        merchantService.infoMerchantSelected();
        MerchantView.cancelOption();
        MerchantView.fieldProductName();
        String name = c.inputLine();
        if (name.equals("x")){
            MerchantView.infoCreateFail("produk");
        } else {
            MerchantView.fieldProductPrice();
            Integer price = c.inputInt();
            if (price.equals("x")){
                MerchantView.infoCreateFail("produk");
            } else {
                //save repo
                Product pd = new Product();
                pd.setProduct_name(name);
                pd.setPrice(price);
                pd.setMerchant(merchantService.getThisMerchant());
                selectType(pd);
                pd.setProductTypes(productTypesSelected);
                saveProduct(pd);
                productTypesSelected.clear();
                MerchantView.infoCreateOK("Produk");
            }
        }
    }
    public void selectType(Product pd){
        boolean active = true;
        while(active){
            MerchantView.selectedID(productTypesSelected);
            MerchantView.inputTypeID(getAllType());
            int input = c.inputInt();
            if(input==0){
                active=false;
            } else {
                ProductType pt = getByID(input);
                productTypesSelected.add(pt);
                pt.setProducts(Arrays.asList(pd));
//                pt=entityManager.merge(pt);
                productTypeRepository.save(pt);
            }
        }
    }
    public void initiateProductType(){
        if(productTypeRepository.count()==0){
            productTypeRepository.save(new ProductType().setName("Minuman Dingin"));
            productTypeRepository.save(new ProductType().setName("Minuman Panas"));
            productTypeRepository.save(new ProductType().setName("Kopi"));
            productTypeRepository.save(new ProductType().setName("Nasi"));
            productTypeRepository.save(new ProductType().setName("Kue"));
            productTypeRepository.save(new ProductType().setName("Manis"));
            productTypeRepository.save(new ProductType().setName("Pedas"));
        }
    }

    //==================================================================================================

    public void saveProduct(Product product){
        productRepository.save(product);
    }
    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }
    public List<Product> getOpenMerchants() {
        return productRepository.findOpenMerchantProducts();
    }
    public List<Product> filterProductsByName(String name) {
        return productRepository.findProductByName(name);
    }
    public List<Product> filterProductsByProductType(Integer id) {
        return productRepository.findByProductTypesId(id);
    }
    public List<ProductType> getAllType(){
        return productTypeRepository.findAll();
    }
    public ProductType getByID(int id){
        Optional<ProductType> productType = productTypeRepository.findById(id);
        return productType.orElse(null);
    }
    public void updateProduct(UUID id, String name, Integer price){
        productRepository.updateProduct(id, name, price);
    }
}
