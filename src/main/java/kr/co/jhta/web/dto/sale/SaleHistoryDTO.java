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
public class SaleHistoryDTO {
    private Long saleId;
    private LocalDateTime saledate;
    private int saleCnt;
    private String mealName;
    private int mealPrice;
    private String mealPhoto1;
    private int saleCode;

}
