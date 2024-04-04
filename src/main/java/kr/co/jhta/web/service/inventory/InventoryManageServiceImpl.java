package kr.co.jhta.web.service.inventory;

import kr.co.jhta.web.dao.invoice.InvoiceDAO;
import kr.co.jhta.web.dao.material.MaterialsDAO;
import kr.co.jhta.web.dao.production.ProductionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;

@Service
public class InventoryManageServiceImpl implements  InventoryManageService{
    @Autowired
    private ProductionDAO productionDAO;

    @Autowired
    private MaterialsDAO materialsDAO;

    @Autowired
    private InvoiceDAO invoiceDAO;


    @Override
    public List<HashMap<String, Object>> getAll(String day) {
        LocalDate start1 = null;
        LocalDate end1 = null;
        System.out.println("day : " + day);
        if(day.equals("0")) {
            YearMonth today1 = YearMonth.now();
            start1 = today1.atDay(1);
            end1 = today1.atEndOfMonth();
        }else{
            String[] days = day.split("-");
            int year = Integer.parseInt(days[0]);
            int month = Integer.parseInt(days[1]);

            YearMonth today1 = YearMonth.of(year, month);

            start1 = today1.atDay(1);
            end1 = today1.atEndOfMonth();
        }
        System.out.println(start1.toString());
        System.out.println(end1.toString());

//        productionDAO.getAllProductionByDate(start1.toString());
//
        HashMap<String, String> map = new HashMap<>();
        map.put("start", start1.toString());
        map.put("end", end1.toString());
        List<HashMap<String, Object>> list = materialsDAO.getAllByDate(map);

//        List<HashMap<String, Object>> list =
//                invoiceDAO.getAllByDate(start1.toString(), end1.toString());

        return list;
    }
}
