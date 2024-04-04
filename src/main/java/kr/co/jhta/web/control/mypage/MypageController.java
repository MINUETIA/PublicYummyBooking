package kr.co.jhta.web.control.mypage;

import jakarta.servlet.http.HttpSession;
import kr.co.jhta.web.dto.RestrantByTheme;
import kr.co.jhta.web.dto.restaurant.RestaurantDTO;
import kr.co.jhta.web.dto.userBusiness.UbCommonDTO;
import kr.co.jhta.web.service.mypage.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.stream.Collectors;


@Controller
public class MypageController {

    @Autowired
    MypageService mypageService;

    @GetMapping("/mypage")
    public String bookinghistory(/*@RequestParam("ubId")int ub_id*/ Model model, HttpSession httpSession) {

        //세션에서 ub_id가져오기
        UbCommonDTO ubCommonDTOLogin = (UbCommonDTO) httpSession.getAttribute("ubCommonDTOLogin");
        if (ubCommonDTOLogin == null) {
            // 세션에서 ubCommonDTOLogin이 null인 경우 처리
            return "redirect:/login/login"; // 로그인 페이지로 리다이렉트
        }

        Long ub_id = ubCommonDTOLogin.getUbId();
        model.addAttribute("ubCommonDTOLogin", ubCommonDTOLogin);
        System.out.println("ubCommonDTOLogin:"+ubCommonDTOLogin);

        List<HashMap<String, Object>> reservationHistory = mypageService.lastBookingList(ub_id);
        System.out.println("reservationHistory:" + reservationHistory);

        if (reservationHistory == null){
            reservationHistory = Collections.emptyList();
        } //예약내역이 없을 때 빈 리스트 생성

        //레스토랑 id 추출
        List<Object> restaurantId = reservationHistory.stream().map(x -> x.get("restaurantId")).filter(Objects::nonNull).collect(Collectors.toList());
        System.out.println("restaurantId:    ================>  "+restaurantId);


            //해당 레스토랑 id 검색
        if(!restaurantId.isEmpty()) {
            List<RestrantByTheme> restrantLocations = mypageService.locationByRestaurantId(restaurantId);
            //레스토랑 id 별 location 주소 정보 형태로 가공
            HashMap<Integer, Object> test = new HashMap();

            for (RestrantByTheme x : restrantLocations) {

                test.put(x.getRestaurant_id(), x.getTheme_name());
            }
            //     test <== location 정보 , 식당 번호

            for (HashMap<String, Object> x : reservationHistory) {
                for (Integer id : test.keySet()) {

                    if (Integer.parseInt(String.valueOf(x.get("restaurantId"))) == id) {
                        System.out.println("in");
                        x.put("location", test.get(id));
                    }
                }
            }
            System.out.println("reservationHistory+location"+reservationHistory);
            model.addAttribute("list", reservationHistory);

        }else{
            System.out.println("식당정보 없음");
        }





        List<HashMap<String, Object>> lastReservationHistory = mypageService.lastReservationHistory(ub_id);
        if (lastReservationHistory == null){
            lastReservationHistory = Collections.emptyList();
        } //


        //레스토랑 id 추출
        List<Object> restaurantId2 = lastReservationHistory.stream().map(x -> x.get("restaurantId")).collect(Collectors.toList());
        //해당 레스토랑 id 검색
        if(!restaurantId.isEmpty()) {
            List<RestrantByTheme> restrantLocations = mypageService.locationByRestaurantId(restaurantId2);
            System.out.println("restrantLocations : " + restrantLocations);
            //레스토랑 id 별 location 주소 정보 형태로 가공
            HashMap<Integer, Object> test = new HashMap();

            for (RestrantByTheme x : restrantLocations) {

                test.put(x.getRestaurant_id(), x.getTheme_name());
            }

            for (HashMap<String, Object> x : lastReservationHistory) {
                for (Integer id : test.keySet()) {

                    if (Integer.parseInt(String.valueOf(x.get("restaurantId"))) == id) {
                        System.out.println("in");
                        x.put("location", test.get(id));
                        System.out.println("location ===> "+ test.get(id));
                    }
                }
            }
            model.addAttribute("Llist", lastReservationHistory);
            System.out.println("lastReservationHistory:" + lastReservationHistory);

        }else{
            System.out.println("식당정보 없음");
        }



        List<HashMap<String, Object>> cancelledReservationHistory = mypageService.cancelledReservationHistory(ub_id);
        if (cancelledReservationHistory == null){
            cancelledReservationHistory = Collections.emptyList();
        } //


        //레스토랑 id 추출
        List<Object> restaurantId3 = cancelledReservationHistory.stream().map(x -> x.get("restaurantId")).collect(Collectors.toList());
        //해당 레스토랑 id 검색
        if(!restaurantId.isEmpty()) {
            List<RestrantByTheme> restrantLocations = mypageService.locationByRestaurantId(restaurantId3);
            //레스토랑 id 별 location 주소 정보 형태로 가공
            HashMap<Integer, Object> test = new HashMap();

            for (RestrantByTheme x : restrantLocations) {

                test.put(x.getRestaurant_id(), x.getTheme_name());
            }

            for (HashMap<String, Object> x : cancelledReservationHistory) {
                for (Integer id : test.keySet()) {

                    if (Integer.parseInt(String.valueOf(x.get("restaurantId"))) == id) {
                        System.out.println("in");
                        x.put("location", test.get(id));
                    }
                }
            }

            model.addAttribute("Clist", cancelledReservationHistory);
            System.out.println("Clist"+cancelledReservationHistory);

        }else{
            System.out.println("식당정보 없음");
        }



        //프로필 정보 가져오기
        List<UbCommonDTO> getProfile = mypageService.getProfile(ub_id);
        model.addAttribute("pList", getProfile);

        return "views/mypage/bookinghistory";
    }



