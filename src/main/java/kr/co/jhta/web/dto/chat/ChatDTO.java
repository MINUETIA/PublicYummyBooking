package kr.co.jhta.web.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ChatDTO {
    private Long chatId;
    private String chatContnet;
    private LocalDateTime chatDate;
    private int chatStatus;
    private Long ubId;
    private Long restaurantId;
}
