package id.achfajar.challenge8.controller;

import id.achfajar.challenge8.dto.request.ResetPasswordDTO;
import id.achfajar.challenge8.dto.request.UserLoginRequest;
import id.achfajar.challenge8.dto.request.UserRegistrationRequest;
import id.achfajar.challenge8.dto.response.JwtResponse;
import id.achfajar.challenge8.dto.response.ResponseHandler;
import id.achfajar.challenge8.security.jwt.JwtUtils;
import id.achfajar.challenge8.security.service.UserDetailsImpl;
import id.achfajar.challenge8.service.UserService;
import id.achfajar.challenge8.service.OTPService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final UserService userService;

    private final OTPService OTPService;
    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils,
                          UserService userService, OTPService OTPService) {
        this.authenticationManager=authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
        this.OTPService = OTPService;
    }

    @PostMapping("/signin")
    public ResponseEntity<Object> authenticate(@RequestBody UserLoginRequest userLoginRequest){
        Authentication authentication = authenticationManager
                .authenticate( new UsernamePasswordAuthenticationToken(
                        userLoginRequest.getUsername(), userLoginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        JwtResponse jwtResponse = new JwtResponse(jwt, userDetails.getUsername(), roles);
        return ResponseHandler.generateResponseSuccess(jwtResponse);
    }
    @PostMapping("/register")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest) {
        return ResponseHandler.generateResponseSuccess(userService.createUser(userRegistrationRequest));
    }

    @RequestMapping(path = "/forgot-password", method = RequestMethod.POST)
    public ResponseEntity<?> forgotPassword(@RequestParam String email){
        String response = OTPService.forgotPassword(email);
        return ResponseHandler.generateResponse(response, HttpStatus.ACCEPTED, null);
    }

    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam Integer token, @RequestBody ResetPasswordDTO newPassword){
        String response = OTPService.resetPassword(token, newPassword);
        return ResponseHandler.generateResponse(response, HttpStatus.ACCEPTED, null);
    }
}
