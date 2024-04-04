package kr.co.jhta.web.service.production;

import kr.co.jhta.web.dao.production.ProductionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class ProductionServiceImpl  implements  ProductionService{
    @Autowired
    private ProductionDAO productionDAO;
    @Override
    public List<HashMap<String, Object>> getAll() {
        return productionDAO.getAllProduction();
    }
}
