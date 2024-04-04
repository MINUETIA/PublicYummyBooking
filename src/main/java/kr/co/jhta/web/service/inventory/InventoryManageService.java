package kr.co.jhta.web.service.inventory;

import java.util.HashMap;
import java.util.List;

public interface InventoryManageService {


    List<HashMap<String, Object>> getAll(String day);
}
