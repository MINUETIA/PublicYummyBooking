package kr.co.jhta.web.dto.userBusiness;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UbCommonDTO {
    private Long ubId;
    private String userId;
    private String userPw;
    private String email;
    private String name;
    private LocalDate birthdate;
    private String phone;
    private int classificationNumber;
    private String securityRole;
    private String profilePhoto;
    private int uAgreement;
    private String introduction;
    private String customerRating;
    private int followOnOff;
    private String address;
    private LocalDateTime joinDate;
    private LocalDateTime modifyDate;
    private double latitude;
    private double longitude;
    private int businessAddCheck;
}
