package id.achfajar.challenge4.controller;

import id.achfajar.challenge4.model.Merchant;
import id.achfajar.challenge4.model.Product;
import id.achfajar.challenge4.model.Users;
import id.achfajar.challenge4.service.MerchantService;
import id.achfajar.challenge4.view.ErrorView;
import id.achfajar.challenge4.view.MerchantView;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;

@Component
public class MerchantController {

    @Autowired
    MerchantService merchantService;

    MerchantView viewM = new MerchantView();
    ErrorView viewE = new ErrorView();
    BinarFudController c = new BinarFudController();
    private final static Logger logger = LoggerFactory.getLogger(MerchantService.class);
    private Map<Integer, Merchant> merchantMap = new HashMap<>();
    @Setter
    private Merchant thisMerchant;

    public void mapAllbyUser(Users user) {
        List<Merchant> mc = merchantService.getByUser(user);
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
    public void printMerchant(Users user) {
        mapAllbyUser(user);
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
    public void clearMap() {
        merchantMap.clear();
    }
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
                merchantService.saveMerchant(mc);
                viewM.infoCreateOK("Toko");
            }
        }
    }
    public void updateMerchant(int input){
        findMerchant(input);
        viewM.headerInfo("Ubah data toko");
        infoMerchant();
        viewM.fieldName();
        String name = c.inputLine();
        viewM.fieldLocation();
        String loc = c.inputLine();
        merchantService.updateMerchant(thisMerchant.getId(),name,loc);
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
        merchantService.saveMerchant(thisMerchant);
    }
    //==================================================================================
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
            if (price.equals("x")){
                viewM.infoCreateFail("produk");
            } else {
                //save repo
                Product pd = new Product();
                pd.setProduct_name(name);
                pd.setPrice(price);
                pd.setMerchant(thisMerchant);
                merchantService.saveProduct(pd);
                viewM.infoCreateOK("Produk");
            }
        }
    }
}
