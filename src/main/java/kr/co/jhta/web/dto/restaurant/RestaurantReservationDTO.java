package kr.co.jhta.web.dto.restaurant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RestaurantReservationDTO {

    private Long restaurantReserveId;
    private LocalDate reserveTime;
    private int reservationInfoTableFor2;
    private int reservationInfoTableFor4;
    private int reservationInfoTableFor8;
    private int reservationInfoStatus;
    private Long restaurantId;




}
