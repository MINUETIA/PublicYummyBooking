package kr.co.jhta.web.service.mypage;

import kr.co.jhta.web.dao.mypage.MypageDAO;
import kr.co.jhta.web.dto.RestrantByTheme;
import kr.co.jhta.web.dto.restaurant.RestaurantDTO;
import kr.co.jhta.web.dto.userBusiness.UbCommonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

@Service
public class MypageServiceImple implements  MypageService {

    @Autowired
    MypageDAO mypageDAO;

    @Override
    public List<HashMap<String, Object>> lastBookingList(Long ub_id) {
        return mypageDAO.getReservationHistory(ub_id);
    }

    @Override
    public List<HashMap<String, Object>> lastReservationHistory(Long ubId) {
        return mypageDAO.getLastBookingHistory(ubId);
    }

    @Override
    public List<HashMap<String, Object>> cancelledReservationHistory(Long ubId) {
        return mypageDAO.getCancelledBookingHistory(ubId);
    }


    @Override
    public List<RestrantByTheme> locationByRestaurantId(List<Object> restrantId) {
        return mypageDAO.locationByRestaurantId(restrantId);
    }

    @Override
    public List<HashMap<String, Object>> getBookingStatus(Long restaurantId) {
        return mypageDAO.getBookingStatus(restaurantId);
    }

    @Override
    public List<HashMap<String, Object>> getBookingStatusForCalendar(String selectedDate, Long restaurantId) {
        List<HashMap<String, Object>> bookingStatusList = mypageDAO.getBookingStatusForCalendar(selectedDate, restaurantId);

        // 날짜 형식 변경
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH시 mm분");
        for (HashMap<String, Object> bookingStatus : bookingStatusList) {
            LocalDateTime reservationDate = (LocalDateTime) bookingStatus.get("reservationDate");
            if (reservationDate != null) {
                String formattedDate = reservationDate.format(outputFormatter);
                bookingStatus.put("reservationDate", formattedDate);
            }
        }

        return bookingStatusList;
    }

    @Override
    public List<HashMap<String, Object>> getBookingRequest(Long restaurantId) {
        List<HashMap<String, Object>> booking = mypageDAO.checkBookingRequest(restaurantId);

        return booking;
    }

    @Override
    public void acceptReservation(Long reservationId) {

        mypageDAO.acceptBooking(reservationId);

    }

    @Override
    public void rejectReservation(Long reservationId) {
        mypageDAO.rejectBooking(reservationId);

    }

    @Override
    public List<UbCommonDTO> getProfile(Long ubId) {
        return mypageDAO.getProfileInfo(ubId);
    }

    @Override
    public List<RestaurantDTO> getRestaurantInfo(Long restaurantId) {
        return  mypageDAO.restaurantInfo(restaurantId);


    }

    @Override
    public Long getRestaurantIdByUbId(Long ubId) {
        return mypageDAO.getRestaurantIdByUbId(ubId);
    }

}

