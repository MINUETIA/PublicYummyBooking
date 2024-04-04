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

/*
칼럼
설명
file_id
    테이블의 PK(Primary Key)를 의미합니다.
ub_id
    테이블의 FK(Foreign Key)로, 파일과 연결되는 사용자 번호를 의미합니다.
original_name
    업로드 하는 파일의 원본 이름을 의미합니다.
save_name
    실제로 디스크에 저장되는 파일명을 의미합니다.
size
    파일의 크기를 의미합니다.
delete_yn
    파일 삭제 여부를 의미합니다.
created_date
    파일 생성일시를 의미합니다.
deleted_date
    파일 삭제일시를 의미합니다.

원본 파일명과 저장 파일명을 따로 두는 이유
    동일한 경로에 동일한 이름의 파일이 업로드되는 경우,
    OS에 따라 파일이 저장되지 않거나 이름이 자동으로 바뀐 채 업로드되는 이슈가 있습니다.
    윈도우의 경우에는 파일명 뒤에 숫자가 붙게 되는데요.
    이러한 상황이 벌어지면 디스크에 업로드된 파일을 찾을 수 없게 됩니다.
    이와 같은 이슈를 방지하기 위해 원본 파일명(original_name)에는 업로드한 파일의 이름을 그대로 저장하고,
    저장 파일명(save_name)에는 흔히 UUID라고 부르는 고유 식별자를 저장해서,
    UUID를 기준으로 파일을 구분하게 됩니다.

최종 수정일시(modified_date)가 없는 이유
    게시판, 댓글, 회원과는 달리 파일 테이블에는 modified_date 칼럼이 없습니다.
    예를 들어 기존에 파일이 업로드된 게시글을 수정한다고 가정했을 때,
    파일이 추가/삭제되거나 기존 파일이 다른 파일로 변경되는 경우,
    기존에 등록된 모든 파일을 삭제 처리한 후 추가/삭제/변경된 파일을 새롭게 생성하는 방식을 이용하기 때문입니다.
    말로는 이해가 쉽지 않으실 수 있으니,
    실제로 파일을 처리하는 과정에서 다시 한번 설명드리겠습니다.


출처: https://congsong.tistory.com/39 [Let's develop:티스토리]
*/
