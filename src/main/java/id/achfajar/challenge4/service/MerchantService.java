package id.achfajar.challenge4.service;

import id.achfajar.challenge4.controller.BinarFudController;
import id.achfajar.challenge4.model.Merchant;
import id.achfajar.challenge4.model.Product;
import id.achfajar.challenge4.model.Users;
import id.achfajar.challenge4.repository.MerchantRepository;
import id.achfajar.challenge4.repository.ProductRepository;
import id.achfajar.challenge4.view.ErrorView;
import id.achfajar.challenge4.view.MerchantView;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MerchantService {
    @Autowired
    MerchantRepository merchantRepository;
    @Autowired
    ProductRepository productRepository;
    MerchantView viewM = new MerchantView();
    ErrorView viewE = new ErrorView();
    BinarFudController c = new BinarFudController();
    private final static Logger logger = LoggerFactory.getLogger(MerchantService.class);
    private Map<Integer, Merchant> merchantMap = new HashMap<>();
    @Setter
    private Merchant thisMerchant;


    public void addMerchant(Users user) {
        viewM.headerInfo("Pembuatan toko baru");
        viewM.cancelOption();
        viewM.fieldName();
        String name = c.inputLine();
        if (name.equals("x")){
            viewM.infoCreateFail("toko");
        } else {
            viewM.fieldLocation();
            String address = c.inputLine();
            if (address.equals("x")){
                viewM.infoCreateFail("toko");
            } else {
                //save repo
                Merchant mc = new Merchant();
                mc.setMerchant_name(name);
                mc.setMerchant_location(address);
                mc.setOpen(true);
                mc.setUsers(user);
                merchantRepository.save(mc);
                viewM.infoCreateOK("Toko");
            }
        }
    }
    public void createProduct(int input){
        findMerchant(input);
        viewM.headerInfo("Pembuatan produk baru");
        infoMerchant();
        viewM.cancelOption();
        viewM.fieldProductName();
        String name = c.inputLine();
        if (name.equals("x")){
            viewM.infoCreateFail("produk");
        } else {
            viewM.fieldProductPrice();
            Integer price = c.inputInt();

            //save repo
            Product pd = new Product();
            pd.setProduct_name(name);
            pd.setPrice(price);
            pd.setMerchant(thisMerchant);
            productRepository.save(pd);
            viewM.infoCreateOK("Produk");
        }
    }
    public void mapAllbyUser(Users user) {
        List<Merchant> mc = merchantRepository.findByUsers(user);
        if (mc.isEmpty()) {
            System.out.println("Anda belum memiliki toko");
        } else {
            int i = 1;
            for (Merchant m : mc) {
                merchantMap.put(i, m);
                i++;
            }
        }
    }
    public void printMerchant() {
        merchantMap.forEach((i, m) -> {
            System.out.println("\n"+ i + ".\tNama toko \t: " + m.getMerchant_name() +
                    "\n\tLokasi \t\t\t: " + m.getMerchant_location() +
                    "\n\tStatus buka \t: " + m.getStatus() +
                    "\n\tProduk \t: ");
            List<Product> productList = m.getProductList();
            if (productList.isEmpty()){
                System.out.println("\ttoko ini tidak memiliki produk");
            } else {
                for (Product pd: productList){
                    System.out.println("\t\t- "+pd.getProduct_name()+" | "+pd.getPrice());
                }
            }
        });
    }
    public void clearMap() {
        merchantMap.clear();
    }
    public void findMerchant(int input){
        merchantMap.keySet().stream()
                .filter(key -> key == input)
                .findFirst()
                .ifPresentOrElse(
                        key -> {
                            Merchant merchant = merchantMap.get(key);
                            setThisMerchant(merchant);
                            },
                        viewE::wrongInput
                );
    }
    private void infoMerchant(){
        System.out.println("untuk toko => "+thisMerchant.getMerchant_name()+" | "
                +thisMerchant.getMerchant_location()+" | "+thisMerchant.getStatus());
    }
    public void updateMerchantName(int input) {
        findMerchant(input);
        viewM.headerInfo("Ubah nama toko");
        infoMerchant();
        viewM.fieldName();
        String name = c.inputLine();
        thisMerchant.setMerchant_name(name);
        merchantRepository.save(thisMerchant);
    }
    public void updateMerchantLoc(int input) {
        findMerchant(input);
        viewM.headerInfo("Ubah lokasi toko");
        infoMerchant();
        viewM.fieldLocation();
        String loc = c.inputLine();
        thisMerchant.setMerchant_location(loc);
        merchantRepository.save(thisMerchant);
    }
    public void updateMerchantStatus(int input) {
        findMerchant(input);
        viewM.headerInfo("Ubah status toko");
        infoMerchant();
        viewM.statusOption();
        try {
            int set = c.inputInt();
            switch (set){
                case 1 -> thisMerchant.setOpen(true);
                case 2 -> thisMerchant.setOpen(false);
                default -> updateMerchantStatus(input);
            }
        } catch (InputMismatchException e){
            updateMerchantStatus(input);
        }
        merchantRepository.save(thisMerchant);
    } 
    public List<Merchant> getAll(){
        return merchantRepository.findAll();
    }

}

