package kr.co.jhta.web.service.security;

import kr.co.jhta.web.dao.membership.UbCommonDAO;
import kr.co.jhta.web.dto.userBusiness.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UbCommonDAO ubCommonDAO;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("CustomOAuth2UserService oAuth2User : " + oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        System.out.println("CustomOAuth2UserService registrationId : " + registrationId);

        OAuth2Response oAuth2Response = null;

        if ( registrationId.equals("naver") ) {
            System.out.println("CustomOAuth2UserService 네이버 로그인 됨");
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        } else if ( registrationId.equals("google") ) {
            System.out.println("CustomOAuth2UserService 구글 로그인 됨");
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else {
            System.out.println("CustomOAuth2UserService 네이버, 구글만 로그인 구현");
            return null;
        }

        String userId = oAuth2Response.getProvider() + "_" + oAuth2Response.getProviderId();
        System.out.println("CustomOAuth2UserService userId : " + userId);

        UbCommonDTO ubCommonDTO = ubCommonDAO.findByUserId(userId);

        String role = null;

        if ( ubCommonDTO == null ) {
            UbCommonDTO ubCommonDTO1 = UbCommonDTO.builder()
                    .userId(userId).email(oAuth2Response.getEmail())
                    .build();
            ubCommonDAO.join(ubCommonDTO1);
//            req.getSession().setAttribute("ubCommonDTOsignup",ubCommonDTO1);
        }  else {
            role = ubCommonDTO.getSecurityRole();
        }
        return new CustomOAuth2User(oAuth2Response, role);
    }
}
