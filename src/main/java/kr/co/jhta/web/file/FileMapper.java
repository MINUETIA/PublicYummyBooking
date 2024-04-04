package kr.co.jhta.web.file;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FileMapper {
    /*
    파일 정보 저장
    @Param files - 파일 정보 리스트
    */
    public void saveAll ( List<FileRequest> files );
    // 업로드된 파일의 정보를 DB에 저장합니다. 여러 개의 파일 정보를 한 번에 저장하기 위해 FileRequest를 List 타입으로 선언했습니다.
}