    @GetMapping("/bookingstatus")
    public String bookingstatus(Model model,HttpSession httpSession) {


        //세션에서 ub_id가져오기
        UbCommonDTO ubCommonDTOLogin = (UbCommonDTO) httpSession.getAttribute("ubCommonDTOLogin");
        if (ubCommonDTOLogin == null) {
            // 세션에서 ubCommonDTOLogin이 null인 경우 처리
            return "redirect:/login/login"; // 로그인 페이지로 리다이렉트
        }

        Long restaurantId=1L;
        model.addAttribute("ubCommonDTOLogin", ubCommonDTOLogin);


        List<HashMap<String, Object>> getBookingStatus = mypageService.getBookingStatus(restaurantId);
        System.out.println("예약현황:" + getBookingStatus);


        model.addAttribute("list", getBookingStatus);
        return "views/mypage/bookingstatus";
    }


    @GetMapping("/calendar")
    public String calendar(Model model,HttpSession httpSession) {

        //세션에서 ub_id가져오기
        UbCommonDTO ubCommonDTOLogin = (UbCommonDTO) httpSession.getAttribute("ubCommonDTOLogin");
        if (ubCommonDTOLogin == null) {
            // 세션에서 ubCommonDTOLogin이 null인 경우 처리
            return "redirect:/login/login"; // 로그인 페이지로 리다이렉트
        }


        Long ub_id = ubCommonDTOLogin.getUbId();
        Long restaurantId= mypageService.getRestaurantIdByUbId(ub_id);
        System.out.println("restaurantId:"+restaurantId);

        model.addAttribute("ubCommonDTOLogin", ubCommonDTOLogin);
        model.addAttribute("restaurantId", restaurantId);



        List<RestaurantDTO> getRestaurantInfo = mypageService.getRestaurantInfo(restaurantId);
        model.addAttribute("Rinfo", getRestaurantInfo);
        System.out.println("Rinfo:" + getRestaurantInfo);


        return "views/mypage/calendar";
    }


    @GetMapping("/getReservationDate")
    public String getReservationdetails(@RequestParam("date") String date, Model model,HttpSession httpSession) {

        System.out.println("Selected date: " + date);



        //세션에서 ub_id가져오기
        UbCommonDTO ubCommonDTOLogin = (UbCommonDTO) httpSession.getAttribute("ubCommonDTOLogin");
        if (ubCommonDTOLogin == null) {
            // 세션에서 ubCommonDTOLogin이 null인 경우 처리
            return "redirect:/login/login"; // 로그인 페이지로 리다이렉트
        }


        Long ub_id = ubCommonDTOLogin.getUbId();
        Long restaurantId= mypageService.getRestaurantIdByUbId(ub_id);
        System.out.println("restaurantId:"+restaurantId);

        model.addAttribute("ubCommonDTOLogin", ubCommonDTOLogin);
        model.addAttribute("restaurantId", restaurantId);

        List<RestaurantDTO> getRestaurantInfo = mypageService.getRestaurantInfo(restaurantId);
        model.addAttribute("Rinfo", getRestaurantInfo);
        System.out.println("Rinfo:" + getRestaurantInfo);
        model.addAttribute("restaurantId" , restaurantId);

        List<HashMap<String, Object>> getBookingStatusForCalendar = mypageService.getBookingStatusForCalendar(date, restaurantId);


        model.addAttribute("list", getBookingStatusForCalendar);

        model.addAttribute("date", date);
        System.out.println("getBookingStatusForCalendar" + getBookingStatusForCalendar);


        return "views/mypage/calendar";

    }

