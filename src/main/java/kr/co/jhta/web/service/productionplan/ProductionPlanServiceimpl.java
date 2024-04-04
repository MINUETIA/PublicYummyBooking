package kr.co.jhta.web.service.productionplan;

import kr.co.jhta.web.dao.productioplan.ProductionPlanDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class ProductionPlanServiceimpl implements ProductionPlanService {
    @Autowired
    private ProductionPlanDAO productionPlanDao;
    @Override
    public List<HashMap<String, Object>> getAll() {

        return productionPlanDao.getAll();
    }

    @Override
    public void apply(HashMap<String, Object> map) {


        boolean result = productionPlanDao.getOne((int)map.get("no"));
        System.out.println("result : " + result);
        // 공장별 생산가능 수는 추후에 구현하기로 하자
        int factory  = (int) map.get("factory");
        map.put("maxproduction", factory*1000);


        System.out.println("result : " + result);
        if(result){
            System.out.println(map);
            productionPlanDao.modifyData(map);
        }else {
            productionPlanDao.insertOne(map);
        }


    }

    @Override
    public List<HashMap<String, Object>> getAllByFactory(int factory) {
        return productionPlanDao.getByFactory(factory);
    }

    @Override
    public List<HashMap<String, Object>> getAllByCondition(int factory, String production, String productId, String start, String end) {
        List<HashMap<String, Object>> list = null;

        System.out.println("factory : "+ factory + " production : " + production + " productId : " + productId  + " start  : " + start + " end : " + end );
        if(factory == 1 || factory == 2){
           list =  productionPlanDao.getByFactory(factory);
           return list;
        }
        if(!production.equals("PP0000")){
            list =  productionPlanDao.getByProduction(production);
            return list;
        }

        if(!productId.equals("noid")){
            list =  productionPlanDao.getByProductionId(productId);
            return list;
        }
        if(!start.equals("2000-01-01") && end.equals("9999-12-31")){
            list =  productionPlanDao.getByStartDate(start);
            return list;
        }
        if(start.equals("2000-01-01") && !end.equals("9999-12-31")){
            list =  productionPlanDao.getByEndDate(end);
            return list;
        }
        if(!start.equals("2000-01-01") && !end.equals("9999-12-31")){
            list =  productionPlanDao.getByStartDateEndDate(start, end);
            return list;
        }

        list = productionPlanDao.getAll();
        System.out.println("list : " + list);



        return list;
    }
}
