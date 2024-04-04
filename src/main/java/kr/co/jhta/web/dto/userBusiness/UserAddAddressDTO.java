package kr.co.jhta.web.dto.userBusiness;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAddAddressDTO {
    private Long addId;
    private String addressName;
    private String addAddress;
    private int addLatitude;
    private int addlongitude;
    private Long ubId;

}
