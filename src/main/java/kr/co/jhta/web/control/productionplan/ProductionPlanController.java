package kr.co.jhta.web.control.productionplan;

import kr.co.jhta.web.service.production.ProductionService;
import kr.co.jhta.web.service.productionplan.ProductionPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/admin/productionplan")
public class ProductionPlanController {

    @Autowired
    private ProductionPlanService productionPlanService;
    @Autowired
    private ProductionService productionService;

    @GetMapping("/plan")
    public String plan(Model model,@RequestParam(value = "factory", defaultValue = "0")int factory,
                        @RequestParam(value = "name", defaultValue = "PP0000")String production,
                        @RequestParam(value = "productId", defaultValue = "noid")String productId,
                        @RequestParam(value = "start", defaultValue = "2000-01-01")String start,
                        @RequestParam(value = "end", defaultValue = "9999-12-31")String end){

        List<HashMap<String, Object>> list = productionPlanService.getAllByCondition(factory, production,  productId, start, end);
        list.stream().forEach(x -> {
            if(x.get("createDate") == null) {
                x.put("createDate","");
            }
        });

        model.addAttribute("list", list);
        model.addAttribute("productionList", productionService.getAll());
        return "views/productionplan/plan";
    }

    @GetMapping("/modify")
    @ResponseBody
    public String modify(
            @RequestParam(value = "productionId", defaultValue = "productionId") int productionId,
            @RequestParam(value = "production", defaultValue = "noname")String production,
            @RequestParam(value = "no", defaultValue = "0")int no,
            @RequestParam(value = "plandate", defaultValue = "9999-12-31")String plandate,
            @RequestParam(value = "cnt", defaultValue = "0")int cnt,
            @RequestParam(value = "factory", defaultValue = "1")int factory){

        // 이름으로 벤더번호 가져오기
        HashMap<String, Object> map = new HashMap<>();
        map.put("productionId", productionId);
        map.put("production", production);
        map.put("no", no);
        map.put("plandate", plandate);
        map.put("cnt", cnt);
        map.put("factory", factory);

        productionPlanService.apply(map);

        return "OK";

    }

}
