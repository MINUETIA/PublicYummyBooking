package kr.co.jhta.web.service.admin;

import kr.co.jhta.web.dao.admin.AdminDAO;
import kr.co.jhta.web.dto.admin.AdminAccountDTO;
import kr.co.jhta.web.dto.userBusiness.BusinessAddDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final AdminDAO adminDAO;

    @Override
    public AdminAccountDTO findOne(String username) {
        return adminDAO.findOne(username);
    }

    @Override
    public void addOne(AdminAccountDTO dto) {
        adminDAO.addOne(dto);
    }

    @Override
    public List<AdminAccountDTO> selectAll() {
        return adminDAO.selectAll();
    }

    @Override
    public void deleteId(int num) {
        adminDAO.deleteId(num);
    }

    @Override
    public List<AdminAccountDTO> searchAccount(String searchText) {
        return adminDAO.searchAccount(searchText);
    }

    // classificationNumber로 사업자 정보 불러오기
    @Override
    public List<HashMap<String, Object>> classificationNumber3() {
        List<HashMap<String, Object>> list = adminDAO.classificationNumber3();

        int no = 0;
        for (HashMap<String,Object> x : list){
            x.put("num", ++no); // 넘버링 추가
        }

        System.out.println("serviceImpl : " + list);
        return list;
    }

    // businessAdd insert
    @Override
    public void businessAdd(BusinessAddDTO addDTO) {
        adminDAO.businessAdd(addDTO);
    }

    @Override
    public BusinessAddDTO findUbid(Long ubId) {
        return adminDAO.findUbid(ubId);
    }

}
