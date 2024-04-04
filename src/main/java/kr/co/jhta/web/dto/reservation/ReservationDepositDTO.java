package kr.co.jhta.web.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ReservationDepositDTO {
    private Long depositId;
    private Long depositNumber;
    private int depositPaymentMethod;
    private int depositAmount;
    private String depositApprovalNumber;
    private LocalDate depositPaymentTime;
    private LocalDate depositCancellationTime;
    private Long reservationId;

}
