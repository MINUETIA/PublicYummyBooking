package kr.co.jhta.web.dao.meal;

import kr.co.jhta.web.dto.product.ProductDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MealDetailDAO {
    public ProductDTO getMealDTO(Long productId);
}
