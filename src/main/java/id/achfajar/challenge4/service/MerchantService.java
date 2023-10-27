package id.achfajar.challenge4.service;

import id.achfajar.challenge4.controller.BinarFudController;
import id.achfajar.challenge4.model.Merchant;
import id.achfajar.challenge4.model.Product;
import id.achfajar.challenge4.model.ProductType;
import id.achfajar.challenge4.model.Users;
import id.achfajar.challenge4.repository.MerchantRepository;
import id.achfajar.challenge4.repository.ProductRepository;
import id.achfajar.challenge4.repository.ProductTypeRepository;
import id.achfajar.challenge4.view.ErrorView;
import id.achfajar.challenge4.view.MerchantView;
import jakarta.persistence.EntityManager;
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
    @Autowired
    ProductTypeRepository productTypeRepository;
    @Autowired
    EntityManager entityManager;
    MerchantView viewM = new MerchantView();
    BinarFudController c = new BinarFudController();
    private final static Logger logger = LoggerFactory.getLogger(MerchantService.class);
    private Map<Integer, Merchant> merchantMap = new HashMap<>();
    @Setter
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
                mc=entityManager.merge(mc);
                saveMerchant(mc);
                viewM.infoCreateOK("Toko");
            }
        }
    }
    public void updateMerchant(int input){
        if(!findMerchant(input)){
            ErrorView.wrongInput();
        } else {
            viewM.headerInfo("Ubah data toko");
            infoMerchant();
            viewM.fieldName();
            String name = c.inputLine();
            viewM.fieldLocation();
            String loc = c.inputLine();
            updateMerchant(thisMerchant.getId(),name,loc);
        }
    }
    public void updateMerchantStatus(int input) {
        if(!findMerchant(input)){
            ErrorView.wrongInput();
        } else {
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
            saveMerchant(thisMerchant);}
    }
    public void deleteMerchant(int inputMerchant) {
        if(!findMerchant(inputMerchant)){
            ErrorView.wrongInput();
        } else {
            viewM.headerInfo("Hapus toko");
            infoMerchant();
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
                selectType(pd);
                pd.setProductTypes(productTypesSelected);
                saveProduct(pd);
                productTypesSelected.clear();
                viewM.infoCreateOK("Produk");
            }
        }
    }
    public List<ProductType> productTypesSelected = new ArrayList<>();
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
}

