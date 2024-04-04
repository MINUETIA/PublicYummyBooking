package kr.co.jhta.web.dto.userBusiness;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSelectDTO {
    private Long userSelectId;
    private LocalDateTime selectCDate;
    private Long ubId;
    private Long restaurantId;
}