    @GetMapping("/getNotYetReservationDate")
    @ResponseBody
    public List<HashMap<String, Object>> getReservationdetails(Model model, /*@RequestParam("restaurantId") Long restaurantId,*/HttpSession httpSession) {


        //세션에서 ub_id가져오기
        UbCommonDTO ubCommonDTOLogin = (UbCommonDTO) httpSession.getAttribute("ubCommonDTOLogin");

        Long ub_id = ubCommonDTOLogin.getUbId();
        Long restaurantId= mypageService.getRestaurantIdByUbId(ub_id);

        List<HashMap<String, Object>> getBookingReservationForCalendar = mypageService.getBookingRequest((long) restaurantId);

        return getBookingReservationForCalendar;
    }


    @GetMapping("/acceptReservation")
    @ResponseBody
    public String acceptReservation(@RequestParam("reservationId") int reservationId) {
        System.out.println("파라미터 값 : " + reservationId);
        // 예약을 수락하는 로직 수행
        mypageService.acceptReservation((long) reservationId);
        return "Reservation accepted successfully.";
    }


    @PostMapping("/rejectReservation")
    @ResponseBody
    public String rejectReservation(@RequestParam("reservationId") Long reservationId) {
        // 예약을 거절하는 로직 수행
        mypageService.rejectReservation(reservationId);
        return "Reservation rejected successfully";

    }





    @GetMapping("/viewAllReservations")
    public String viewAllReservations(@RequestParam("selectedDate") String selectedDate, Model model,HttpSession httpSession) {
        //세션에서 ub_id가져오기
        UbCommonDTO ubCommonDTOLogin = (UbCommonDTO) httpSession.getAttribute("ubCommonDTOLogin");
        if (ubCommonDTOLogin == null) {
            // 세션에서 ubCommonDTOLogin이 null인 경우 처리
            return "redirect:/login/login"; // 로그인 페이지로 리다이렉트
        }


        Long ub_id = ubCommonDTOLogin.getUbId();
        Long restaurantId= mypageService.getRestaurantIdByUbId(ub_id);
        System.out.println("restaurantId:"+restaurantId);

        model.addAttribute("ubCommonDTOLogin", ubCommonDTOLogin);
        model.addAttribute("restaurantId", restaurantId);

        System.out.println("Selected date2: ");



        // 레스토랑 프로필 가져오기
        List<RestaurantDTO> getRestaurantInfo = mypageService.getRestaurantInfo(restaurantId);
        model.addAttribute("Rinfo", getRestaurantInfo);

        // 선택된 날짜의 예약 정보 가져오기
        List<HashMap<String, Object>> getBookingStatusForCalendar = mypageService.getBookingStatusForCalendar(selectedDate, restaurantId);

        // 시간대별로 그룹화된 예약 정보 생성
        Map<String, List<HashMap<String, Object>>> groupedBookings = groupBookingsByTime(getBookingStatusForCalendar);
        model.addAttribute("groupedBookings", groupedBookings);

        return "views/mypage/viewAllReservations";
    }

    private Map<String, List<HashMap<String, Object>>> groupBookingsByTime(List<HashMap<String, Object>> bookings) {
        Map<String, List<HashMap<String, Object>>> groupedBookings = new LinkedHashMap<>();

        for (HashMap<String, Object> booking : bookings) {
            String reservationTime = (String) booking.get("reservationDate");
            String timeSlot = getTimeSlot(reservationTime);

            // 해당 시간대로 예약 정보를 그룹화
            groupedBookings.computeIfAbsent(timeSlot, k -> new ArrayList<>()).add(booking);

            System.out.println("Grouped bookings: " + groupedBookings);

        }

        return groupedBookings;
    }

    private String getTimeSlot(String reservationTime) {
        // 시간대 구하기
        return reservationTime.substring(0, 2) + "시 - " + (Integer.parseInt(reservationTime.substring(0, 2)) + 1) + "시";
    }


}