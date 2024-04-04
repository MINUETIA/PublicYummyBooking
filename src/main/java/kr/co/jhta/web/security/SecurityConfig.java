package kr.co.jhta.web.security;

import kr.co.jhta.web.service.security.CustomOAuth2UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    // 암호화 관련 bean
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception{
        log.info(" ------------- admin 시큐리티 적용 ------------------- ");

        // 스프링 6부터 람다식으로 사용 - 순서 중요!! 섞이면 이상해짐
        http.authorizeHttpRequests((auth) -> auth

                        // 시큐리티 제외 페이지
                        .requestMatchers("/admin/login","/admin/purchase/list","/admin/**","/**","/login","/login/**","/admin/join","/admin/checkId", "/fonts/**", "/admin/","/css/**", "/img/**", "/js/**").permitAll()

                        // admin페이지 권한부여
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // 그 외에는 인증받은 사람만 가능하게 해줘
                        .anyRequest().permitAll()
        );

        //사이트 위 변조 방지기능
        http.csrf((auth) -> auth.disable()); // csrf 사용안함
        http.formLogin((auth) -> auth

                // 커스텀 로그인 페이지 사용하기
                .loginPage("/admin/login")

                // username의 값을 ad_id로 변경해줌
                .usernameParameter("ad_id")
                .passwordParameter("ad_pw")

                // 로그인 페이지에서 submit하면 처리하는 부분
                .defaultSuccessUrl("/admin/management/info")
        );

        // adminLogout 설정 - adminController에서 endpoint 설정해줌
        http.logout((logout) -> logout.logoutUrl("/admin/logout") // 로그아웃 요청 URL 설정, 기본값은 "/logout"
                .logoutSuccessUrl("/admin/login") // 로그아웃 성공 시 이동할 URL
                .invalidateHttpSession(true) // HTTP 세션 무효화 여부
                .deleteCookies("JSESSIONID") // 로그아웃 시 삭제할 쿠키 설정, 여러 개일 경우 여러 번 호출
                .permitAll()// 로그아웃 페이지 접근 권한 설정
        );

        return http.build();
    }

    @Order(0)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info(" ------------- user 시큐리티 적용 ------------------- ");

        // 스프링 6부터 람다식으로 사용 - 순서 중요!! 섞이면 이상해짐
        http.authorizeHttpRequests( (auth) -> auth

                        // permitAll 은 로그인 없이 접근할 수 있도록 세팅
                        .requestMatchers("/**").permitAll()

        ); // 나머지는 로그인 후 접근 가능
        http.oauth2Login( (oauth2) -> oauth2
                .loginPage("/login")
                .userInfoEndpoint( (userInfoEndpointConfig) -> userInfoEndpointConfig.userService(customOAuth2UserService) )
        );

        //사이트 위 변조 방지기능 ---> 스프링 6부터 람다식으로 사용해야됨
        http.csrf((auth) -> auth.disable()); // csrf 사용안함

        http.formLogin( (login) -> login.loginPage("/login") // login 페이지를 커스터마이징하여 로그인 체크를 할 때 해당 url을 타도록 세팅
                .defaultSuccessUrl("/",true) // 로그인 성공시 /로 페이지 이동
                .permitAll()
        );
        http.logout((logout) -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll());


        http.httpBasic( (basic) -> basic.disable() );

        return http.build();
    }
}
