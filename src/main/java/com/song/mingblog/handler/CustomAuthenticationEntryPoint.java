package com.song.mingblog.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Minkyu
 * Date : 2022-08-30
 * Time :
 * Remark : 인증되지 않은 사용자가 접근시 막아주는 핸들러
 */
@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        log.info("인증되지 않은 사용자가 접근시 막아주는 핸들러 작동");
        // 처리작업하기1
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
