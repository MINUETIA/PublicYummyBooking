package kr.co.jhta.web.dao.membership;

import kr.co.jhta.web.dto.userBusiness.UbCommonDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UbCommonDAO {
    public UbCommonDTO findByUserId(String userId);
    public void join(UbCommonDTO dto);
    public void signup(UbCommonDTO dto);
    public void signupAll(UbCommonDTO dto);
    public void modify(UbCommonDTO dto);
    public UbCommonDTO findByUserIdAndPw(String userId, String userPw);
    public void updateUserDetail(UbCommonDTO dto);
    public UbCommonDTO findByUbId(Long ubId);
}
