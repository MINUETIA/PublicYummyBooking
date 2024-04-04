package kr.co.jhta.web.service.product;

import kr.co.jhta.web.dto.product.ProductDTO;
import kr.co.jhta.web.dto.sale.SaleHistoryDTO;

import java.util.List;

public interface ProductService {

    List<ProductDTO> select10NewMealKits();
    List<ProductDTO> selectKeyWord(String keyWord);
    List<ProductDTO> selectMealBasket(List<Integer> mealId);
    List<SaleHistoryDTO> selectSaleHistory(Long ubId);
}
