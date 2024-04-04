package kr.co.jhta.web.file;

import lombok.*;

import java.time.LocalDateTime;
// https://congsong.tistory.com/39 참고
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileTableDTO {
    private Long fileId;
    private Long ubId; // 프로필 사진 구분을 위한 개인 사용자 아이디 구분 fk
    private Long restaurantId; // 레스토랑 아이디 fk
    private Long restaurantMenuId; // 레스토랑 메뉴 아이디 fk
    private String originalFileName; // 원본 파일 명
    private String saveFileName; // 저장한 파일 명
    private Long size; // 파일 크기
    private int deleteYn; // 삭제 여부
    private LocalDateTime createdDate; // 생성일시
    private LocalDateTime deleteDate; // 삭제일시
}
