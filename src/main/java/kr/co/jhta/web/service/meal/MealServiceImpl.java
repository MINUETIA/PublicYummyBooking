package kr.co.jhta.web.service.meal;

import kr.co.jhta.web.dao.product.ProductDAO;
import kr.co.jhta.web.dto.product.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    ProductDAO productDAO;

    @Override
    public List<ProductDTO> select10NewMealKits() {
        return productDAO.select10NewMealKits();
    }
}
