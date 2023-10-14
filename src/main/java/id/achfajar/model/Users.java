package id.achfajar.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class Users {
    private UUID id;
    private String username;
    private String email_address;
    private String password;
}
