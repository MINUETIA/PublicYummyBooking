package kr.co.jhta.web.dto.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

    private Long reviewId;
    private int reviewService;
    private int reviewTeast;
    private int reviewMood;
    private String reviewDescription;
    private int reviewRevisit;
    private LocalDateTime reviewCDate;
    private LocalDateTime reviewMDate;
    private Long id;
    private Long restaurantId;

}
