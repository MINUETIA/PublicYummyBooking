package kr.co.jhta.web.control.management;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/management")
public class ManagementController {

    @GetMapping("/info")
    public String info(Model model){
//        List<String> list = List.of("불고기", "돈까스", "햄가득 부대찌개", "평양냉면");
        List<String> list = List.of("대한제분", "롯데", "삼양", "농심", "송학식품");
        List<Integer> data = List.of(1500,1200,500,300, 150);
        model.addAttribute("productList", list);
        model.addAttribute("data", data);
        return "views/manage/info.html";
    }

}
