package kr.co.jhta.web.dto.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SuperCategory {
    private Long superId;
    private int superCode;
    private String superName;
}
