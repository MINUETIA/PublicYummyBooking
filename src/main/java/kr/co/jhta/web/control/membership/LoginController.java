package kr.co.jhta.web.control.membership;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.co.jhta.web.dto.userBusiness.UbCommonDTO;
import kr.co.jhta.web.file.FileRequest;
import kr.co.jhta.web.file.FileService;
import kr.co.jhta.web.file.FileUtils;
import kr.co.jhta.web.file.UbCommonRequest;
import kr.co.jhta.web.service.membership.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/login")
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    @Autowired
    LoginService loginService;
    private final FileService fileService;
    private final FileUtils fileUtils;

    @GetMapping("/signup")
    public String joinForm(){
        return "views/userBusiness/signup2.html";
    }

    @GetMapping("/login")
    public String loginForm() { return "views/userBusiness/login.html"; }

    @PostMapping("/login")
    public String loginOk(@ModelAttribute UbCommonDTO dto, HttpServletRequest req,Model model) {

        UbCommonDTO ubCommonDTO = loginService.findByUserIdAndPw(dto.getUserId(), dto.getUserPw());

        if ( ubCommonDTO == null ) {
            model.addAttribute("msg","아이디 또는 비밀번호가 존재하지 않습니다.");
            return "views/userBusiness/login.html";
        }

        req.getSession().setAttribute("ubCommonDTOLogin", ubCommonDTO);

        return "redirect:/home";
    }

    // 아이디, 이름, 비밀번호 입력 후 상세정보 입력 페이지로 이동
    @PostMapping("/signup")
    public String joinOk(@ModelAttribute UbCommonDTO ubCommonDTO, HttpServletRequest req) {

        req.getSession().setAttribute("ubCommonDTOsignup", ubCommonDTO);
        return "views/userBusiness/signupDetail.html";
    }

    @PostMapping("/signupDetail")
    public String joinDetailOk(@ModelAttribute UbCommonDTO dto, @RequestParam String addressDetail) throws JSONException {

        loginService.signupAll(dto, addressDetail);

        return "redirect:/login/login";
    }

    // 소셜로 처음 회원가입/로그인 했을 경우 개인/사업자 구분 저장을 하기 위해 상세정보 입력 페이지로 이동
    @PostMapping("/signupSocial")
    public String socialDetailOk(@ModelAttribute UbCommonDTO dto, @RequestParam String addressDetail) throws JSONException {

        UbCommonDTO ubCommonDTO = loginService.findByUserId(dto.getUserId());
        dto.setEmail(ubCommonDTO.getEmail());

        loginService.updateUserDetail(dto, addressDetail);

        return "redirect:/home";
    }

    @GetMapping("/modify")
    public String ubCommonModify(@RequestParam Long ubId, Model model) {

        UbCommonDTO dto = loginService.findByUbId(ubId);

        String[] address = dto.getAddress().split(",");

        model.addAttribute("dtoForModify", dto);
        model.addAttribute("address", address[0]);
        if ( address.length > 1 ) model.addAttribute("addressDetail", address[1]);

        return "views/userBusiness/userModify.html";
    }

    @PostMapping("/modify")
    public String ubCommonModifyOk(@ModelAttribute UbCommonDTO dto, final UbCommonRequest params, @RequestParam String addressDetail, HttpSession session) throws JSONException {

        Long ubId = dto.getUbId();
        List<FileRequest> files = fileUtils.uploadFiles(params.getFiles());

        for ( FileRequest file : files ) {
            dto.setProfilePhoto(file.getSaveFileName());
        }

        loginService.userInfoModify(dto, addressDetail);
        fileService.saveFiles(ubId, files);

        session.setAttribute("ubCommonDTOLogin", loginService.findByUbId(dto.getUbId()));

        return "redirect:/mypage?ubId="+ubId;
    }

    //회원가입 - 아이디 중복체크
    @RequestMapping("/checkId")
    @ResponseBody
    public Map<Object, Object> checkId(@RequestParam String userId) {

        UbCommonDTO dto = loginService.findByUserId(userId);

        Map<Object, Object> map = new HashMap<>();

        if(dto == null) { // 아이디가 존재하지 않으면
            map.put("cnt", 0);
        }else { // 아이디가 존재하면
            map.put("cnt", 1);
        }

        return map;
    }

    @RequestMapping("/display")
    @ResponseBody
    public ResponseEntity<byte[]> getFile(String fileName) {
        return FileUtils.getFile(fileName);
    }
}
