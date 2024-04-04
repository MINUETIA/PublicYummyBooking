package kr.co.jhta.web.dto.materials;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MeterialDisposeDTO {
    private Long orderDetailId;
    private Long disposeCnt;
    private LocalDateTime disposeDate;
}
