package com.song.mingblog.jwt.handeler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Minkyu
 * Remark : 로그인 인증되지않은 페이지 진입시 핸들러
 */
@Component
public class WebAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
		// 인증되지 않은 경우 페이지 이동 시 사용
		response.sendRedirect("/error/error404");
		// 인증되지 않은 경우 에러코드 반환 시 사용
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	}

}