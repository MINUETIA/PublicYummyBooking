package kr.co.jhta.web.control.meal;

import kr.co.jhta.web.dto.product.ProductDTO;
import kr.co.jhta.web.service.meal.MealDetailsServiceImple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/meal")
public class MealDetails {

    @Autowired
    MealDetailsServiceImple service;

    @RequestMapping("/mealDetails")
    public String mealDetails(@RequestParam Long productId, Model model){
        ProductDTO meal = service.selectMealOne(productId);
        model.addAttribute("meal", meal);

        return "views/meal/mealDetails";
    }
}
