package kr.co.jhta.web.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {

    private Long productId;
    private String mealName;
    private int mealPrice;
    private LocalDateTime mealRegist;
    private int mealCnt;
    private Long subId;
    private LocalDateTime mealManufacture_day;
    private String mealDescription;
    private int mealWeight;
    private String mealPhoto1;
    private String mealPhoto2;
    private String mealPhoto3;

}
