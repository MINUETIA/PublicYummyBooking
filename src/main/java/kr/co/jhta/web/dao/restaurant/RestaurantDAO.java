package kr.co.jhta.web.dao.restaurant;

import kr.co.jhta.web.dto.restaurant.RestaurantDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RestaurantDAO {

    public List<RestaurantDTO> select10NewRestaurants();

    public List<RestaurantDTO> address10NewRestaurants();

    List<RestaurantDTO> select10hotRestaurants();

    List<RestaurantDTO> recommandations();
}
