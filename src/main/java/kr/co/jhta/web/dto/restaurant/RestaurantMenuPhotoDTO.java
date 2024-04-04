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
public class RestaurantMenuPhotoDTO {
    private Long restaurantMenuPhotoId;
    private String restaurantMenuPhoto;
    private LocalDate restaurantMenuPhotoCDate;
    private LocalDate restaurantMenuMDate;
    private Long restaurantMenuId;

}
