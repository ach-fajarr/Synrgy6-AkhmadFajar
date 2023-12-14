package id.achfajar.challenge7.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserLoginRequest {
    private String username;
    private String password;
}
