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

    // defaultForm 연결 - test용
    @GetMapping("/de")
    public String de(){
        return "/fragment/defaultForm";
    }


    @GetMapping({"/","/index","/home"})
    public String main(Model model, HttpServletRequest req){
        List<RestaurantDTO> Nlist = restaurantService.select10NewRestaurants();
        System.out.println("Nlist:"+Nlist);
        model.addAttribute("Nlist",Nlist);


        List<RestaurantDTO> Hlist = restaurantService.select10hotRestaurants();
        System.out.println("Hlist:"+Hlist);
        model.addAttribute("Hlist",Hlist);


        List<RestaurantDTO> Rlist = restaurantService.recommandations();
        System.out.println("Rlist:"+Rlist);
        model.addAttribute("Rlist",Rlist);



        // 사용자 정보 확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 이름 가져오기
        String name = authentication.getName();
        String username = null;

        System.out.println(authentication);
        System.out.println(authentication.getPrincipal());

        if(authentication.getPrincipal() instanceof CustomOAuth2User ) {
            CustomOAuth2User principal
                    = (CustomOAuth2User) authentication.getPrincipal();
            username = principal.getUsername();

            System.out.println("MainController username : " + username);
            req.getSession().setAttribute("snsId", username);
        }
        // Role 확인하기
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        System.out.println("MainController role 1 : " + role);

        if ( name.equals("anonymousUser") && role.equals("ROLE_ANONYMOUS") ) { // 소셜 로그인 안할 때
            UbCommonDTO dtoLogin = (UbCommonDTO) req.getSession().getAttribute("ubCommonDTOLogin");
            if ( dtoLogin != null ) {
                System.out.println("mainController dto : " + dtoLogin);
                System.out.println(dtoLogin.getUserId() + " : " + dtoLogin.getUserPw());
                UbCommonDTO dto = loginService.findByUserIdAndPw(dtoLogin.getUserId(), dtoLogin.getUserPw());
//                if (name.equals("anonymousUser")) {
                    name = dto.getName();
//                }
//                if (role.equals("ROLE_ANONYMOUS")) {
                    role = dto.getSecurityRole();
//                }
                req.getSession().setAttribute("ubCommonDTOLogin", dto);
            }
            System.out.println("MainController name : " + name);
            System.out.println("MainController role 2 : " + role);
            if ( role == null ) {
                return "views/userBusiness/signupDetail.html";
            }

        } else { // 소셜 로그인 할 때
            UbCommonDTO dto = loginService.findByUserId(username);

            role = dto.getSecurityRole();
            System.out.println("MainController role 3 : " + role);
            System.out.println("MainController user_Id : " + dto.getUserId());
            System.out.println("MainController ub_id : " + dto.getUbId());
            req.getSession().setAttribute("ubCommonDTOLogin", dto);
            if (role == null ) {
                System.out.println("MainController snsId : " + req.getSession().getAttribute("snsId"));
                return "views/userBusiness/signupSocial.html";
            }
        }

        model.addAttribute("name", name);
        model.addAttribute("role", role);



        //메인 테마에 회원이름 넣으려고 추가했어요
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

