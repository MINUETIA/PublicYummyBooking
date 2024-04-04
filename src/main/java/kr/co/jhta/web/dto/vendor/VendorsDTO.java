package kr.co.jhta.web.dto.vendor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VendorsDTO {
    private Long vendorId;
    private String vendorName;
    private String vendorAddress;
    private String vendorTel;
    private String vendorManager;
    private String vendorHp;
    private String materialType;
}
