package id.achfajar.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Note {
    private String name;
    private byte quantity;
    private String note;
}
