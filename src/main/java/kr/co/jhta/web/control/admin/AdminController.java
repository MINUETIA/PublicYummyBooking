package kr.co.jhta.web.control.admin;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.co.jhta.web.dto.admin.AdminAccountDTO;
import kr.co.jhta.web.dto.userBusiness.BusinessAddDTO;
import kr.co.jhta.web.emailCertification.RegisterMail;
import kr.co.jhta.web.file.FileService;
import kr.co.jhta.web.service.admin.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {


    //@Value("d:/dev/upload")
    @Value("${kr.co.jhta.upload.path}") //---> properties에 적은 환경변수 주소, 우분투 배포시 여기만 변경하면 됨
    String uploadPath;

    @Autowired
    AdminService adminService;

    @Autowired
    RegisterMail registerMail;

    @Autowired
    FileService fileService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 로그인 - 로그인 한 상태면 home으로 아니면 로그인 화면으로
    @GetMapping("/login")
    public String adminLogin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken){
            return "views/admin/adminLogin/login";
        }
        return "redirect:/admin/management/info";
    }

    // 로그인 성공시
    @PostMapping("/loginOk")
    public String login(@RequestParam String ad_id, @RequestParam String ad_pw,
                        HttpSession session, Model model, HttpServletRequest request, HttpServletResponse response){

        // 저장된 비밀번호 가져오기
        AdminAccountDTO adminAccountDTO = adminService.findOne(ad_id);
        System.out.println(adminAccountDTO.getAdminId());

        // 입력된 패스워드와 암호화된 패스워드가 맞는지 확인
        if(passwordEncoder.matches(ad_pw, adminAccountDTO.getAdPw())){
            // 맞는경우 세션에 값 저장

            // 세션 생성
            session = request.getSession();

            //세션에 로그인 회원정보 보관
            session.setAttribute("dto", adminAccountDTO);
            System.out.println("adminAccountDTO2>>>>> "+ adminAccountDTO.getAdPw());

            // 쿠키에 시간 정보를 주지 않으면 세션 쿠키(브라우저 종료시 모두 종료)
            Cookie idCookie = new Cookie("memberId", String.valueOf(adminAccountDTO.getAdId()));
            response.addCookie(idCookie);

            // 세션 유지시간 무한대 설정
            session.setMaxInactiveInterval(-1);

            log.info("sessionId={}", session.getId());
            log.info("maxInactiveInterval={}", session.getMaxInactiveInterval());
            log.info("creationTime={}", new Date(session.getCreationTime()));
            log.info("lastAccessedTime={}", new Date(session.getLastAccessedTime()));
            log.info("isNew={}", session.isNew());

            // admin 메인페이지로
            return "redirect:/admin/management/info";
        }
        else{
            // 아니면
            return "views/admin/adminLogin/login";
        }
    }

    @GetMapping("/logout")
    public String performLogout() {
        return "redirect:/admin/login";
    }

    // 가입
    @GetMapping("/join")
    public String adminJoin(){
        return "views/admin/adminJoin/Lv1Join";
    }

    // 가입완료
    @PostMapping("/join")
    public String adminjoinOk(@ModelAttribute AdminAccountDTO dto, @RequestParam String email1, @RequestParam String email2){
        dto.setEmail(email1+email2);
        System.out.println("dto>>>> " +dto);

        // 패스워드를 인코더를 통해 비밀번호를 암호화시킴
        String enPassword = passwordEncoder.encode(dto.getAdPw());
        dto.setAdPw(enPassword);

        adminService.addOne(dto);

        if(dto.getAdClassificationNumber() == 2){
            return "views/admin/franchisee/franchiseeList";
        }
        else{
            return "views/admin/adminLogin/login";
        }
    }

    // Lv2Join
    @GetMapping("/lv2Join")
    public String lv2Join(){
        return "views/admin/adminJoin/Lv2Join";
    }


    // 중복검사
    @PostMapping("/checkId")
    @ResponseBody
    public boolean checkId(@RequestParam String ad_id){
        boolean result = false;

        if ( adminService.findOne(ad_id) != null){ // 중복인 경우 false
            System.out.println("null 아님");
            return result;
        }
        else{ // 중복이 아닌경우 false
            System.out.println("null");
            result = true;
            return result;
        }
    }

    // 이메일 인증
    @PostMapping("/mailConfirm")
    @ResponseBody
    String mailConfirm(@RequestParam("email") String email) throws Exception {

        System.out.println("mailConfirm>>>>>> "+ email);

        String code = registerMail.sendSimpleMessage(email);
        System.out.println("인증코드 : " + code);
        return code;
    }

    // 계정 List
    @GetMapping("/accountList")
    public String accountList(Model model){

        List<AdminAccountDTO> list = adminService.selectAll();
        System.out.println(list);

        model.addAttribute("list", list);

        return "views/admin/account/accountList";
    }

    // 계정 삭제
    @PostMapping("/accountDelete")
    @ResponseBody
    public String accountDelete(@RequestParam int num){
        System.out.println("delete: "+num);
        adminService.deleteId(num);
        return "OK";
    }

    // 계정 검색
    @PostMapping("/searchAccount")
    @ResponseBody
    public List<AdminAccountDTO> searchAccount(@RequestParam String searchText, Model model){


        List<AdminAccountDTO> list = null;

        if(searchText == null){ // 검색어 미입력시
            list = adminService.selectAll();
        }
        else{ // 검색어 입력시
            list = adminService.searchAccount(searchText);
        }
        return list;
    }

    // classificationNumber로 사업자 정보 불러오기
    @GetMapping("/franchiseeList")
    public String adminHome(Model model){

        List<HashMap<String, Object>> list = adminService.classificationNumber3();
        
        // HashMap으로 처리하기 때문에 map 안에 dto가 없는경우 null값을 제외해고 출력시킴 null값을 넣기 위해서
        // list를 풀어서 그 안에 businessAdd테이블의 컬럼값이 없는 경우에 공백으로 처리
        // get으로 뺄때 dto랑 자료형이 맞아야 함, put할때 값 주기 싫으면 null로 주기 ---> HashMap이 object형이라서 null도 put 가능
        // int 컬럼인 경우에 put에 0을 넣었더니.. 에러나서 String으로 잡았더니 또 에러...
        // 그래서 Integer로 자료형 주고 put을 공백으로 줬는데 오류 없음
        // LocalDateTime도 똑같이 자료형 맞춰주고 put할때 LocalDateTime으로 하던지 null로 하면 됨
        list.stream().forEach(
                x -> {

                    String businessName = (String) x.get("businessName");
                    if (businessName == null) {
                        x.put("businessName", "");
                    }

                    String businessCompanyRegistrationNum = (String) x.get("businessCompanyRegistrationNum");
                    if (businessCompanyRegistrationNum == null) {
                        x.put("businessCompanyRegistrationNum", "");
                    }

                    String zoneCode = (String) x.get("zoneCode");
                    if (zoneCode == null) {
                        x.put("zoneCode", "");
                    }

                    String businessAddress = (String) x.get("businessAddress");
                    if (businessAddress == null) {
                        x.put("businessAddress", "");
                    }

                    String businessType = (String) x.get("businessType");
                    if (businessType == null) {
                        x.put("businessType", "");
                    }

                    String businessTel = (String) x.get("businessTel");
                    if (businessTel == null) {
                        x.put("businessTel", "");
                    }

                    String businessDescription = (String) x.get("businessDescription");
                    if (businessDescription == null) {
                        x.put("businessDescription", "");
                    }

                    String businessInfo = (String) x.get("businessInfo");
                    if (businessInfo == null) {
                        x.put("businessInfo", "");
                    }

                    String businessContractFile1 = (String) x.get("businessContractFile1");
                    if (businessContractFile1 == null) {
                        x.put("businessContractFile1", "");
                    }

                    String businessContractFile2 = (String) x.get("businessContractFile2");
                    if (businessContractFile2 == null) {
                        x.put("businessContractFile1", "");
                    }

                    String businessContractFile3 = (String) x.get("businessContractFile3");
                    if (businessContractFile3 == null) {
                        x.put("businessContractFile3", "");
                    }

                    String businessContractFile4 = (String) x.get("businessContractFile4");
                    if (businessContractFile4 == null) {
                        x.put("businessContractFile4", "");
                    }

                    String businessContractFile5 = (String) x.get("businessContractFile5");
                    if (businessContractFile5 == null) {
                        x.put("businessContractFile5", "");
                    }

                    Integer businessContractPeriod = (Integer) x.get("businessContractPeriod");
                    if (businessContractPeriod == null) {
                        x.put("businessContractPeriod", null);
                    }

                    LocalDateTime businessContractStartDate = (LocalDateTime) x.get("businessContractStartDate");
                    if (businessContractStartDate == null) {
                        x.put("businessContractStartDate", null);
                    }

                    String businessEtc = (String) x.get("businessEtc");
                    if (businessEtc == null) {
                        x.put("businessEtc", "");
                    }

                    LocalDateTime businessCDate = (LocalDateTime) x.get("businessCDate");
                    if (businessCDate == null) {
                        x.put("businessCDate", null);
                    }

                    LocalDateTime businessMDate = (LocalDateTime) x.get("businessMDate");
                    if (businessMDate == null) {
                        x.put("businessMDate", null);
                    }

                    Integer businessStatus = (Integer) x.get("businessStatus");
                    if (businessStatus == null) {
                        x.put("businessStatus", null);
                    }
                }

        );
        model.addAttribute("list", list);

        return "views/admin/franchisee/franchiseeList";
    }

    // ubid로 찾은 modal에 넣을값
    @PostMapping("/ubData")
    @ResponseBody
    public BusinessAddDTO ubData(@RequestParam Long ubId){
        System.out.println("ubID>>>>> " +ubId);

        BusinessAddDTO addDTO = adminService.findUbid(ubId);
        System.out.println("DTO>>> " + adminService.findUbid(ubId));

        return addDTO;
    }

    @PostMapping("/businessAdd")
    public String businessAdd(@ModelAttribute BusinessAddDTO addDTO, @RequestParam Long ubId, @RequestParam String address, @RequestParam String addressDetail, Model model){

        //addDTO.setUbId(ubId);
        addDTO.setBusinessAddress(address + "," +addressDetail);
        System.out.println("addDTO>>>> " +addDTO);
        System.out.println("파일저장위치 : " + uploadPath);

        // 비어있는 list 선언
        List<String> list = new ArrayList<>();
        
        // addDTO의 list형인 BusinessContractFile1의 값을 가져와서 list형인 files에 담아줌
        List<MultipartFile> files = addDTO.getBusinessContractFile1();
        
        // for문 돌려서 변환시키기
        for(MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
            System.out.println(originalFilename);

            String uuid = UUID.randomUUID().toString(); // 고유한 이름으로 변경
            System.out.println(uuid);
            Path savePath = Paths.get(uploadPath,
                    uuid + "_" + originalFilename);


            // uploadDetail에 저장 
            // - 이미지 파일을 여러개 넣으려면 따로 테이블을 생성해서 넣어도 되고 이미지파일 숫자가 정해져있으면 딱 그것만 넣어주면 됨 
            
            // 현재 로직에서는 이미지파일의 숫자가 정해져있어서 비어있는 list 변수에 값을 다 담아줌
            list.add(uuid + "_" + originalFilename);

            try {
                file.transferTo(savePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println(list);

        int num = list.size();
        System.out.println(num);

        // 정형화가 안되어서 로직이 안이쁘다ㅜ...
        int i = 0 ;

        // i가 list의 크기보다 작은 경우에만 담기
        if( i < num ) {
            i++;
            if (list.get(0) != null) addDTO.setBusinessContractFile2(list.get(0));
        }
        if(i < num){
            i++;
            if(list.get(1) != null) addDTO.setBusinessContractFile3(list.get(1));
        }
        if( i < num ){
            i++;
            if(list.get(2) != null) addDTO.setBusinessContractFile4(list.get(2));
        }
        if(i < num ){
            i++;
            if(list.get(3) != null) addDTO.setBusinessContractFile5(list.get(3));
        }

        // dto 저장
        adminService.businessAdd(addDTO);

        return "redirect:/admin/franchiseeList";
    }

    @GetMapping("/businessForm")
    public String businessForm(){
        return "views/admin/business/businessForm";
    }
}

