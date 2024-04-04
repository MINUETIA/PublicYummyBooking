package kr.co.jhta.web.dto.vendor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class VendorMaterialDetailDTO {

    private Long vendorMaterialDetailId;
    private LocalDateTime vendorMaterialProductionDate;
    private Long vendorMaterialListId;

}
