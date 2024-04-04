package kr.co.jhta.web.file;

import lombok.Builder;
import lombok.Getter;

// 업로드된 파일 정보를 저장(INSERT)하는 용도로 사용할 파일 요청 클래스입니다.

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

    public void setUbId(Long ubId) {
        this.ubId = ubId;
    }

}

