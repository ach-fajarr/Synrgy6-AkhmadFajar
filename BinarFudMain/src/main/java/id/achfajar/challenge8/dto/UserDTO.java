package id.achfajar.challenge8.dto;

import id.achfajar.challenge8.model.ERole;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserDTO {

    private String email;
    private String firstName;
    private String lastName;
    private String username;
    private Set<ERole> roles = new HashSet<>();
}
