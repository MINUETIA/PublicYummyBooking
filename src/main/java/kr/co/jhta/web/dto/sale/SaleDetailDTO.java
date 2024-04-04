package kr.co.jhta.web.dto.sale;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SaleDetailDTO {
    private Long saleDetailId;
    private int saleCnt;
    private Long productId;
    private Long saleId;
}
