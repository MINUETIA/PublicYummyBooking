package kr.co.jhta.web.service.restaurant;

import kr.co.jhta.web.dto.RestrantByTheme;
import kr.co.jhta.web.dto.reservation.ReservationDTO;
import kr.co.jhta.web.dto.restaurant.RestaurantDTO;
import kr.co.jhta.web.dto.restaurant.RestaurantMenuDTO;
import kr.co.jhta.web.dto.restaurant.RestaurantOpeningDTO;
import kr.co.jhta.web.dto.review.EvaluationDTO;

import java.util.List;

public interface RestaurantDetailsService {
    RestaurantDTO RestaurantOne(int restaurantId);
    List<RestaurantMenuDTO> getMenu(int restaurantId);
    EvaluationDTO getStarRating(int restaurantId);
    List<RestaurantOpeningDTO> getOperatingTime(int restaurantId);
    void reservation(ReservationDTO reservationDTO);
    List<RestrantByTheme> restaurantTheme(int restaurantId);
}
