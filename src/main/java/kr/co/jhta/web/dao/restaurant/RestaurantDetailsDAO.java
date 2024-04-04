package kr.co.jhta.web.dao.restaurant;

import kr.co.jhta.web.dto.RestrantByTheme;
import kr.co.jhta.web.dto.reservation.ReservationDTO;
import kr.co.jhta.web.dto.restaurant.RestaurantDTO;
import kr.co.jhta.web.dto.restaurant.RestaurantMenuDTO;
import kr.co.jhta.web.dto.restaurant.RestaurantOpeningDTO;
import kr.co.jhta.web.dto.review.EvaluationDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RestaurantDetailsDAO {
    public RestaurantDTO selectRestaurantOne(int restaurantId);
    public List<RestaurantMenuDTO> restaurantMenuList(int restaurantId);
    public EvaluationDTO starRating(int restaurantId);
    public List<RestaurantOpeningDTO> operatingTime(int restaurantId);
    public void addReservation(ReservationDTO reservationDTO);
    public List<RestrantByTheme> getTheme(int reservationDTO);
}
