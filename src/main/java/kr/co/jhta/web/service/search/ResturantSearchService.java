package kr.co.jhta.web.service.search;

import kr.co.jhta.web.dto.RestrantByTheme;

import java.util.HashMap;
import java.util.List;


public interface ResturantSearchService {
    public List<HashMap<String, Object>> selectRestraunt(HashMap<String, String> parameters);

    public List<RestrantByTheme> restaurantTheme(List restrauntId);

    public List<RestrantByTheme> themeFilter();
    public List<HashMap<String, Object>> themeByRestaurant(HashMap<String, Object> parameters);

    public List<RestrantByTheme> findBytheme(List theme);

    public List<RestrantByTheme> themeIdconvertName(List themeId);
}
