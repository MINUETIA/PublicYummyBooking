package kr.co.jhta.web.dao.search;

import kr.co.jhta.web.dto.RestrantByTheme;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
@Mapper
public interface SearchRestaurant {
   public List<HashMap<String, Object>> selectRestraunt(HashMap<String, String> parameters);

   public List<RestrantByTheme> restaurantTheme(List restrauntId);

   public List<RestrantByTheme> themeFilter();
   public List<HashMap<String, Object>> themeByRestaurant(HashMap<String, Object> parameters);

   public List<RestrantByTheme> findBytheme(List theme);

   public List<RestrantByTheme> themeIdconvertName(List themeId);

   List<HashMap<String, Object>> getRestraunt(HashMap<String, String> parameters);
}
