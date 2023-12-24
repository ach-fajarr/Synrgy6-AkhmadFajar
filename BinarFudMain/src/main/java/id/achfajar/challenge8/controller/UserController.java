package id.achfajar.challenge8.controller;

import id.achfajar.challenge8.dto.UserDTO;
import id.achfajar.challenge8.dto.request.UserRegistrationRequest;
import id.achfajar.challenge8.dto.response.ResponseHandler;
import id.achfajar.challenge8.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<?> userInfo(Principal principal){
        UserDTO userDTO = userService.userInfo(principal.getName());
        return ResponseHandler.generateResponseSuccess(userDTO);
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserRegistrationRequest userRegistrationRequest, Principal principal) {
        UserDTO userDTO = userService.updateUser(principal.getName(), userRegistrationRequest);
        return ResponseHandler.generateResponseSuccess(userDTO);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(Principal principal) {
        userService.deleteUser(principal.getName());
        return ResponseHandler.generateResponse("sucess", HttpStatus.ACCEPTED, "Data telah dihapus");
    }

}

