package kr.co.jhta.web.service.purchase;

import java.util.HashMap;
import java.util.List;

public interface PurchaseService {

    List<HashMap<String, Object>> getAll();

    List<HashMap<String, Object>> getAllByStatus(int status);

    List<HashMap<String, Object>>  getAllByName(String name);

    List<HashMap<String, Object>>  getAllByCode(String code);

    List<HashMap<String, Object>> getAllByStartDay(String start);

    List<HashMap<String, Object>> getAllByEndDay(String end);

    List<HashMap<String, Object>> getAllByStartDayEndDay(String start,String end);

    void modifyData(HashMap<String, Object> map);

    boolean getOne(String purchaseno);

    void insertOne(HashMap<String, Object> map);
}
