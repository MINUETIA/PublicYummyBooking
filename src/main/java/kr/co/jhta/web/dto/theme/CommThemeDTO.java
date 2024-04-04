package kr.co.jhta.web.dto.theme;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CommThemeDTO {
    private Long themeId;
    private String themeName;
    private int themeClassifyCode;
    private LocalDateTime themeCDate;
}
