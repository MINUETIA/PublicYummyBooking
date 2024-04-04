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
public class UserFollowDTO {
    private Long userFollowId;
    private LocalDateTime followCDate;
    private Long followingId;
    private Long followedId;
}
