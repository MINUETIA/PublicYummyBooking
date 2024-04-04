package kr.co.jhta.web.dao.product;

import kr.co.jhta.web.dto.product.ProductDTO;
import kr.co.jhta.web.dto.sale.SaleHistoryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
@Mapper
public interface ProductDAO {

    public List<HashMap<String, Object>> selectAllproducts();

    List<ProductDTO> select10NewMealKits();

    List<ProductDTO> selectKeyWord(String keyWord);

    List<ProductDTO> selectMealBasket(List<Integer> mealId);

    List<SaleHistoryDTO> selectSaleHistory(Long ubId);
}
