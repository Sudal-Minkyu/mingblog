package com.song.mingblog.config;

import com.song.mingblog.jwt.handeler.UserCustomLoginFailHandler;
import com.song.mingblog.jwt.handeler.UsrCustomLoginSuccessHandler;
import com.song.mingblog.jwt.handeler.WebAccessDeniedHandler;
import com.song.mingblog.jwt.handeler.WebAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {

    // 권한이 없는 사용자 접근에 대한 handler
    private final WebAccessDeniedHandler webAccessDeniedHandler;

    // 인증되지 않은 사용자 접근에 대한 handler
    private final WebAuthenticationEntryPoint webAuthenticationEntryPoint;

    @Autowired
    public SecurityConfig(WebAccessDeniedHandler webAccessDeniedHandler, WebAuthenticationEntryPoint webAuthenticationEntryPoint){
        this.webAccessDeniedHandler = webAccessDeniedHandler;
        this.webAuthenticationEntryPoint = webAuthenticationEntryPoint;
    }

//    // 실제 인증을 담당하는 provider
//    @Bean
//    public CustomAuthenticationProvider customAuthenticationProvider() {
//        return new CustomAuthenticationProvider(passwordEncoder());
//    }

//    // 스프링 시큐리티가 사용자를 인증하는 방법이 담긴 객체
//    @Bean
//    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {
//        authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider());
//    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*");
    }

    @Bean
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/resources/**")
                .antMatchers("/css/**")
                .antMatchers("/js/**")
                .antMatchers("/favicon*/**")
                .antMatchers("/img/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    // 스프링 시큐리티 규칙
    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http.csrf().disable() // csrf 보안 설정 비활성화
                .httpBasic().disable() // rest api 만을 고려
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션은 사용하지 않음
                .and()

                // 스프링에서 '로그인하지 않은 사용자'가 아닌 '익명 사용자'로 판단하기 때문에 Forbidden이 발생
                .anonymous().disable() // anonymous를 off시켜야 Unauthorize Exception이 잘 작동한다.

                .antMatcher("/**").authorizeRequests() // 보호된 리소스 URI에 접근할 수 있는 권한 설정
                .antMatchers("/", "/error403", "/error404","/logout").permitAll() // 전체 접근 허용
                .antMatchers("/main").authenticated() // 인증된 사용자만 접근 허용
                .antMatchers("/mypage").hasRole("ADMIN") // ROLE_ADMIN 권한을 가진 사용자만 접근 허용
                .antMatchers("/check").hasAnyRole("ADMIN", "USER") // ROLE_ADMIN 혹은 ROLE_USER 권한을 가진 사용자만 접근 허용

                // 그 외 항목 전부 인증 적용
                .anyRequest()
                .authenticated()
                .and()

                 // exception 처리
                .exceptionHandling()
                .accessDeniedHandler(webAccessDeniedHandler) // 권한이 없는 사용자 접근 시
                .authenticationEntryPoint(webAuthenticationEntryPoint) // 인증되지 않은 사용자 접근 시
                .and()

                .formLogin() // 로그인하는 경우에 대해 설정
                .loginPage("/login") // 로그인 페이지 URL을 설정
                .successForwardUrl("/main") // 로그인 성공 후 이동할 URL 설정
                .failureForwardUrl("/login") // 로그인 실패 URL 설정
                .permitAll()
                .and()

                .logout() // 로그아웃 관련 처리
                .logoutUrl("/logout") // 로그아웃 URL 설정
                .logoutSuccessUrl("/login") // 로그아웃 성공 후 이동할 URL 설정
                .invalidateHttpSession(true) // 로그아웃 후 세션 초기화 설정
                .deleteCookies("JSESSIONID") // 로그아웃 후 쿠기 삭제 설정
                .and();

//                // 사용자 인증 필터 적용
//                .addFilterBefore(usrCustomAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);



        return http.build();
    }

//    /*
//     * customLoginSuccessHandler를 CustomAuthenticationFilter의 인증 성공 핸들러로 추가
//     * 로그인 성공 시 /user/login 로그인 url을 체크하고 인증 토큰 발급
//     */
//    @Bean
//    public UsrCustomAuthenticationFilter usrCustomAuthenticationFilter() throws Exception {
//        UsrCustomAuthenticationFilter customAuthenticationFilter = new UsrCustomAuthenticationFilter(authenticationManager());
//        customAuthenticationFilter.setFilterProcessesUrl("/user/login");
//        customAuthenticationFilter.setAuthenticationSuccessHandler(usrCustomLoginSuccessHandler());
//        customAuthenticationFilter.setAuthenticationFailureHandler(usrCustomLoginFailHandler());
//        customAuthenticationFilter.afterPropertiesSet();
//        return customAuthenticationFilter;
//    }

    // 로그인 성공 시 실행될 handler bean 등록
    @Bean
    public UsrCustomLoginSuccessHandler usrCustomLoginSuccessHandler() {
        return new UsrCustomLoginSuccessHandler();
    }

    // 로그인 실패 시 실행될 handler bean 등록
    @Bean
    public UserCustomLoginFailHandler usrCustomLoginFailHandler() {
        return new UserCustomLoginFailHandler();
    }


}