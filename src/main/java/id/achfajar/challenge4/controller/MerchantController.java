package id.achfajar.challenge4.controller;

import id.achfajar.challenge4.model.Users;
import id.achfajar.challenge4.service.MerchantService;
import id.achfajar.challenge4.service.ProductService;
import id.achfajar.challenge4.view.ErrorView;
import id.achfajar.challenge4.view.MerchantView;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;

@Component
public class MerchantController {

    @Autowired
    MerchantService merchantService;
    @Autowired
    ProductService productService;

    @Setter Users activeUser;
    BinarFudController c = new BinarFudController();
    MerchantView view = new MerchantView();
    public void merchant(Users activeUser) {
        boolean active=true;
        setActiveUser(activeUser);
        while (active){
            try {
                view.merchantHeader();
                merchantService.printMerchant(activeUser);
                view.merchantOption();
                int option = c.inputInt();
                switch (option){
                    case 1 -> merchantService.addMerchant(activeUser);
                    case 0 -> active=false;
                    default -> merchantSett(option);
                }
            } catch (InputMismatchException e) {ErrorView.wrongInput();}
        }

    }
    private void merchantSett(Integer menuSelected) {
        boolean active=true;
        while (active){
            try {
                view.merchantHeader();
                merchantService.printMerchant(activeUser);
                view.selectMerchant();
                int option = c.inputInt();
                switch (option){
                    case 0 -> active=false;
                    default -> {
                        if (menuSelected==2) {
                            merchantService.updateMerchant(option);
                        } else if (menuSelected==3) {
                            merchantService.updateMerchantStatus(option);
                        } else if (menuSelected==4) {
                            merchantService.deleteMerchant(option);
                        } else if (menuSelected==5) {
                            productSetting(option);
                        } else {
                            ErrorView.wrongInput();
                            active=false;
                        }
                    }
                }
            } catch (InputMismatchException e) {ErrorView.wrongInput();}
        }
    }
    private void productSetting(Integer merchantSelected) {
        if(!merchantService.findMerchant(merchantSelected)){
            ErrorView.wrongInput();
        } else {
            boolean active=true;
            while (active){
                try {
                    view.productHeader();
                    productService.printProductByMerchant();
                    MerchantView.productSettingOption();
                    int option = c.inputInt();
                    switch (option){
                        case 1 -> productService.createProduct();
                        case 0 -> active=false;
                        default -> productSett(option);
                    }
                } catch (InputMismatchException e) {ErrorView.wrongInput();}
            }
        }
    }
    private void productSett(Integer menuSelected) {
        boolean active=true;
        while (active){
            try {
                view.productHeader();
                productService.printProductByMerchant();
                view.selectProduct();
                int option = c.inputInt();
                switch (option){
                    case 0 -> active=false;
                    default -> {
                        if (menuSelected==2) {
                            productService.updateProduct(option);
                        } else if (menuSelected==3) {
                            productService.deleteProduct(option);
                        } else {
                            ErrorView.wrongInput();
                            active=false;
                        }
                    }
                }
            } catch (InputMismatchException e) {ErrorView.wrongInput();}
        }
    }
    public void clearMap() {
        merchantService.clearMap();
    }
    public void initiateData() {
        productService.initiateProductType();
    }
    //===================================================================================
//    private void updateMerchant() {
//        boolean active=true;
//        while (active){
//            try {
//                view.merchantHeader();
//                merchantService.printMerchant(activeUser);
//                view.productOption();
//                int option = c.inputInt();
//                switch (option){
//                    case 0 -> active=false;
//                    default -> merchantService.updateMerchant(option);
//                }
//            } catch (InputMismatchException e) {ErrorView.wrongInput();}
//        }
//    }
//
//    private void setOpen() {
//        boolean active=true;
//        while (active){
//            try {
//                view.merchantHeader();
//                merchantService.printMerchant(activeUser);
//                view.productOption();
//                int option = c.inputInt();
//                switch (option){
//                    case 0 -> active=false;
//                    default -> merchantService.updateMerchantStatus(option);
//                }
//            } catch (InputMismatchException e) {ErrorView.wrongInput();}
//        }
//    }
//    private void deleteMerchant() {
//        boolean active=true;
//        while (active){
//            try {
//                view.merchantHeader();
//                merchantService.printMerchant(activeUser);
//                view.productOption();
//                int option = c.inputInt();
//                switch (option){
//                    case 0 -> active=false;
//                    default -> merchantService.deleteMerchant(option);
//                }
//            } catch (InputMismatchException e) {ErrorView.wrongInput();}
//        }
//    }

}
