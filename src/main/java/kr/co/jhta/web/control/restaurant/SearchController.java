package kr.co.jhta.web.control.restaurant;


import kr.co.jhta.web.dto.RestrantByTheme;
import kr.co.jhta.web.dto.restaurant.RestaurantDTO;
import kr.co.jhta.web.service.restaurant.RestaurantService;
import kr.co.jhta.web.service.search.ResturantSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;


@Controller
public class SearchController {

    @Autowired
    ResturantSearchService resturantSearchService;

    @Autowired
    RestaurantService restaurantService;

    @RequestMapping(value = "/search/keyword")
    public String firstFilter(@RequestParam("keyword") String searchKeyword,
                              @RequestParam(value = "orderby", defaultValue = "default") String orderby,
                              Model model){
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("keyword",searchKeyword);
        parameters.put("orderby",orderby);

        //레스토랑 이름, 주소, 메뉴 이름 컬럼에 키워드로 like 검색
        List<HashMap<String, Object>> restrauntResults = resturantSearchService.selectRestraunt(parameters);

        //검색값이 없을 경우 검색 페이지로 리턴
        if(restrauntResults.isEmpty()){
            model.addAttribute("keyWord","해당 하는 레스토랑이 없습니다." );
            model.addAttribute("msg", "해당 하는 레스토랑이 없습니다.");
            return "views/restaurant/searchpage";
        }
        //keyword 검색 결과에서 레스토랑 id 만 추출
        List<Object> restaurantId = restrauntResults
                .stream().map(x -> x.get("restaurant_id")).collect(Collectors.toList());

        //추출한 레스토랑 id를 이용해서 해당 레스토랑에 관한 테마 검색
        List<RestrantByTheme> restaurantTheme = resturantSearchService.restaurantTheme(restaurantId);

        //테마 분류 코드 별 그룹핑( 사이드바에 출력할 테마들)
        Map<String, List<RestrantByTheme>> collect = restaurantTheme.stream()
                .collect(Collectors.groupingBy(RestrantByTheme::getTheme_classify_code));

        //사이드바에 출력할 테마의 형태를 (theme_id , theme_name) 형태의 hashmap으로 가공
        //theme_id값은 결과내 검색 시 검색값으로 활용
        Map<String, HashMap<Integer, String>> themeCode = new HashMap<>();
         for(String code : collect.keySet()){
             HashMap<Integer, String> themeName = new HashMap<>();
             for(RestrantByTheme k : collect.get(code)){
                 if(k.getTheme_classify_code().equals(code)){
                     themeName.put(k.getTheme_id(), k.getTheme_name());
                 }
             }

             //구분 code 한글로 변환 (사이드바 출력값으로 변환)
             if(code.equals("atmosphere")){
                 code="분위기";
             }else if(code.equals("country")){
                 code="국가별";
             }else if(code.equals("location")){
                 code="지역";
             }else if(code.equals("facility")){
                 code="편의시설";
             }else if(code.equals("food")){
                 code="음식 종류";
             }
             themeCode.put(code, themeName);
        }

        //레스토랑id로 그룹핑
        Map<Integer, List<RestrantByTheme>> themeGroup = restaurantTheme.stream()
                .collect(Collectors.groupingBy(RestrantByTheme::getRestaurant_id));


        //각각의 레스토랑 테마들을 하나의 String으로 변환
        HashMap<Integer, String> restrantThemeString = new HashMap();

        for (Integer x : themeGroup.keySet() ) {
            List<Integer> list = new ArrayList<>();
            for ( RestrantByTheme restrantByTheme : themeGroup.get(x) ) {
                list.add( restrantByTheme.getTheme_id()) ;
            }
            String str = StringUtils.join(list, ",");
            restrantThemeString.put(x, str);
        }

        //검색어 like 결과에 해당 레스토랑 theme 추가
        for (HashMap<String, Object> map : restrauntResults){
            for( Integer x:  restrantThemeString.keySet() ) {
                if( Integer.parseInt(String.valueOf(map.get("restaurant_id")))==x) {
                    map.put("theme", restrantThemeString.get(x));
                }
            }
        }

        model.addAttribute("resultList", restrauntResults);
        model.addAttribute("restaurantTheme", themeCode);
        model.addAttribute("keyWord",searchKeyword);
        model.addAttribute("searchType","keyWord");
        return "views/restaurant/searchpage";
    }

