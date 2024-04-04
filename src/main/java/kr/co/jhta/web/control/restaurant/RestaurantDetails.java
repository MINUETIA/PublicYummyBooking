package kr.co.jhta.web.control.restaurant;

import kr.co.jhta.web.dto.RestrantByTheme;
import kr.co.jhta.web.dto.reservation.ReservationDTO;
import kr.co.jhta.web.dto.restaurant.RestaurantDTO;
import kr.co.jhta.web.dto.restaurant.RestaurantMenuDTO;
import kr.co.jhta.web.dto.restaurant.RestaurantOpeningDTO;
import kr.co.jhta.web.dto.review.EvaluationDTO;
import kr.co.jhta.web.service.restaurant.RestaurantDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Controller
public class RestaurantDetails {
    @Autowired
    RestaurantDetailsService service;
    @GetMapping("/restaurantDetails")
    public String restaurantDetails(@RequestParam int restaurantId , Model model){
        RestaurantDTO dto = service.RestaurantOne(restaurantId);
        List<RestaurantMenuDTO> list = service.getMenu(restaurantId);
        EvaluationDTO star = service.getStarRating(restaurantId);
        List<RestaurantOpeningDTO> operatingTime = service.getOperatingTime(restaurantId);
        List<RestrantByTheme> theme = service.restaurantTheme(restaurantId);

        model.addAttribute("dto", dto);
        model.addAttribute("list", list);
        model.addAttribute("star", star);
        model.addAttribute("operatingTime", operatingTime);
        model.addAttribute("theme", theme);

        return "views/restaurant/restaurantDetails";
    }
    @RequestMapping("/reservation")
    public String reservation(@RequestParam Long ubId ,@RequestParam String datepicker, @RequestParam String timepicker, @RequestParam int guests, @RequestParam Long restaurantId, @RequestParam String request){

        String reservationTime = datepicker+" "+timepicker+":00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 문자열 -> Date
        LocalDateTime date = LocalDateTime.parse(reservationTime, formatter);

        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setReservationDate(date);
        reservationDTO.setReservationNum(guests);
        reservationDTO.setRestaurantId(restaurantId);
        reservationDTO.setReservationRequest(request);
        reservationDTO.setUbId(ubId);

        service.reservation(reservationDTO);

        return "redirect:/mypage";
    }

}
