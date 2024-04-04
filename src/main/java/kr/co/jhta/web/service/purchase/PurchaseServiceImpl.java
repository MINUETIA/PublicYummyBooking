package kr.co.jhta.web.service.purchase;

import kr.co.jhta.web.dao.purchase.PurchaseDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class PurchaseServiceImpl implements  PurchaseService {
    @Autowired
    private PurchaseDAO purchaseDAO;

    @Override
    public List<HashMap<String, Object>> getAll() {

        List<HashMap<String, Object>> list = purchaseDAO.getAllPurchase();

        int no = 0 ;
        for(HashMap<String, Object> x : list){
            int sprice = (int) x.get("sprice");

            int tax = (int) (sprice*0.1);
            int total = (int) (sprice*1.1);
            HashMap<String, Integer> map1 = new HashMap<>();
            x.put("num", ++no);
            x.put("total", total);
            x.put("tax",tax);
            x.put("modifier","system");
            x.put("manager","담당자");


            Object modifydate = x.get("modifydate");
            if(modifydate == null){
                x.put("modifydate", "");
            }

        }
        return list;

    }

    @Override
    public List<HashMap<String, Object>> getAllByStatus(int status) {
        List<HashMap<String, Object>> list = purchaseDAO.getAllPurchaseByStatus(status);

        int no = 0 ;
        for(HashMap<String, Object> x : list){
            int sprice = (int) x.get("sprice");

            int tax = (int) (sprice*0.1);
            int total = (int) (sprice*1.1);
            HashMap<String, Integer> map1 = new HashMap<>();
            x.put("num", ++no);
            x.put("total", total);
            x.put("tax",tax);
            x.put("modifier","system");
            x.put("manager","담당자");


            Object modifydate = x.get("modifydate");
            if(modifydate == null){
                x.put("modifydate", "");
            }

        }
        return list;

    }

    @Override
    public List<HashMap<String, Object>> getAllByName(String name) {
        List<HashMap<String, Object>> list = purchaseDAO.getAllPurchaseByName(name);

        int no = 0 ;
        for(HashMap<String, Object> x : list){
            int sprice = (int) x.get("sprice");

            int tax = (int) (sprice*0.1);
            int total = (int) (sprice*1.1);
            HashMap<String, Integer> map1 = new HashMap<>();
            x.put("num", ++no);
            x.put("total", total);
            x.put("tax",tax);
            x.put("modifier","system");
            x.put("manager","담당자");


            Object modifydate = x.get("modifydate");
            if(modifydate == null){
                x.put("modifydate", "");
            }

        }
        return list;
    }

    @Override
    public List<HashMap<String, Object>> getAllByCode(String code) {
        List<HashMap<String, Object>> list = purchaseDAO.getAllPurchaseByCode(code);

        int no = 0 ;
        for(HashMap<String, Object> x : list){
            int sprice = (int) x.get("sprice");

            int tax = (int) (sprice*0.1);
            int total = (int) (sprice*1.1);
            HashMap<String, Integer> map1 = new HashMap<>();
            x.put("num", ++no);
            x.put("total", total);
            x.put("tax",tax);
            x.put("modifier","system");
            x.put("manager","담당자");


            Object modifydate = x.get("modifydate");
            if(modifydate == null){
                x.put("modifydate", "");
            }

        }
        return list;
    }

    @Override
    public List<HashMap<String, Object>> getAllByStartDay(String start) {
        List<HashMap<String, Object>> list = purchaseDAO.getAllPurchaseByStartDay(start);

        int no = 0 ;
        for(HashMap<String, Object> x : list){
            int sprice = (int) x.get("sprice");

            int tax = (int) (sprice*0.1);
            int total = (int) (sprice*1.1);
            HashMap<String, Integer> map1 = new HashMap<>();
            x.put("num", ++no);
            x.put("total", total);
            x.put("tax",tax);
            x.put("modifier","system");
            x.put("manager","담당자");


            Object modifydate = x.get("modifydate");
            if(modifydate == null){
                x.put("modifydate", "");
            }

        }
        return list;
    }

    @Override
    public List<HashMap<String, Object>> getAllByEndDay(String end) {
        List<HashMap<String, Object>> list = purchaseDAO.getAllPurchaseByEndDay(end);

        int no = 0 ;
        for(HashMap<String, Object> x : list){
            int sprice = (int) x.get("sprice");

            int tax = (int) (sprice*0.1);
            int total = (int) (sprice*1.1);
            HashMap<String, Integer> map1 = new HashMap<>();
            x.put("num", ++no);
            x.put("total", total);
            x.put("tax",tax);
            x.put("modifier","system");
            x.put("manager","담당자");


            Object modifydate = x.get("modifydate");
            if(modifydate == null){
                x.put("modifydate", "");
            }

        }
        return list;
    }

    @Override
    public List<HashMap<String, Object>> getAllByStartDayEndDay(String start, String end) {
        List<HashMap<String, Object>> list = purchaseDAO.getAllPurchaseByStartDayEndDay(start, end);

        int no = 0 ;
        for(HashMap<String, Object> x : list){
            int sprice = (int) x.get("sprice");

            int tax = (int) (sprice*0.1);
            int total = (int) (sprice*1.1);
            HashMap<String, Integer> map1 = new HashMap<>();
            x.put("num", ++no);
            x.put("total", total);
            x.put("tax",tax);
            x.put("modifier","system");
            x.put("manager","담당자");


            Object modifydate = x.get("modifydate");
            if(modifydate == null){
                x.put("modifydate", "");
            }

        }
        return list;
    }

    @Override
    public void modifyData(HashMap<String, Object> map) {
        int cnt = (Integer) map.get("cnt");
        int price = (Integer) map.get("price");
        map.put("sprice", cnt*price);

        purchaseDAO.update(map);
    }

    @Override
    public boolean getOne(String purchaseno) {
        return purchaseDAO.getOne(purchaseno);

    }

    @Override
    public void insertOne(HashMap<String, Object> map) {
        int vendorno = purchaseDAO.getVendorNo((String)map.get("name"));
        int cnt = (Integer) map.get("cnt");
        int price = (Integer) map.get("price");
        map.put("sprice", cnt*price);
        map.put("vendorno", vendorno);
        purchaseDAO.insertOne(map);
    }
}
