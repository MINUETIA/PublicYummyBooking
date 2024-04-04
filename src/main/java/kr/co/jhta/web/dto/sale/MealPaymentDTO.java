package kr.co.jhta.web.dto.sale;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MealPaymentDTO {
    private Long saleId;
    private int isPaid;
    private int paymentMethod;
    private LocalDateTime paymentTime;
    private LocalDateTime cancellationTime;
}
