package kr.co.jhta.web.service.product;

import kr.co.jhta.web.dao.product.ProductDAO;
import kr.co.jhta.web.dto.product.ProductDTO;
import kr.co.jhta.web.dto.sale.SaleHistoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductDAO productDAO;

    @Override
    public List<ProductDTO> select10NewMealKits() {
        return productDAO.select10NewMealKits();
    }

    @Override
    public List<ProductDTO> selectKeyWord(String keyWord) {
        return productDAO.selectKeyWord(keyWord);
    }

    @Override
    public List<ProductDTO> selectMealBasket(List<Integer> mealId) {
        return productDAO.selectMealBasket(mealId);
    }

    @Override
    public List<SaleHistoryDTO> selectSaleHistory(Long ubId) {
        return productDAO.selectSaleHistory(ubId);
    }
}
