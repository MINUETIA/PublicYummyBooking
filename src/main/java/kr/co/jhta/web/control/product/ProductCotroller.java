package kr.co.jhta.web.control.product;

import kr.co.jhta.web.dao.product.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;

@Controller
public class ProductCotroller {

    @Autowired
    ProductDAO productDAO;

    @RequestMapping("/products")
    public String productlist(Model model){

        List< HashMap<String, Object>> stringObjectHashMap = productDAO.selectAllproducts();
        model.addAttribute("list",stringObjectHashMap );

        return "views/product/products";
    }
}
