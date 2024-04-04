package kr.co.jhta.web.service.meal;

import kr.co.jhta.web.dao.meal.MealDetailDAO;
import kr.co.jhta.web.dto.product.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MealDetailsServiceImple implements MealDetailsService{
    @Autowired
    MealDetailDAO dao;

    @Override
    public ProductDTO selectMealOne(Long productId) {
        return dao.getMealDTO(productId);
    }
}
