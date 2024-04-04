package kr.co.jhta.web.file;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UbCommonRequest {
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
    private List<MultipartFile> files = new ArrayList<>(); // 첨부파일 리스트
}
