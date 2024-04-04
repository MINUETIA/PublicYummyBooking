package kr.co.jhta.web.file;

import lombok.Builder;
import lombok.Getter;

// https://congsong.tistory.com/39 참고
// 다음은 업로드된 파일 정보를 저장(INSERT)하는 용도로 사용할 파일 요청 클래스입니다.
// 물리적인 파일은 디스크에 저장되기 때문에, 파일의 부가적인 정보만 DB에 저장해 주면 됩니다.

// 게시글을 저장하면 PostController의 savePost( )가 실행되고,
// FileUtils로 사용자가 업로드한 파일(MultipartFile)을 전달하려면,
// 가장 먼저 PostController의 savePost( )에서 파라미터를 수집해야 합니다.
// 컨트롤러 메서드에 파라미터를 추가해도 되지만,
// 요청 클래스에서 한꺼번에 처리하는 게 가독성이 좋을 듯합니다.
// PostRequest에 멤버 files를 추가해 주세요.
// files에 담기는 정보는 화면 처리를 끝낸 후에 보여드리도록 하겠습니다.


@Getter
public class FileRequest {
    private Long fileId;
    private Long ubId; // 프로필 사진 구분을 위한 개인 사용자 아이디 구분
    private String originalFileName; // 원본 파일 명
    private String saveFileName; // 저장한 파일 명
    private Long size; // 파일 크기

    @Builder
    public FileRequest (String originalFileName, String saveFileName, Long size) {
        this.originalFileName = originalFileName;
        this.saveFileName = saveFileName;
        this.size = size;
    }
    //생성자 메서드에 @Builder 어노테이션이 선언되어 있는데요.
    // @Builder는 롬복에서 제공해주는 기능으로,
    // 빌더 패턴(Builder pattern)으로 객체를 생성할 수 있게 해줍니다.
    // 빌더 패턴은 생성자 파라미터가 많은 경우에 가독성을 높여주기도 하고,
    // 아래 코드와 같이 변수에 값을 넣어주는 순서를 달리하거나,
    // 원하는 변수에만 값을 넣어 객체를 생성할 수 있습니다.


    public void setUbId(Long ubId) {
        this.ubId = ubId;
    }
    // 파일은 게시글이 생성(INSERT) 된 후에 처리되어야 합니다.
    // 해당 메서드는 생성된 사용자 ID를 파일 요청 객체의 ubId에 저장하는 용도로 사용되는데요.
    // 객체 생성 시점에 같이 처리하지 않고 set 메서드를 이용해서 처리하는 이유는 뒤에서 설명드리도록 하겠습니다.

}

