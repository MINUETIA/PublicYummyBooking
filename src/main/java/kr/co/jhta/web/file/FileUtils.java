package kr.co.jhta.web.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class FileUtils {

    // 리눅스에 파일을 저장할 위치를 의미합니다.
    private static final String uploadPath = Paths.get("/var/upload").toString();

    //다중 파일 업로드
    public List<FileRequest> uploadFiles(final List<MultipartFile> multipartFiles) {
        List<FileRequest> files = new ArrayList<>();
        for ( MultipartFile multipartFile : multipartFiles ) {
            if ( multipartFile.isEmpty() ) continue;
            files.add(uploadFiles(multipartFile));
        }
        return files;
    } // uploadFiles() end

    // 단일(1개) 파일을 업로드합니다.
    private FileRequest uploadFiles(final MultipartFile multipartFile) {
        if ( multipartFile.isEmpty() ) return null;

        String saveName = generateSaveFilename(multipartFile.getOriginalFilename());
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd")).toString();
        String uploadPath = getUploadPath() + File.separator + saveName;
        File uploadFile = new File(uploadPath);

        try {
            multipartFile.transferTo(uploadFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return FileRequest.builder()
                .originalFileName(multipartFile.getOriginalFilename())
                .saveFileName(saveName)
                .size(multipartFile.getSize())
                .build();
    } // uploadFiles() end


    // 저장 파일명 생성
    private String generateSaveFilename(String originalFilename) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String extension = StringUtils.getFilenameExtension(originalFilename);
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd")).toString();
        return uuid + "_" + originalFilename;
    } // generateSaveFilename() end

    //업로드 경로 반환
    private String getUploadPath() {
        return makeDirectories(uploadPath);
    } // getUploadPath() end

    //업로드 경로 반환
    private String getUploadPath(final String addPath) {
        return makeDirectories(uploadPath + File.separator + addPath);
    } // getUploadPath() end

    // 업로드 폴더 ( Directory ) 생성
    private String makeDirectories(final String path) {
        File dir = new File(path);
        if ( dir.exists() == false ) dir.mkdir();
        return dir.getPath();
    } // makeDirectories() end

    public static ResponseEntity<byte[]> getFile(String fileName) {

        ResponseEntity<byte[]> result;

        try {

            String srcFileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
            File file = new File(uploadPath + File.separator + srcFileName);
            HttpHeaders header = new HttpHeaders();

            // MIME 타입 처리
            header.add("Content-Type", Files.probeContentType(file.toPath()));

            // 파일 데이터 처리
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return result;
    }

}