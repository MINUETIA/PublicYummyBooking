package kr.co.jhta.web.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Waiting {
    private Long waitingId;
    private LocalDateTime waitReceptionTiem;
    private int waitNum;
    private LocalDateTime waitEnterTime;
    private int waitStatus;
    private Long ubId;
    private Long restaurantId;
}
