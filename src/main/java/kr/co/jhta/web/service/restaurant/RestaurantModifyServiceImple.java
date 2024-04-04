package kr.co.jhta.web.service.restaurant;

import kr.co.jhta.web.dao.restaurant.RestaurantModifyDAO;
import kr.co.jhta.web.dto.restaurant.RestaurantDTO;
import kr.co.jhta.web.dto.restaurant.RestaurantMenuDTO;
import kr.co.jhta.web.dto.restaurant.RestaurantOpeningDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantModifyServiceImple implements RestaurantModifyService{
    @Autowired
    RestaurantModifyDAO dao;

    @Override
    public void insertMenu(RestaurantMenuDTO dto) {
        dao.addMenu(dto);
    }

    @Override
    public void deleteMenu(Long restaurantMenuId) {
        dao.removeMenu(restaurantMenuId);
    }

    @Override
    public void changeAdd(RestaurantDTO dto) {
        dao.updateAdd(dto);
    }

    @Override
    public void changeTime(RestaurantOpeningDTO dto) {
        dao.updateTime(dto);
    }


}
