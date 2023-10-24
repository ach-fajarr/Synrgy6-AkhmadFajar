package id.achfajar.challenge4.controller;

import id.achfajar.challenge4.model.Users;
import id.achfajar.challenge4.service.MerchantService;
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
                    case 2 -> addProduct();
                    case 3 -> updateMerchant();
                    case 4 -> setOpen();
                    case 5 -> active=false;
                    default -> ErrorView.wrongInput();
                }
            } catch (InputMismatchException e) {ErrorView.wrongInput();}
        }

    }
    private void updateMerchant() {
        boolean active=true;
        while (active){
            try {
                view.merchantHeader();
                merchantService.printMerchant(activeUser);
                view.productOption();
                int option = c.inputInt();
                switch (option){
                    case 0 -> active=false;
                    default -> merchantService.updateMerchant(option);
                }
            } catch (InputMismatchException e) {ErrorView.wrongInput();}
        }
    }

    private void setOpen() {
        boolean active=true;
        while (active){
            try {
                view.merchantHeader();
                merchantService.printMerchant(activeUser);
                view.productOption();
                int option = c.inputInt();
                switch (option){
                    case 0 -> active=false;
                    default -> merchantService.updateMerchantStatus(option);
                }
            } catch (InputMismatchException e) {ErrorView.wrongInput();}
        }
    }

    private void addProduct() {
        boolean active=true;
        while (active){
            try {
                view.productHeader();
                merchantService.printMerchant(activeUser);
                view.productOption();
                int option = c.inputInt();
                switch (option){
                    case 0 -> active=false;
                    default -> merchantService.createProduct(option);
                }
            } catch (InputMismatchException e) {ErrorView.wrongInput();}
        }
    }

    public void clearMap() {
        merchantService.clearMap();
    }
    //===================================================================================


}
