package id.achfajar.challenge7.dto.request;

import id.achfajar.challenge7.model.ERole;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
public class UserRegistrationRequest {

//    @Email(
//            regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$",
//            message = "Email tidak valid")
    private String email;

//    @NotBlank(message = "Username tidak boleh kosong")
//    @NotNull(message = "Username Null")
    private String username;

//    @NotBlank(message = "Password tidak boleh kosong")
//    @NotNull(message = "Password Null")
//    @Pattern(
//            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\\\S+$).{8,50}$",
//            message = "Password minimal 8 karakter dan maksimal 50 karakter, " +
//                    "harus mengandung setidaknya satu huruf kecil, satu huruf besar, " +
//                    "dan satu karakter spesial.")
    private String firstName;
    private String lastName;
    private String password;

    private Set<ERole> roles;

    public UserRegistrationRequest() {
        this.roles = new HashSet<>();
    }
}
