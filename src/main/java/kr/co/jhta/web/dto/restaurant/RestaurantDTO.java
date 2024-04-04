package kr.co.jhta.web.dto.restaurant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RestaurantDTO {

    private Long restaurantId;
    private String restaurantName;
    private int restaurantBranchNum;
    private String restaurantManagerName;
    private String restaurantUserId;
    private String restaurantPw;
    private String restaurantDescription;
    private String restaurantManagerHp;
    private int restaurantDepositCode;
    private int restaurantWaitingCode;
    private String restaurantAddress;
    private double restaurantLatitude;
    private double restaurantLongitude;
    private Date restaurantRegdate;
    private String restaurantMainPhoto;
    private Long ubId;


}
