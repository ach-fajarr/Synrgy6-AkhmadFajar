package id.achfajar.challenge4.controller;

import id.achfajar.challenge4.model.Merchant;
import id.achfajar.challenge4.model.Users;
import id.achfajar.challenge4.service.MerchantService;
import id.achfajar.challenge4.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {
    @Autowired
    UsersService usersService;
    @Autowired
    MerchantService merchantService;

    @GetMapping("/user")
    public ResponseEntity<List<Users>> userList(){
        List<Users> users = usersService.getUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }
    @GetMapping("/merchant")
    public ResponseEntity<List<Merchant>> merchantList(){
        List<Merchant> merchants = merchantService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(merchants);
    }
}
