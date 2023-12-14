package id.achfajar.challenge7.service;

import id.achfajar.challenge7.dto.request.ResetPasswordDTO;

public interface OTPService {

    public String forgotPassword(String email);

    public String resetPassword(Integer token, ResetPasswordDTO password);

}
