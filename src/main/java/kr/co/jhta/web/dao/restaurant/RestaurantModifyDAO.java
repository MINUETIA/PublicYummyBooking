package kr.co.jhta.web.dao.restaurant;

import kr.co.jhta.web.dto.restaurant.RestaurantDTO;
import kr.co.jhta.web.dto.restaurant.RestaurantMenuDTO;
import kr.co.jhta.web.dto.restaurant.RestaurantOpeningDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface RestaurantModifyDAO {
    public void addMenu(RestaurantMenuDTO dto);

    public void removeMenu(Long restaurantMenuId);

    public void updateAdd(RestaurantDTO dto);

    public void updateTime(RestaurantOpeningDTO dto);
}
