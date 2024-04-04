package kr.co.jhta.web.service.restaurant;

import kr.co.jhta.web.dao.restaurant.RestaurantDAO;
import kr.co.jhta.web.dto.restaurant.RestaurantDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RestaurantServiceImple implements RestaurantService {
    @Autowired
    RestaurantDAO restaurantDAO;


    @Override
    public List<RestaurantDTO> select10NewRestaurants() {
        return restaurantDAO.select10NewRestaurants();
    }

    @Override
    public List<RestaurantDTO> select10hotRestaurants() {
        return restaurantDAO.select10hotRestaurants();
    }

    @Override
    public List<RestaurantDTO> recommandations() {
        return restaurantDAO.recommandations();
    }
}
