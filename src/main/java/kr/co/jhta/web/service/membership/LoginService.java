package kr.co.jhta.web.service.membership;

import kr.co.jhta.web.dao.membership.UbCommonDAO;
import kr.co.jhta.web.dto.Coordinates;
import kr.co.jhta.web.dto.userBusiness.UbCommonDTO;
import kr.co.jhta.web.service.AddressService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class LoginService {
    @Autowired
    UbCommonDAO ubCommonDAO;
    private final AddressService addressService;

    public LoginService(AddressService addressService) {
        this.addressService = addressService;
    }

    public void signup(@ModelAttribute UbCommonDTO ubCommonDTO){
        ubCommonDAO.signup(ubCommonDTO);
    }

    public UbCommonDTO findByUserId(String userId) {
        return ubCommonDAO.findByUserId(userId);
    }
    public UbCommonDTO findByUbId(Long ubId) {
        return ubCommonDAO.findByUbId(ubId);
    }

    public UbCommonDTO findByUserIdAndPw(String userId, String userPw) {
        return ubCommonDAO.findByUserIdAndPw(userId, userPw);
    }

    public void signupAll(@ModelAttribute UbCommonDTO dto, @RequestParam String addressDetail) throws JSONException {
        Coordinates coordinate = addressService.getCoordinate(dto.getAddress());

        dto.setAddress(dto.getAddress() + ", " + addressDetail);
        dto.setLatitude(Double.parseDouble(coordinate.getY())); // 위도
        dto.setLongitude(Double.parseDouble(coordinate.getX())); // 경도

        if ( dto.getClassificationNumber() == 3 ) {
            dto.setSecurityRole("USER");
            dto.setBusinessAddCheck(0);
        } else if ( dto.getClassificationNumber() == 4 ) {
            dto.setSecurityRole("BUSINESS");
            dto.setBusinessAddCheck(2);
        }

        ubCommonDAO.signupAll(dto);
    }

    public void updateUserDetail(@ModelAttribute UbCommonDTO dto, @RequestParam String addressDetail) throws JSONException {

        Coordinates coordinate = addressService.getCoordinate(dto.getAddress());

        dto.setAddress(dto.getAddress() + ", " + addressDetail);
        dto.setLatitude(Double.parseDouble(coordinate.getY())); // 위도
        dto.setLongitude(Double.parseDouble(coordinate.getX())); // 경도

        if ( dto.getClassificationNumber() == 3 ) {
            dto.setSecurityRole("USER");
            dto.setBusinessAddCheck(0);
        } else if ( dto.getClassificationNumber() == 4 ) {
            dto.setSecurityRole("BUSINESS");
            dto.setBusinessAddCheck(2);
        }

        ubCommonDAO.updateUserDetail(dto);
    }

    public void userInfoModify(@ModelAttribute UbCommonDTO dto, @RequestParam String addressDetail) throws JSONException {
        Coordinates coordinate = addressService.getCoordinate(dto.getAddress());

        dto.setAddress(dto.getAddress() + ", " + addressDetail);
        dto.setLatitude(Double.parseDouble(coordinate.getY())); // 위도
        dto.setLongitude(Double.parseDouble(coordinate.getX())); // 경도

        ubCommonDAO.modify(dto);

    }
}
