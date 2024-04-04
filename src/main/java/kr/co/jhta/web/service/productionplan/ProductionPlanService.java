package kr.co.jhta.web.service.productionplan;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public interface ProductionPlanService {

    List<HashMap<String,Object>> getAll();

    void apply(HashMap<String, Object> map);

    List<HashMap<String, Object>> getAllByFactory(int factory);



    List<HashMap<String, Object>> getAllByCondition(int factory, String production,  String productId, String start, String end);
}
