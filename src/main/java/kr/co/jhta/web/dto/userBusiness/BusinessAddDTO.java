package kr.co.jhta.web.dto.userBusiness;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessAddDTO {
    private Long businessId;
    private Long ubId;
    private String businessName;
    private String businessCompanyRegistrationNum;
    private String zoneCode;
    private String businessAddress;
    private String businessType;
    private String businessTel;
    private String businessDescription;
    private String businessInfo;

    // 파일이 한개인지 여러개인지 모르니까 list형으로
    private List<MultipartFile> businessContractFile1;

    // 이 형태로 만들면 img 딱 4개만 들어감
    private String businessContractFile2;
    private String businessContractFile3;
    private String businessContractFile4;
    private String businessContractFile5;

    private int businessContractPeriod;
    private LocalDate businessContractStartDate;
    private String businessEtc;
    private LocalDateTime businessCDate;
    private LocalDateTime businessMDate;
    private int businessStatus;
}
