package id.achfajar.challenge8.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserLoginRequest {
    private String username;
    private String password;
}
