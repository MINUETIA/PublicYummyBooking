package kr.co.jhta.web.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ReservationDTO {
    private Long reservationId;
    private LocalDateTime reservationDate;
    private int reservationNum;
    private int reservationStatus;
    private String reservationRequest;
    private LocalDate reservationCDate;
    private LocalDate reservationMDate;
    private Long ubId;
    private Long restaurantId;

}
