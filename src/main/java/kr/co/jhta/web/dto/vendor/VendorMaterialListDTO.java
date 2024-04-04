package kr.co.jhta.web.dto.vendor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VendorMaterialListDTO {

    private Long vendorMaterialListId;
    private String vendorMaterialName;
    private LocalDateTime vendorMaterialExpirationDate;
    private Long vendorId;
}
