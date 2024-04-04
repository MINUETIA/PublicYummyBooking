package kr.co.jhta.web.control.main;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.jhta.web.dto.restaurant.RestaurantDTO;
import kr.co.jhta.web.dto.userBusiness.CustomOAuth2User;
import kr.co.jhta.web.dto.userBusiness.UbCommonDTO;
import kr.co.jhta.web.service.membership.LoginService;
import kr.co.jhta.web.service.restaurant.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    LoginService loginService;

    @GetMapping({"/","/index","/home"})
    public String main(Model model, HttpServletRequest req){
        List<RestaurantDTO> Nlist = restaurantService.select10NewRestaurants();
        model.addAttribute("Nlist",Nlist);

        List<RestaurantDTO> Hlist = restaurantService.select10hotRestaurants();
        model.addAttribute("Hlist",Hlist);

        List<RestaurantDTO> Rlist = restaurantService.recommandations();
        model.addAttribute("Rlist",Rlist);

        // 사용자 정보 확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 이름 가져오기
        String name = authentication.getName();
        String username = null;

        if(authentication.getPrincipal() instanceof CustomOAuth2User ) {
            CustomOAuth2User principal
                    = (CustomOAuth2User) authentication.getPrincipal();
            username = principal.getUsername();

            req.getSession().setAttribute("snsId", username);
        }
        // Role 확인하기
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        // 소셜 로그인 안할 때
        if ( name.equals("anonymousUser") && role.equals("ROLE_ANONYMOUS") ) {
            UbCommonDTO dtoLogin = (UbCommonDTO) req.getSession().getAttribute("ubCommonDTOLogin");
            if ( dtoLogin != null ) {
                UbCommonDTO dto = loginService.findByUserIdAndPw(dtoLogin.getUserId(), dtoLogin.getUserPw());
                name = dto.getName();
                role = dto.getSecurityRole();
                req.getSession().setAttribute("ubCommonDTOLogin", dto);
            }
            if ( role == null ) {
                return "views/userBusiness/signupDetail.html";
            }

        }
        // 소셜 로그인 할 때
        else {
            UbCommonDTO dto = loginService.findByUserId(username);

            role = dto.getSecurityRole();
            req.getSession().setAttribute("ubCommonDTOLogin", dto);
            if (role == null ) {
                return "views/userBusiness/signupSocial.html";
            }
        }

        model.addAttribute("name", name);
        model.addAttribute("role", role);

        // 메인 테마에 회원이름 넣으려고 추가
        // 세션에서 ubCommonDTOLogin 가져오기
        UbCommonDTO ubCommonDTOLogin = (UbCommonDTO) req.getSession().getAttribute("ubCommonDTOLogin");

        if (ubCommonDTOLogin != null) {

            // 세션에 ubCommonDTOLogin이 있으면 이름을 가져와서 모델에 추가
            String nameforuser = ubCommonDTOLogin.getName();
            model.addAttribute("username", nameforuser);
        }

        return  "index";
    }
}

