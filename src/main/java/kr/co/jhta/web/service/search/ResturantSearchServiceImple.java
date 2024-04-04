package kr.co.jhta.web.service.search;

import kr.co.jhta.web.dao.search.SearchRestaurant;
import kr.co.jhta.web.dto.RestrantByTheme;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ResturantSearchServiceImple implements ResturantSearchService{

    @Autowired
    private SearchRestaurant searchRestaurant;
    @Autowired
    SqlSession session;
    @Override
    public List<HashMap<String, Object>> selectRestraunt(HashMap<String, String> parameters) {
        System.out.println(searchRestaurant.getRestraunt(parameters));

        //List<HashMap<String, Object>> searchRestaurant = session.selectList("searchRestaurant", parameters);
        return searchRestaurant.getRestraunt(parameters);
    }

    @Override
    public List<RestrantByTheme> restaurantTheme(List restrauntId) {
        List<RestrantByTheme> restaurantTheme = session.selectList("restaurantTheme",restrauntId);
        return restaurantTheme;
    }

    @Override
    public List<RestrantByTheme> themeFilter() {
        return session.selectList("themeFilter");
    }

    @Override
    public List<HashMap<String, Object>> themeByRestaurant(HashMap<String, Object> parameters) {
        return session.selectList("themeByRestaurant", parameters);
    }

    @Override
    public List<RestrantByTheme> findBytheme(List theme) {
        return session.selectList("findBytheme", theme);
    }

    @Override
    public List<RestrantByTheme> themeIdconvertName(List themeId) {
        return session.selectList("themeIdconvertName", themeId);
    }
}
