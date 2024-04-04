package kr.co.jhta.web.dto.mealkitDelivery;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealDeliveryDTO {

    private Long saleId;
    private int invoiceNum;
    private LocalDate mealDeliveryStart;

}
