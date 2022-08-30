package com.song.mingblog.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Minkyu
 * Date : 2022-08-30
 * Time :
 * Remark : 권한없는 사용자가 접근시 막아주는 핸들러
 */
@Slf4j
@Component
public class WebAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ade) throws IOException {
        log.info("권한없는 사용자가 접근시 막아주는 핸들러 작동");
        // 처리작업하기2
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }

}
