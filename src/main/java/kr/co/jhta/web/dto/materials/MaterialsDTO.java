package kr.co.jhta.web.dto.materials;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MaterialsDTO {

    private Long materialsId;
    private int materialReceivePrice;
    private int materialReceiveCnt;
    private Long productId;
    private Long vendorMaterialListId;
}
