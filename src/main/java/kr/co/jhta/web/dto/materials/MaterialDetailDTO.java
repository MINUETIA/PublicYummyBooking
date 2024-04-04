package kr.co.jhta.web.dto.materials;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MaterialDetailDTO {

    private Long materialDetailId;
    private Long productDetailId;
    private Long vendor_materialDetailId;
    private Long orderDetailId;

}
