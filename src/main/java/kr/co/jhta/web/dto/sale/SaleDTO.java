package kr.co.jhta.web.dto.sale;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleDTO {
    private Long saleId;
    private int orderNum;
    private LocalDateTime saledate;
}
