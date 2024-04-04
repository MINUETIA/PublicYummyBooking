package kr.co.jhta.web.dto.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BaseCategoryDTO {
    private Long baseId;
    private int baseCode;
    private String baseName;
    private Long superId;
}
