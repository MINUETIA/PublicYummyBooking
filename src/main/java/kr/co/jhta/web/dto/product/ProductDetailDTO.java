package kr.co.jhta.web.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductDetailDTO {
    
    private Long pDetailId;
    private LocalDateTime mealManufactureDay;
    private String mealDescription;
    private String mealPhoto1;
    private String mealPhoto2;
    private String mealPhoto3;
    private Long productId;
}
