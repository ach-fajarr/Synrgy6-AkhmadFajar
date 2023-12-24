package id.achfajar.challenge8.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RevenueRequestDTO {
    private String requestBy;
    private Integer month;
    private Integer year;
    private LocalDate week;
    private LocalDate startDate;
    private LocalDate endDate;
}