    @RequestMapping("/search/theme")
    public String themeFiltergo(Model model){
        //테마 검색을 위한 현재 db상에 등록된 전체 레스토랑에 대한 테마 검색
        List<RestrantByTheme> restrantByThemes = resturantSearchService.themeFilter();

        //테마 분류 코드 별 그룹핑
        Map<String, List<RestrantByTheme>> collect = restrantByThemes.stream()
                .collect(Collectors.groupingBy(RestrantByTheme::getTheme_classify_code));

        model.addAttribute("restaurantTheme",collect );
        return "views/restaurant/searchtheme";
    }

    @RequestMapping("/search/themes")
    public String searchBythemes(@RequestParam (required = false)List<Integer> checkTheme, Model model, RedirectAttributes re){

        if(checkTheme==null){
            re.addFlashAttribute("keyWord","검색 필터를 체크해 주세요.");
            return "redirect:/search/theme";
        }

        //사이드바에서 선택한 테마값들의 theme_id를 List형태로 받아서 해당 테마들을 가지고 있는 레스토랑 검색

        List<RestrantByTheme> bytheme = resturantSearchService.findBytheme(checkTheme);

        Map<Integer, List<RestrantByTheme>> bygrouping = bytheme.stream().collect(groupingBy(RestrantByTheme::getRestaurant_id));
        System.out.println(">>>" + bygrouping);
        Map<Integer, List<Integer>> collect1 = new HashMap<>();
        List<Integer> restaurantId = new ArrayList<>();

        //view단에서 받은 테마id에 해당하는 모든 레스토랑을 검색
        //검색된 레스토랑을 해당 id에 해당하는 테마들을 List에 넣고, 최종적으로 view단에서 받은 테마의 배열 사이즈와 같으면 해당 검색조건 충족
        //배열이 empty면 해당 조건에 충족되는 레스토랑이 없음. 리라이렉트 시킴
        for (Integer x : bygrouping.keySet()) {
            List<Integer> themeId = new ArrayList<>();
            bygrouping.get(x).forEach(theme ->
                    themeId.add(theme.getTheme_id()));
            collect1.put(x, themeId);
            if (themeId.size() == checkTheme.size()) {
                restaurantId.add(x);
            }
        }

        if (restaurantId.isEmpty()) {
            re.addFlashAttribute("keyWord", "조건에 해당하는 레스랑이 없습니다..");
            return "redirect:/search/theme";
        }

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("restaurantId",restaurantId);

        //사이드바에서 넘어온 thmem_id에 해당하는 레스토랑 id로 해당 레스토랑 정보 검색
        List<HashMap<String, Object>> restrauntResults = resturantSearchService.themeByRestaurant(parameters);

        //레스토랑 id를 이용해서 해당 레스토랑에 관한 테마 검색
        List<RestrantByTheme> restaurantTheme = resturantSearchService.restaurantTheme(restaurantId);

        //테마 분류 코드 별 그룹핑( 사이드바에 출력할 테마들)
        Map<String, List<RestrantByTheme>> collect = restaurantTheme.stream()
                .collect(Collectors.groupingBy(RestrantByTheme::getTheme_classify_code));

        //사이드바에 출력할 테마의 형태를 (theme_id , theme_name) 형태의 hashmap으로 가공
        //theme_id값은 결과내 검색 시 검색값으로 활용
        Map<String, HashMap<Integer, String>> themeCode = new HashMap<>();
        for(String code : collect.keySet()){
            HashMap<Integer, String> themeName = new HashMap<>();
            for(RestrantByTheme k : collect.get(code)){
                if(k.getTheme_classify_code().equals(code)){
                    themeName.put(k.getTheme_id(), k.getTheme_name());
                }
            }

            //구분 code 한글로 변환 (사이드바 출력값으로 변환)
            if(code.equals("atmosphere")){
                code="분위기";
            }else if(code.equals("country")){
                code="국가별";
            }else if(code.equals("location")){
                code="지역";
            }else if(code.equals("facility")){
                code="편의시설";
            }else if(code.equals("food")){
                code="음식 종류";
            }
            themeCode.put(code, themeName);
        }

        //레스토랑id로 그룹핑
        Map<Integer, List<RestrantByTheme>> themeGroup = restaurantTheme.stream()
                .collect(Collectors.groupingBy(RestrantByTheme::getRestaurant_id));

        HashMap<Integer, String> restrantThemeString = new HashMap();

        for (Integer x : themeGroup.keySet() ) {
            List<Integer> list = new ArrayList<>();
            for ( RestrantByTheme restrantByTheme : themeGroup.get(x) ) {
                list.add( restrantByTheme.getTheme_id()) ;
            }
            String str = StringUtils.join(list, ",");
            restrantThemeString.put(x, str);
        }

        //테마 결과에 해당 레스토랑 theme 추가
        for (HashMap<String, Object> map : restrauntResults){
            for( Integer x:  restrantThemeString.keySet() ) {
                if( Integer.parseInt(String.valueOf(map.get("restaurant_id")))==x) {
                    map.put("theme", restrantThemeString.get(x));
                }
            }
        }

        //요청 theme_id를 theme_name으로 변환 후 model에 전달
        List<RestrantByTheme> strings = resturantSearchService.themeIdconvertName(checkTheme);
        List<String> collect2 = strings.stream().map(x -> x.getTheme_name()).collect(Collectors.toList());
        String searchKeyword = StringUtils.join(collect2, " + ");

        model.addAttribute("resultList", restrauntResults);
        model.addAttribute("restaurantTheme", themeCode);
        model.addAttribute("keyWord",searchKeyword);
        model.addAttribute("searchType","theme");
        return "views/restaurant/searchpage";
    }

