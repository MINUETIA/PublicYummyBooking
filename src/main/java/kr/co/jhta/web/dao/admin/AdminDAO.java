package kr.co.jhta.web.dao.admin;

import kr.co.jhta.web.dto.admin.AdminAccountDTO;
import kr.co.jhta.web.dto.userBusiness.BusinessAddDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;


@Mapper
@Repository
public interface AdminDAO {

    public AdminAccountDTO findOne(String username);

    public void addOne(AdminAccountDTO dto);

    public List<AdminAccountDTO> selectAll();

    public void deleteId(int num);

    public List<AdminAccountDTO> searchAccount(String searchText);

    // classificationNumber로 사업자만 불러오기
    public List<HashMap<String, Object>> classificationNumber3();

    // businessAdd insert
    public void businessAdd(BusinessAddDTO addDTO);

    public BusinessAddDTO findUbid(Long ubId);
}
