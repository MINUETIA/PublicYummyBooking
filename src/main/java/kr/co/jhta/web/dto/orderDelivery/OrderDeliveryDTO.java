package kr.co.jhta.web.dto.orderDelivery;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDeliveryDTO {
    private Long orderId;
    private LocalDate orderDeliveryStart;

}
