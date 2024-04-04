package kr.co.jhta.web.dto.mealbasket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MealBasket {
    private Long mealBasketId;
    private Long ubId;
    private Long productId;

}
