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
public class MaterialOrdersDTO {
    private Long order_id;
    private LocalDateTime OrderDate;
}
