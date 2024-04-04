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
public class RestaurantMenuDTO {

    private Long RestaurantMenuId;
    private String menuName;
    private int menuPrice;
    private String menuDescription;
    private LocalDate menuCDate;
    private LocalDate menuMDate;
    private Long RestaurantId;

}