    @RequestMapping("/search/themes/orderby")
    public String themeOderby(@RequestParam (required = false) List<Integer> restaurantId,
                              @RequestParam(value = "orderby", defaultValue = "default") String orderby,
                              @RequestParam String keyWord, Model model){
        HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("restaurantId",restaurantId);
            parameters.put("orderby",orderby);

        //사이드바에서 넘어온 thmem_id에 해당하는 레스토랑 id로 해당 레스토랑 정보 검색
        List<HashMap<String, Object>> restrauntResults = resturantSearchService.themeByRestaurant(parameters);

        //레스토랑 id를 이용해서 해당 레스토랑에 관한 테마 검색
        List<RestrantByTheme> restaurantTheme = resturantSearchService.restaurantTheme(restaurantId);

        //테마 분류 코드 별 그룹핑( 사이드바에 출력할 테마들)
        Map<String, List<RestrantByTheme>> collect = restaurantTheme.stream()
                .collect(Collectors.groupingBy(RestrantByTheme::getTheme_classify_code));

        //사이드바에 출력할 테마의 형태를 (theme_id , theme_name) 형태의 hashmap으로 가공
        //theme_id값은 결과내 검색 시 검색값으로 활용
        Map<String, HashMap<Integer, String>> themeCode = new HashMap<>();
        for(String code : collect.keySet()){
            HashMap<Integer, String> themeName = new HashMap<>();
            for(RestrantByTheme k : collect.get(code)){
                if(k.getTheme_classify_code().equals(code)){
                    themeName.put(k.getTheme_id(), k.getTheme_name());
                }
            }

            //구분 code 한글로 변환 (사이드바 출력값으로 변환)
            if(code.equals("atmosphere")){
                code="분위기";
            }else if(code.equals("country")){
                code="국가별";
            }else if(code.equals("location")){
                code="지역";
            }else if(code.equals("facility")){
                code="편의시설";
            }else if(code.equals("food")){
                code="음식 종류";
            }
            themeCode.put(code, themeName);
        }

        //레스토랑id로 그룹핑
        Map<Integer, List<RestrantByTheme>> themeGroup = restaurantTheme.stream()
                .collect(Collectors.groupingBy(RestrantByTheme::getRestaurant_id));

        HashMap<Integer, String> restrantThemeString = new HashMap();

        for (Integer x : themeGroup.keySet() ) {
            List<Integer> list = new ArrayList<>();
            for ( RestrantByTheme restrantByTheme : themeGroup.get(x) ) {
                list.add( restrantByTheme.getTheme_id()) ;
            }
            String str = StringUtils.join(list, ",");
            restrantThemeString.put(x, str);
        }

        //테마 결과에 해당 레스토랑 theme 추가
        for (HashMap<String, Object> map : restrauntResults){
            for( Integer x:  restrantThemeString.keySet() ) {
                if( Integer.parseInt(String.valueOf(map.get("restaurant_id")))==x) {
                    map.put("theme", restrantThemeString.get(x));
                }
            }
        }

        model.addAttribute("resultList", restrauntResults);
        model.addAttribute("restaurantTheme", themeCode);
        model.addAttribute("keyWord",keyWord);
        model.addAttribute("searchType","theme");
        return "views/restaurant/searchpage";
    }

