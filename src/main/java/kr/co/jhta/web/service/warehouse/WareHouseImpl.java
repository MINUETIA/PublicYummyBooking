package kr.co.jhta.web.service.warehouse;

import kr.co.jhta.web.dao.warehouse.WareHouseDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class WareHouseImpl implements WareHouseService {
    @Autowired
    private WareHouseDAO wareHouseDAO;

    @Override
    public List<HashMap<String, Object>> getAll() {
        return wareHouseDAO.getAll();

    }

    @Override
    public List<HashMap<String, Object>> getOne(int no) {
        return wareHouseDAO.getOne(no);
    }
}
