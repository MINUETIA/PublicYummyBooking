package kr.co.jhta.web.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AdminAccountDTO {
    private Long adminId;
    private String adId;
    private String adPw;
    private String adSecurityRole;
    private int adClassificationNumber;
    private LocalDateTime adCDate;
    private LocalDateTime adMDate;
    private String email;
}