    @RequestMapping("/search/maintheme")
    public String hotplace(@RequestParam String theme, Model model){

        List<RestaurantDTO> restaurantDTOS = new ArrayList<>();
        if(theme.equals("hotplace")) {
             restaurantDTOS = restaurantService.select10hotRestaurants();
        }else if(theme.equals("newopen")){
             restaurantDTOS = restaurantService.select10NewRestaurants();
        }else if(theme.equals("random")){
             restaurantDTOS = restaurantService.recommandations();
        }

        List<Object> restaurantId = restaurantDTOS
                .stream().map(x -> x.getRestaurantId()).collect(Collectors.toList());
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("restaurantId",restaurantId);

        //사이드바에서 넘어온 thmem_id에 해당하는 레스토랑 id로 해당 레스토랑 정보 검색
        List<HashMap<String, Object>> restrauntResults = resturantSearchService.themeByRestaurant(parameters);

        //레스토랑 id를 이용해서 해당 레스토랑에 관한 테마 검색
        List<RestrantByTheme> restaurantTheme = resturantSearchService.restaurantTheme(restaurantId);

        //테마 분류 코드 별 그룹핑( 사이드바에 출력할 테마들)
        Map<String, List<RestrantByTheme>> collect = restaurantTheme.stream()
                .collect(Collectors.groupingBy(RestrantByTheme::getTheme_classify_code));

        //사이드바에 출력할 테마의 형태를 (theme_id , theme_name) 형태의 hashmap으로 가공
        //theme_id값은 결과내 검색 시 검색값으로 활용
        Map<String, HashMap<Integer, String>> themeCode = new HashMap<>();
        for(String code : collect.keySet()){
            HashMap<Integer, String> themeName = new HashMap<>();
            for(RestrantByTheme k : collect.get(code)){
                if(k.getTheme_classify_code().equals(code)){
                    themeName.put(k.getTheme_id(), k.getTheme_name());
                }
            }

            //구분 code 한글로 변환 (사이드바 출력값으로 변환)
            if(code.equals("atmosphere")){
                code="분위기";
            }else if(code.equals("country")){
                code="국가별";
            }else if(code.equals("location")){
                code="지역";
            }else if(code.equals("facility")){
                code="편의시설";
            }else if(code.equals("food")){
                code="음식 종류";
            }
            themeCode.put(code, themeName);
        }

        //레스토랑id로 그룹핑
        Map<Integer, List<RestrantByTheme>> themeGroup = restaurantTheme.stream()
                .collect(Collectors.groupingBy(RestrantByTheme::getRestaurant_id));

        HashMap<Integer, String> restrantThemeString = new HashMap();

        for (Integer x : themeGroup.keySet() ) {
            List<Integer> list = new ArrayList<>();
            for ( RestrantByTheme restrantByTheme : themeGroup.get(x) ) {
                list.add( restrantByTheme.getTheme_id()) ;
            }
            String str = StringUtils.join(list, ",");
            restrantThemeString.put(x, str);
        }

        //테마 결과에 해당 레스토랑 theme 추가
        for (HashMap<String, Object> map : restrauntResults){
            for( Integer x:  restrantThemeString.keySet() ) {
                if( Integer.parseInt(String.valueOf(map.get("restaurant_id")))==x) {
                    map.put("theme", restrantThemeString.get(x));
                }
            }
        }

        //요청 theme_id를 theme_name으로 변환 후 model에 전달


        model.addAttribute("resultList", restrauntResults);
        model.addAttribute("restaurantTheme", themeCode);
        model.addAttribute("keyWord","");
        model.addAttribute("searchType","theme");
        return "views/restaurant/searchpage";
    }


}

