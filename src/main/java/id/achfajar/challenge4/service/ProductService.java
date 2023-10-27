//package id.achfajar.challenge4.service;
//
//import id.achfajar.challenge4.model.Product;
//import id.achfajar.challenge4.model.ProductType;
//import id.achfajar.challenge4.view.MerchantView;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//@Service
//public class ProductService {
//    public void createProduct(int input){
//        findMerchant(input);
//        viewM.headerInfo("Pembuatan produk baru");
//        infoMerchant();
//        viewM.cancelOption();
//        viewM.fieldProductName();
//        String name = c.inputLine();
//        if (name.equals("x")){
//            viewM.infoCreateFail("produk");
//        } else {
//            viewM.fieldProductPrice();
//            Integer price = c.inputInt();
//            if (price.equals("x")){
//                viewM.infoCreateFail("produk");
//            } else {
//                //save repo
//                Product pd = new Product();
//                pd.setProduct_name(name);
//                pd.setPrice(price);
//                pd.setMerchant(thisMerchant);
//                selectType(pd);
//                pd.setProductTypes(productTypesSelected);
//                saveProduct(pd);
//                productTypesSelected.clear();
//                viewM.infoCreateOK("Produk");
//            }
//        }
//    }
//    public List<ProductType> productTypesSelected = new ArrayList<>();
//    public void selectType(Product pd){
//        boolean active = true;
//        while(active){
//            MerchantView.selectedID(productTypesSelected);
//            MerchantView.inputTypeID(getAllType());
//            int input = c.inputInt();
//            if(input==0){
//                active=false;
//            } else {
//                ProductType pt = getByID(input);
//                productTypesSelected.add(pt);
//                pt.setProducts(Arrays.asList(pd));
//            }
//        }
//    }
//    public void initiateProductType(){
//        if(productTypeRepository.count()==0){
//            productTypeRepository.save(new ProductType().setName("Minuman Dingin"));
//            productTypeRepository.save(new ProductType().setName("Minuman Panas"));
//            productTypeRepository.save(new ProductType().setName("Kopi"));
//            productTypeRepository.save(new ProductType().setName("Nasi"));
//            productTypeRepository.save(new ProductType().setName("Kue"));
//            productTypeRepository.save(new ProductType().setName("Manis"));
//            productTypeRepository.save(new ProductType().setName("Pedas"));
//        }
//    }
//}
