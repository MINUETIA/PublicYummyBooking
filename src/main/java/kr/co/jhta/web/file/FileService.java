package kr.co.jhta.web.file;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileMapper fileMapper;

    @Transactional
    public void saveFiles(final Long ubId, final List<FileRequest> files) {
        if (CollectionUtils.isEmpty(files)) return;
        for ( FileRequest file : files ) file.setUbId(ubId);
        fileMapper.saveAll(files);
    }
    /*
    게시글 번호(postId)와 파일 정보(files)를 전달받아,
    업로드된 파일 정보를 테이블에 저장하는 역할을 합니다.
    만약 게시글을 저장(INSERT 또는 UPDATE)하는 시점에 업로드된 파일이 없다면 로직을 종료하고,
    파일이 있으면 모든 요청 객체에 게시글 번호(postId)를 세팅한 후 테이블에 파일 정보를 저장합니다.
    */
}
