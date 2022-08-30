package com.song.mingblog.config;

import com.song.mingblog.handler.CustomAuthenticationEntryPoint;
import com.song.mingblog.handler.LoginFailureHandler;
import com.song.mingblog.handler.LoginSuccessHandler;
import com.song.mingblog.handler.WebAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author Minkyu
 * Date : 2022-07-15
 * Time :
 * Remark : 기본 시큐리티셋팅
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final WebAccessDeniedHandler webAccessDeniedHandler;

    @Autowired
    public SecurityConfig(CustomAuthenticationEntryPoint customAuthenticationEntryPoint, WebAccessDeniedHandler webAccessDeniedHandler){
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.webAccessDeniedHandler = webAccessDeniedHandler;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/favicon.ico","/swagger*/**","/v2/api-docs","/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .csrf().disable() // csrf 보안 설정
//                .httpBasic().disable() // rest api 만을 고려

                // 스프링에서 '로그인하지 않은 사용자'가 아닌 '익명 사용자'로 판단하기 때문에 Forbidden이 발생
//                .anonymous().disable() // // anonymous를 off시켜야 Unauthorize Exception이 잘 작동한다.

                .authorizeRequests()

                .antMatchers("/assets/**","/login","/logout","/error/**").permitAll()
                .antMatchers("/admin/**","/api/account/**").hasRole("ADMIN")
                .antMatchers("/user/**","/api/notice/**").hasAnyRole("ADMIN","USER")
                .anyRequest()
                .authenticated()

                .and()

                .formLogin() // 로그인하는 경우에 대해 설정
                .loginPage("/login") // 로그인 페이지 URL 설정
                .successHandler(successHandler()) // 로그인 성공 후 설정
                .failureHandler(failureHandler()) // 로그인 실패시 설정

                .and()

                .logout() // 로그아웃 관련 처리
                .logoutSuccessUrl("/login") // 로그아웃 성공 후 이동할 URL 설정
                .deleteCookies("JSESSIONID") // 로그아웃 후 쿠기 삭제 설정
                .invalidateHttpSession(true) // 세션 날리기

                .and()
                .exceptionHandling()
                .accessDeniedHandler(webAccessDeniedHandler) // 권한없는 사용자가 접근시 막아주는 핸들러
                .authenticationEntryPoint(customAuthenticationEntryPoint) // 인증되지 않은 사용자가 접근시 막아주는 핸들러
        ;
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new LoginSuccessHandler("/loginActive");
    }

    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return new LoginFailureHandler();
    }

}
