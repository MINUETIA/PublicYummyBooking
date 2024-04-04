package kr.co.jhta.web.dto.security;

import kr.co.jhta.web.dto.admin.AdminAccountDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private AdminAccountDTO adminAccountDTO;

    // alt + inser키 해당 클래스 생성자 생성 단축키, 초기화시킴
    public CustomUserDetails(AdminAccountDTO adminAccountDTO) {
        this.adminAccountDTO = adminAccountDTO;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        // 추가 - role을 찾아서 리턴해주는 메소드
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return adminAccountDTO.getAdSecurityRole();
            }
        });

        return collection; // 컬렉션 안의 role을 보내줌
    }

    @Override
    public String getPassword() {
        return adminAccountDTO.getAdPw();
    }

    @Override
    public String getUsername() {
        return adminAccountDTO.getAdId();
    }

    @Override
    public boolean isAccountNonExpired() { // 계정 만료됐어?
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // 잠겨있는 상태야?
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { // 자격이 만료되지 않았어?
        return true;
    }

    @Override
    public boolean isEnabled() { // 사용할 수 있는 상태 체크
        return true;
    }
}
