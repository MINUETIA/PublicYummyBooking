package kr.co.jhta.web.service.admin;

import kr.co.jhta.web.dto.admin.AdminAccountDTO;
import kr.co.jhta.web.dto.userBusiness.BusinessAddDTO;

import java.util.HashMap;
import java.util.List;

public interface AdminService {

    // id중복검사 겸 로그인
    public AdminAccountDTO findOne(String username);

    // 계정 추가
    public void addOne(AdminAccountDTO dto);

    // 계정목록
    public List<AdminAccountDTO> selectAll();

    // 삭제
    public void deleteId(int num);

    // 검색
    public List<AdminAccountDTO> searchAccount(String searchText);

    // classificationNumber로 사업자 정보 불러오기
    public List<HashMap<String, Object>> classificationNumber3();

    // businessAdd insert
    public void businessAdd(BusinessAddDTO addDTO);

    public BusinessAddDTO findUbid(Long ubId);
}
