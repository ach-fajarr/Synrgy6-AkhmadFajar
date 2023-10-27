package id.achfajar.challenge4.service;

import id.achfajar.challenge4.controller.BinarFudController;
import id.achfajar.challenge4.model.Merchant;
import id.achfajar.challenge4.model.Product;
import id.achfajar.challenge4.model.ProductType;
import id.achfajar.challenge4.model.Users;
import id.achfajar.challenge4.repository.MerchantRepository;
import id.achfajar.challenge4.view.ErrorView;
import id.achfajar.challenge4.view.MerchantView;
import jakarta.persistence.EntityManager;
import lombok.Getter;
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
    BinarFudController c = new BinarFudController();
    private final static Logger logger = LoggerFactory.getLogger(MerchantService.class);
    private Map<Integer, Merchant> merchantMap = new HashMap<>();
    @Setter
    @Getter
    Merchant thisMerchant;

    public void mapAllbyUser(Users user) {
        List<Merchant> mc = getByUser(user);
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
                    System.out.print("\t\t- "+pd.getProduct_name()+" | "+pd.getPrice()+"\tJenis : ");

                    List<ProductType> productTypes = pd.getProductTypes();
                    if (productTypes.isEmpty()){
                        System.out.print("Belum ditambahkan jenis produk");
                    } else {
                        productTypes.forEach(pt-> System.out.print(pt.getName()+", "));
                    }
                    System.out.println();
                }
            }
        });
    }
    public boolean findMerchant(int input){
    return merchantMap.keySet().stream()
            .filter(key -> key == input)
            .findFirst()
            .map(key -> {
                Merchant merchant = merchantMap.get(key);
                setThisMerchant(merchant);
                return true;
            })
            .orElse(false);
    }
    public void infoMerchantSelected(){
        System.out.println("untuk toko => "+thisMerchant.getMerchant_name()+" | "
                +thisMerchant.getMerchant_location()+" | "+thisMerchant.getStatus());
    }
    public void clearMap() {
        merchantMap.clear();
    }
    public void addMerchant(Users user) {
        MerchantView.headerInfo("Pembuatan toko baru");
        MerchantView.cancelOption();
        MerchantView.fieldName();
        String name = c.inputLine();
        if (name.equals("x")){
            MerchantView.infoCreateFail("toko");
            logger.error("Dibatalkan");
        } else {
            MerchantView.fieldLocation();
            String address = c.inputLine();
            if (address.equals("x")){
                MerchantView.infoCreateFail("toko");
                logger.error("Dibatalkan");

            } else {
                //save repo
                Merchant mc = new Merchant();
                mc.setMerchant_name(name);
                mc.setMerchant_location(address);
                mc.setOpen(true);
                mc.setUsers(user);
//                mc=entityManager.merge(mc);
                saveMerchant(mc);
                MerchantView.infoCreateOK("Toko");
            }
        }
    }
    public void updateMerchant(int input){
        if(!findMerchant(input)){
            ErrorView.wrongInput();
        } else {
            MerchantView.headerInfo("Ubah data toko");
            infoMerchantSelected();
            MerchantView.fieldName();
            String name = c.inputLine();
            MerchantView.fieldLocation();
            String loc = c.inputLine();
            updateMerchant(thisMerchant.getId(),name,loc);
        }
    }
    public void updateMerchantStatus(int input) {
        if(!findMerchant(input)){
            ErrorView.wrongInput();
        } else {
            MerchantView.headerInfo("Ubah status toko");
            infoMerchantSelected();
            MerchantView.statusOption();
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
            saveMerchant(thisMerchant);}
    }
    public void deleteMerchant(int inputMerchant) {
        if(!findMerchant(inputMerchant)){
            ErrorView.wrongInput();
        } else {
            MerchantView.headerInfo("Hapus toko");
            infoMerchantSelected();
            MerchantView.cancel("toko");
            while (true){
                String input = c.inputLine();
                if (input.equals("y")) {
                    merchantRepository.delete(thisMerchant);
                    break;
                } else if (input.equals("t")) {
                    break;
                } else {ErrorView.wrongInput();}
            }
        }
    }
    //==================================================================================


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

}

