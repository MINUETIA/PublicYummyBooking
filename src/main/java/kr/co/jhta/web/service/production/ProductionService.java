package kr.co.jhta.web.service.production;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public interface ProductionService {

    List<HashMap<String, Object>> getAll();
}
