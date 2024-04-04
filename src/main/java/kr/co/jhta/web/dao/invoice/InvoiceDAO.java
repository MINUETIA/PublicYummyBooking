package kr.co.jhta.web.dao.invoice;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface InvoiceDAO {
    List<HashMap<String,Object>> getAllByDate(String string, String string1);
}
