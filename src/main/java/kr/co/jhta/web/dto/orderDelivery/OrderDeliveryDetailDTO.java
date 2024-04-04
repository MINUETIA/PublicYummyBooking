package kr.co.jhta.web.dto.orderDelivery;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDeliveryDetailDTO {

    private Long orderDeliveryDetailId;
    private int orderDeliveryStatus;
    private Long oderId;
    private Long accprodetailId;

}
