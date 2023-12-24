package id.achfajar.challenge8.service;

import id.achfajar.challenge8.dto.request.ResetPasswordDTO;

public interface OTPService {

    public String forgotPassword(String email);

    public String resetPassword(Integer token, ResetPasswordDTO password);

}
