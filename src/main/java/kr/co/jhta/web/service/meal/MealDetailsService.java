package kr.co.jhta.web.service.meal;

import kr.co.jhta.web.dto.product.ProductDTO;

public interface MealDetailsService {
    public ProductDTO selectMealOne(Long productId);
}
