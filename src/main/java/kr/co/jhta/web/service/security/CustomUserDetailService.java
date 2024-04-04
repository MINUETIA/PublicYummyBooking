package kr.co.jhta.web.service.security;

import kr.co.jhta.web.dao.admin.AdminDAO;
import kr.co.jhta.web.dto.admin.AdminAccountDTO;
import kr.co.jhta.web.dto.security.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private AdminDAO adminDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AdminAccountDTO adminAccountDTO = adminDAO.findOne(username);

        if(adminAccountDTO == null){
            throw new UsernameNotFoundException(username);
        }
        else {
            return  new CustomUserDetails(adminAccountDTO);
        }

    }
}
