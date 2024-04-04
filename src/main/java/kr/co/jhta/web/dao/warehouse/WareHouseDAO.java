package kr.co.jhta.web.dao.warehouse;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
@Mapper
public interface WareHouseDAO {

    public List<HashMap<String, Object>> getAll();

    List<HashMap<String, Object>> getOne(int no);
}

