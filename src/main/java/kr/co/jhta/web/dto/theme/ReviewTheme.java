package kr.co.jhta.web.dto.theme;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewTheme {
    private Long reviewThemeId;
    private Long themeId;
    private Long reviewId;
}
