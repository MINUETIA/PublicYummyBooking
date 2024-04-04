package kr.co.jhta.web.control.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {


    /*@GetMapping(value = {"/","/home"})
    public String home(){
        return "/index";
    }*/
  
    @GetMapping("/cate")
    public String cate(){
        return "/categories";
    }


    @GetMapping("blog_details")
    public String blog_details(){
        return "/blog-details";
    }

    @GetMapping("blog")
    public String blog(){
        return "/blog";
    }

    @GetMapping("anime_watching")
    public String anime_watching(){
        return "/anime-watching";
    }

    @GetMapping("anime_details")
    public String anime_details(){
        return "/anime-details";
    }



}
