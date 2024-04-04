package kr.co.jhta.web.control.warehouse;

import kr.co.jhta.web.service.warehouse.WareHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

@Controller

@RequestMapping("/admin/warehouse")
public class  WareHouseController {
    @Autowired
    private WareHouseService wareHouseService;
    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "no",defaultValue = "0")int no){
        List<HashMap<String, Object>> list = wareHouseService.getAll();
        model.addAttribute("list",list);
            if ( no ==0 ){
                model.addAttribute("plist",list);
            }else {
                model.addAttribute("plist", wareHouseService.getOne(no));
            }

        return "views/warehouse/list";
    }




}
