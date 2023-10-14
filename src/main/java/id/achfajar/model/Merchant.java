package id.achfajar.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class Merchant {
    private UUID id;
    private String merchant_name;
    private String merchant_location;
    private boolean open;

    public String getStatus() {
        return open ? "Buka" : "Tutup";
    }
}