package kr.co.jhta.web.service.warehouse;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public interface WareHouseService {

    List<HashMap<String, Object>> getAll();

    List<HashMap<String, Object>> getOne(int no);
}
