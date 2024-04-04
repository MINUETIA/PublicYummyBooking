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

/*
페이징과 마찬가지로 파일 업로드/다운로드도 모든 영역에서 공통으로 사용할 수 있어야 합니다.
이 클래스는 디스크에 디렉터리(폴더)를 생성하거나, 파일을 업로드 또는 삭제하는 용도로 사용되는 클래스입니다.
우선은 업로드에 필요한 메서드만 정의했습니다.
*/
@Component // @Bean은 개발자가 컨트롤 할 수 없는 외부 라이브러리를 빈으로 등록할 때 사용하고, @Component는 개발자가 직접 정의한 클래스를 빈으로 등록할 때 사용합니다.
@Slf4j
public class FileUtils {

    // @Value("${kr.co.jhta.upload.path}") ---> 아래쪽 method가 static이라서 사용x - 다 고쳐야됨
    // private String uploadPath;

    // local주소라서 리눅스 배포시 사용불가
    // private static final String uploadPath = Paths.get("D:", "dev", "upload").toString();

    // 리눅스 root파일로 imgUpdate 주소변경
    private static final String uploadPath = Paths.get("/var/upload").toString();
    // 물리적으로 파일을 저장할 위치를 의미합니다. 저는 윈도우 환경이기 때문에 D:\\dev\\upload를 기본 위치로 선언했습니다.
    // 경로는 여러분이 원하시는 곳으로 선언하셔도 무관합니다.
    // 보통 OS별 디렉터리 경로를 구분할 때 File.separator를 이용하고는 하는데요.
    // Paths.get( )을 이용하면 OS에 상관없이 디렉터리 경로를 구분할 수 있습니다.
    
    /*
    * 다중 파일 업로드
    * @param multipartFiles - 파일 객체 List
    * @return DB에 저장할 파일 정보 List
    * */
    public List<FileRequest> uploadFiles(final List<MultipartFile> multipartFiles) {
        List<FileRequest> files = new ArrayList<>();
        for ( MultipartFile multipartFile : multipartFiles ) {
            if ( multipartFile.isEmpty() ) continue;
            files.add(uploadFiles(multipartFile));
        }
        return files;
    } // uploadFiles() end
    // 스프링은 파일 업로드를 쉽게 처리할 수 있도록 MultipartFile 인터페이스를 제공해 줍니다.
    // 사용자가 화면에서 파일을 업로드한 후 폼을 전송하면,
    // MultipartFile 객체에 사용자가 업로드한 파일 정보가 담깁니다.
    // 이 메서드의 포인트는 변수 files에 uploadFile( )의 결괏값을 담아 리턴한다는 점인데요.
    // 이유는 바로 아래 행에서 설명드리겠습니다.

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

    // 단일(1개) 파일을 디스크에 업로드합니다.
    // MultipartFile의 isEmpty( )는 파일의 유무를 체크하는 함수로,
    // 업로드 된 파일이 없는 경우에는 null을 리턴해서 로직을 종료합니다.
    // 메인 로직의 각 변수는 디스크에 저장할 파일명(saveName), 오늘 날짜(today), 파일의 업로드 경로(uploadPath(디렉터리 + 파일명)), 업로드할 파일 객체(uploadFile)를 의미합니다.
    // 파일은 uploadPath에 해당되는 경로에 생성되며,
    // MultipartFile의 transferTo( )가 정상적으로 실행되면 파일 생성(write)이 완료됩니다.
    // 리턴하는 객체는 FileRequest 타입의 객체로, 앞에서 말씀드린 빌더 패턴이 적용된 코드입니다.
    // 결과적으로 해당 메서드가 리턴하는 객체에는 디스크에 생성된 파일 정보가 담기게 됩니다.


    /*
    저장 파일명 생성
    @param filename 원본 파일명
    @return 디스크에 저장할 파일명
    */
    private String generateSaveFilename(String originalFilename) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String extension = StringUtils.getFilenameExtension(originalFilename);
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd")).toString();
        return uuid + "_" + originalFilename;
    } // generateSaveFilename() end
    // uploadFile( )의 변수 saveName에서 호출하는 메서드입니다.
    // 변수 uuid에는 32자리의 랜덤 문자열을,
    // extension에는 업로드 한 파일의 확장자를 담아 (랜덤 문자열 + " . " + 파일 확장자)에 해당되는 파일명을 리턴합니다.
    // 이 파일명은 실제로 디스크에 생성되는 파일명을 의미합니다.

    /*
    업로드 경로 반환
    @return 업로드 경로
    */
    private String getUploadPath() {
        return makeDirectories(uploadPath);
    } // getUploadPath() end
    // 변수 uploadPath에 해당되는 경로를 리턴합니다.
    // addPath 파라미터가 선언된 getUploadPath( )는 uploadFile( )의 변수 uploadPath에서 호출하는 메서드로,
    // 당장은 기본 업로드 경로에 오늘 날짜(today)를 연결하는 용도로 사용됩니다.

    /*
    업로드 경로 반환
    @param addPath - 추가 경로
    @return 업로드 경로
    */
    private String getUploadPath(final String addPath) {
        return makeDirectories(uploadPath + File.separator + addPath);
    } // getUploadPath() end
    // 변수 uploadPath에 해당되는 경로를 리턴합니다.
    // addPath 파라미터가 선언된 getUploadPath( )는 uploadFile( )의 변수 uploadPath에서 호출하는 메서드로,
    // 당장은 기본 업로드 경로에 오늘 날짜(today)를 연결하는 용도로 사용됩니다.

    /*
    업로드 폴더 ( Directory ) 생성
    @param path - 업로드 경로
    @return 업로드 경로
    */
    private String makeDirectories(final String path) {
        File dir = new File(path);
        if ( dir.exists() == false ) dir.mkdir();
        return dir.getPath();
    } // makeDirectories() end
    // getUploadPath( )에서 호출하는 메서드입니다.
    // 디스크에 경로(path)에 해당되는 디렉터리(폴더)가 없으면,
    // path에 해당되는 모든 경로에 폴더를 생성합니다.
    // 예를 들어 path가 C:\a\b\c\d\e라고 가정하면, a~e까지의 모든 경로가 폴더로 생성됩니다.

    public static ResponseEntity<byte[]> getFile(String fileName) {

        ResponseEntity<byte[]> result;

        try {
            String srcFileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);

            log.info("fileName: " + srcFileName);


            File file = new File(uploadPath + File.separator + srcFileName);

            log.info("file: " + file);

            HttpHeaders header = new HttpHeaders();


            // MIME 타입 처리
            header.add("Content-Type", Files.probeContentType(file.toPath()));

            // 파일 데이터 처리
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);


        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        System.out.println(result);

        return result;
    }

}