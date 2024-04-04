package kr.co.jhta.web.dto.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvaluationDTO {

    private Long restaurantId;
    private String restaurantName;
    private double avgScore;
    private int reviewCount;
    private double reviewService;
    private double reviewTeast;
    private double reviewMood;
}
