package kr.co.jhta.web.control.inventory;

import kr.co.jhta.web.service.inventory.InventoryManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/admin/inventory")
public class InventoryManageController {

    @Autowired
    private InventoryManageService inventoryManageService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "day", defaultValue = "0") String day){

        List<HashMap<String,Object>> list =  inventoryManageService.getAll(day);
        model.addAttribute("list", list);

        return "views/inventory/list";
    }
}
