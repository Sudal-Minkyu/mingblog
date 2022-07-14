//package com.song.mingblog.jwt.handeler;
//
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.web.access.AccessDeniedHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * @author Minkyu
// * Remark : 권한없이 사용 핸들러
// */
//@Component
//public class WebAccessDeniedHandler implements AccessDeniedHandler {
//
//	@Override
//	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
//		// 권한이 없는 경우 페이지 이동 시 사용
//		response.sendRedirect("/error/error403");
//		// 권한이 없는 경우 에러코드 반환 시 사용
//		response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//	}
//
//}