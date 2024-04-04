package kr.co.jhta.web.service.meal;

import kr.co.jhta.web.dto.product.ProductDTO;

import java.util.List;

public interface MealService {

    List<ProductDTO> select10NewMealKits();
}
