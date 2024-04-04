package kr.co.jhta.web.dto.theme;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class UserThemeDTO {
    private Long userThemeId;
    private Long themeId;
    private Long ubId;
}
