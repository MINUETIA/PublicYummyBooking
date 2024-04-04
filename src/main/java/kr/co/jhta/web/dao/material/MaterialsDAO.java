package kr.co.jhta.web.dao.material;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface MaterialsDAO {

    List<HashMap<String, Object>>   getAllByDate(HashMap map);

}
