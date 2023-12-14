package id.achfajar.challenge7.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResetPasswordDTO{
    private String email;
    private String newPassword;
}
