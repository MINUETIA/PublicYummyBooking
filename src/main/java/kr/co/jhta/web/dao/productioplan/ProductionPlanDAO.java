package kr.co.jhta.web.dao.productioplan;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
@Mapper
public interface ProductionPlanDAO {

    List<HashMap<String, Object>> getAll();

    boolean getOne(int no);

    void modifyData(HashMap<String, Object> map);

    void insertOne(HashMap<String, Object> map);

    List<HashMap<String, Object>> getByFactory(int factory);

    List<HashMap<String, Object>> getByProduction(String production);

    List<HashMap<String, Object>> getByProductionId(String productId);

    List<HashMap<String, Object>> getByStartDate(String start);

    List<HashMap<String, Object>> getByEndDate(String end);

    List<HashMap<String, Object>> getByStartDateEndDate(String start, String end);

    List<HashMap<String, Object>> getByCode(int code);
}
