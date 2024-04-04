package kr.co.jhta.web.dao.mypage;

import kr.co.jhta.web.dto.RestrantByTheme;
import kr.co.jhta.web.dto.restaurant.RestaurantDTO;
import kr.co.jhta.web.dto.userBusiness.UbCommonDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface MypageDAO {


    public List<HashMap<String,Object>> getReservationHistory(Long ub_id);


    public List<HashMap<String,Object>> getLastBookingHistory(Long ub_id);

    public List<RestrantByTheme> locationByRestaurantId(List<Object> restaurantId);

    public List<HashMap<String, Object>> getBookingStatus(Long restaurantId);

    List<HashMap<String, Object>> getBookingStatusForCalendar(String selectedDate, Long restaurantId);

    List<HashMap<String, Object>> checkBookingRequest(Long restaurantId);

    void acceptBooking(Long reservationId);

    void rejectBooking(Long reservationId);

    List<HashMap<String, Object>> getCancelledBookingHistory(Long ubId);


    List<UbCommonDTO> getProfileInfo(Long ubId);

    List<RestaurantDTO> restaurantInfo(Long restaurantId);

    Long getRestaurantIdByUbId(Long ubId);
}
