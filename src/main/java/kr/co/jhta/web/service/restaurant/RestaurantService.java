package kr.co.jhta.web.service.restaurant;

import kr.co.jhta.web.dto.restaurant.RestaurantDTO;

import java.util.List;


public interface RestaurantService {


    List<RestaurantDTO> select10NewRestaurants();

    List<RestaurantDTO> select10hotRestaurants();

    List<RestaurantDTO> recommandations();
}