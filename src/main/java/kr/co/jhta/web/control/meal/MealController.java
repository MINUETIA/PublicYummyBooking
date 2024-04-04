package kr.co.jhta.web.control.meal;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.jhta.web.dto.product.ProductDTO;
import kr.co.jhta.web.dto.sale.SaleHistoryDTO;
import kr.co.jhta.web.dto.userBusiness.UbCommonDTO;
import kr.co.jhta.web.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Controller
@RequestMapping("meal")
public class MealController {

    @Autowired
    ProductService productService;

    @GetMapping("/main")
    public String MealHome(Model model, HttpServletRequest req){

        List<ProductDTO> list = productService.select10NewMealKits();
        model.addAttribute("list",list);

        return "views/meal/mealMain";
    }

    @GetMapping("/keyword")
    public  String mealSearch(@RequestParam String keyWord, Model model){

        model.addAttribute("list", productService.selectKeyWord(keyWord));
        model.addAttribute("keyWord",keyWord);

        return "views/product/products";
    }

    @GetMapping("/mealbasket")
    public  String mealBasket(@RequestParam List<Integer> mealId, Model model){

        List<ProductDTO> productDTOS = productService.selectMealBasket(mealId);
        model.addAttribute("list", productDTOS );

        return "views/meal/mealBasket";
    }

    @GetMapping("/mealPayment")
    public String mealPayment(@RequestParam String mealName,
                              @RequestParam String sumPrice, Model model){
        model.addAttribute("mealName", mealName);
        model.addAttribute("sumPrice", sumPrice);

        return "views/meal/mealPayment";
    }

    @GetMapping("/history")
    public String mealHistroy(HttpServletRequest req, Model model){

        Long ubId = ((UbCommonDTO) req.getSession().getAttribute("ubCommonDTOLogin")).getUbId();
        List<SaleHistoryDTO> saleHistoryDTOS = productService.selectSaleHistory(ubId);

        Map<LocalDateTime, List<SaleHistoryDTO>> collect = saleHistoryDTOS.stream()
                .collect(groupingBy(SaleHistoryDTO::getSaledate));

        model.addAttribute("list",collect );

        return "views/meal/saleHistory";
    }
}
