package kr.co.jhta.web.dto.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SubCategoryDTO {
    private Long subId;
    private int subCode;
    private String subName;
    private Long baseId;
}
