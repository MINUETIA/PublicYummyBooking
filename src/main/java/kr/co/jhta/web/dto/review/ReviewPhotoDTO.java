package kr.co.jhta.web.dto.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewPhotoDTO {
    private Long reviewPhotoId;
    private String reviewPhoto;
    private Long reviewId;
}
