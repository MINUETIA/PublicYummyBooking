package kr.co.jhta.web.service.mypage;

import kr.co.jhta.web.dto.RestrantByTheme;
import kr.co.jhta.web.dto.restaurant.RestaurantDTO;
import kr.co.jhta.web.dto.userBusiness.UbCommonDTO;

import java.util.HashMap;
import java.util.List;

public interface MypageService {

    public List<HashMap<String,Object>> lastBookingList(Long ub_id);

    List<HashMap<String, Object>> lastReservationHistory(Long ubId);

    List<HashMap<String, Object>> cancelledReservationHistory(Long ubId);

    public List<RestrantByTheme> locationByRestaurantId(List<Object> restrantId);

    public List<HashMap<String,Object>> getBookingStatus(Long restaurantId);

    List<HashMap<String, Object>> getBookingStatusForCalendar(String selectedDate, Long restaurantId);

    List<HashMap<String, Object>> getBookingRequest(Long restaurantId);

    void acceptReservation(Long reservationId);

    void rejectReservation(Long reservationId);


    List<UbCommonDTO> getProfile(Long ubId);

    List<RestaurantDTO> getRestaurantInfo(Long restaurantId);


    Long getRestaurantIdByUbId(Long ubId);
}
