package com.song.mingblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 스프링 시큐리티 규칙
    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // csrf 보안 설정 비활성화
                .antMatcher("/**").authorizeRequests() // 보호된 리소스 URI에 접근할 수 있는 권한 설정
                .antMatchers("/api/get").permitAll(); // 전체 접근 허용

        return http.build();
    }

}