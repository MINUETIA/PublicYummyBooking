package kr.co.jhta.web.dao.purchase;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
@Mapper
public interface PurchaseDAO {

    public List<HashMap<String, Object>> getAllPurchase();

    List<HashMap<String, Object>> getAllPurchaseByStatus(int status);

    List<HashMap<String, Object>> getAllPurchaseByName(String name);

    List<HashMap<String, Object>> getAllPurchaseByCode(String code);

    List<HashMap<String, Object>> getAllPurchaseByStartDay(String start);

    List<HashMap<String, Object>> getAllPurchaseByEndDay(String end);

    List<HashMap<String, Object>> getAllPurchaseByStartDayEndDay(String start, String end);

    void update(HashMap<String, Object> map);

    boolean getOne(String purchaseno);

    void insertOne(HashMap<String, Object> map);

    int getVendorNo(String name);
}
