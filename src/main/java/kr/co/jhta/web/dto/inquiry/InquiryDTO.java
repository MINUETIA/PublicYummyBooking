package kr.co.jhta.web.dto.inquiry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InquiryDTO {
    private Long inquiryId;
    private int inquiryCategory;
    private String inquiryDetails;
    private int isAnswered;
    private String inquiryResponseDetails;
    private LocalDateTime inquiryDate;
    private LocalDateTime inquiryResponseDate;
    private Long ubId;
    private Long adminId;
    private Long reservationId;
    private Long productId;
    private Long saleId;
}
