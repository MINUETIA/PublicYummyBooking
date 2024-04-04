package kr.co.jhta.web.dto.theme;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RestaurantThemeDTO {

    private Long restaurantThemeId;
    private Long themeId;
    private Long restaurantId;

}
