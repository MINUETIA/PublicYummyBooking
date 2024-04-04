package kr.co.jhta.web.service.restaurant;

import kr.co.jhta.web.dao.restaurant.RestaurantDetailsDAO;
import kr.co.jhta.web.dto.RestrantByTheme;
import kr.co.jhta.web.dto.reservation.ReservationDTO;
import kr.co.jhta.web.dto.restaurant.RestaurantDTO;
import kr.co.jhta.web.dto.restaurant.RestaurantMenuDTO;
import kr.co.jhta.web.dto.restaurant.RestaurantOpeningDTO;
import kr.co.jhta.web.dto.review.EvaluationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantDetailsServiceImple implements RestaurantDetailsService {
    @Autowired
    RestaurantDetailsDAO dao;


    @Override
    public RestaurantDTO RestaurantOne(int restaurantId) {
        return dao.selectRestaurantOne(restaurantId);
    }

    @Override
    public List<RestaurantMenuDTO> getMenu(int restaurantId) {
        List<RestaurantMenuDTO> list = dao.restaurantMenuList(restaurantId);
        return list;
    }

    @Override
    public EvaluationDTO getStarRating(int restaurantId) {
        EvaluationDTO star = dao.starRating(restaurantId);
        return star;
    }

    @Override
    public List<RestaurantOpeningDTO> getOperatingTime(int restaurantId) {
        List<RestaurantOpeningDTO> operatingTime = dao.operatingTime(restaurantId);
        return operatingTime;
    }

    @Override
    public void reservation(ReservationDTO reservationDTO) {
        dao.addReservation(reservationDTO);
    }

    @Override
    public List<RestrantByTheme> restaurantTheme(int restaurantId) {
        return dao.getTheme(restaurantId);
    }


}
