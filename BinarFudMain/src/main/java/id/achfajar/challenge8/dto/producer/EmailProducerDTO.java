package id.achfajar.challenge8.dto.producer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class EmailProducerDTO {
    private String to;
    private String subject;
    private String message;
}
