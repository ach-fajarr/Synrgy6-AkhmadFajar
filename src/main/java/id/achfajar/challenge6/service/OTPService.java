package id.achfajar.challenge6.service;

import id.achfajar.challenge6.dto.request.ResetPasswordDTO;

public interface OTPService {

    public String forgotPassword(String email);

    public String resetPassword(Integer token, ResetPasswordDTO password);

}
