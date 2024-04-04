package kr.co.jhta.web.dto.mealkitDelivery;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealDeliveryDetailDTO {

    private Long mealDeliveryDetailId;
    private int saleDeliveryStatus;
    private Long saleId;

}
