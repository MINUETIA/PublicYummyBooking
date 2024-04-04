package kr.co.jhta.web.control.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController2 {

    @GetMapping("/")
    public String adminHome(){
        return "views/admin/adminHome";
    }

}
