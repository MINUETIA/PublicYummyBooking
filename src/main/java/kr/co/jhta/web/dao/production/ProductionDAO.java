package kr.co.jhta.web.dao.production;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
@Mapper
public interface ProductionDAO {

    List<HashMap<String, Object>> getAllProduction();

    List<HashMap<String, Object>> getAllProductionByDate(String day);
}
