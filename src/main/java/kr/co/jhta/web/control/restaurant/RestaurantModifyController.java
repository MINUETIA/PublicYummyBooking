package kr.co.jhta.web.control.restaurant;

import kr.co.jhta.web.dto.RestrantByTheme;
import kr.co.jhta.web.dto.restaurant.RestaurantDTO;
import kr.co.jhta.web.dto.restaurant.RestaurantMenuDTO;
import kr.co.jhta.web.dto.restaurant.RestaurantOpeningDTO;
import kr.co.jhta.web.dto.review.EvaluationDTO;
import kr.co.jhta.web.service.restaurant.RestaurantDetailsService;
import kr.co.jhta.web.service.restaurant.RestaurantModifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalTime;
import java.util.List;

@Controller
public class RestaurantModifyController {
    @Autowired
    RestaurantDetailsService service;
    @Autowired
    RestaurantModifyService modifyService;
    @GetMapping("/restaurantModify")
    public String modifyForm(@RequestParam int restaurantId, Model model){
        RestaurantDTO dto = service.RestaurantOne(restaurantId);
        List<RestaurantMenuDTO> list = service.getMenu(restaurantId);
        EvaluationDTO star = service.getStarRating(restaurantId);
        List<RestaurantOpeningDTO> operatingTime = service.getOperatingTime(restaurantId);
        List<RestrantByTheme> theme = service.restaurantTheme(restaurantId);

        model.addAttribute("list", list);
        model.addAttribute("star", star);
        model.addAttribute("operatingTime", operatingTime);
        model.addAttribute("theme", theme);
        model.addAttribute("dto", dto);
        return "views/restaurant/restaurantModify";
    }

    @PostMapping("/addMenu")
    public String addMenu(@RequestParam String name, @RequestParam int price, @RequestParam("restaurantId") int restaurantId,  RedirectAttributes ra){
        System.out.println("restaurantId : "+restaurantId);
        RestaurantMenuDTO dto = new RestaurantMenuDTO();
        dto.setMenuName(name);
        dto.setMenuPrice(price);
        Long id = (long) restaurantId;
        dto.setRestaurantId(id);

        modifyService.insertMenu(dto);
        ra.addAttribute("restaurantId", restaurantId);
        return "redirect:/restaurantModify";
    }
    @PostMapping("/removeMenu")
    public String removeMenu(RedirectAttributes ra, @RequestParam Long restaurantMenuId,@RequestParam("restaurantId") int restaurantId){
        System.out.println("RestaurantMenuId : "+restaurantMenuId);
        modifyService.deleteMenu(restaurantMenuId);

        ra.addAttribute("restaurantId", restaurantId);
        return "redirect:/restaurantModify";
    }
    @PostMapping("/updateAdd")
    public String updateAdd(RedirectAttributes ra,@RequestParam("restaurantId") int restaurantId, @RequestParam String address){
        RestaurantDTO dto = new RestaurantDTO();
        dto.setRestaurantAddress(address);
        Long id = (long) restaurantId;
        dto.setRestaurantId(id);
        modifyService.changeAdd(dto);

        ra.addAttribute("restaurantId", restaurantId);
        return "redirect:/restaurantModify";
    }
    @PostMapping("/changeTime")
    public String changeTime(RedirectAttributes ra, @RequestParam("restaurantId") int restaurantId,@RequestParam String changeOpen, @RequestParam String changeClose,@RequestParam int openingInfoWeek){
        RestaurantOpeningDTO dto = new RestaurantOpeningDTO();
        Long id = (long) restaurantId;
        dto.setRestaurantId(id);

        LocalTime open = LocalTime.parse(changeOpen);
        LocalTime close = LocalTime.parse(changeClose);

        dto.setOpeningInfoStartTime(open);
        dto.setOpeningInfoEndTime(close);
        dto.setOpeningInfoWeek(openingInfoWeek);

        modifyService.changeTime(dto);


        ra.addAttribute("restaurantId", restaurantId);
        return "redirect:/restaurantModify";
    }
}
