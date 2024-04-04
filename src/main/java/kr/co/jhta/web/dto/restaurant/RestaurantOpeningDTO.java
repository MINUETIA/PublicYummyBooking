package kr.co.jhta.web.dto.restaurant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RestaurantOpeningDTO {

    private Long restaurantOpeningId;
    private Integer openingInfoWeek;
    private LocalTime openingInfoStartTime;
    private LocalTime openingInfoEndTime;
    private LocalTime openingInfoBreakStarTime;
    private LocalTime openingInfoBreakEndTime;
    private LocalTime openingInfoLastOrder;
    private int openingInfoStatus;
    private LocalDate openingInfoCDate;
    private LocalDate openingInfoMDate;
    private Long restaurantId;


}
