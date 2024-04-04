package kr.co.jhta.web.service.restaurant;


import kr.co.jhta.web.dto.restaurant.RestaurantDTO;
import kr.co.jhta.web.dto.restaurant.RestaurantMenuDTO;
import kr.co.jhta.web.dto.restaurant.RestaurantOpeningDTO;

public interface RestaurantModifyService{
    public void insertMenu(RestaurantMenuDTO dto);
    public void deleteMenu(Long restaurantMenuId);
    public void changeAdd(RestaurantDTO dto);
    public void changeTime(RestaurantOpeningDTO dto);
}
