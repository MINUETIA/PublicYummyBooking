package kr.co.jhta.web.dto.materialOrders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderDetailDTO {
    private Long order_detail_id;
    private Long order_id;
    private Long vendor_material_detail_id;
    private int order_cnt;
    private LocalDateTime order_receive_goods;
}
