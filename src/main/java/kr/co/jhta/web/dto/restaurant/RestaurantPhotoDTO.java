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
public class RestaurantPhotoDTO {
    private Long restaurantPhotoId;
    private String restaurantPhoto;
    private LocalDate restaurantPhotoCDate;
    private LocalDate restaurantPhotoMDate;
    private Long restaurantId;

}
