package com.song.mingblog.jwt;

import com.song.mingblog.account.accountDtos.AccountProviderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Minkyu
 * Remark : 사용자 로그인 Provider
 */
@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private final BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
		
		// AuthenticaionFilter에서 생성된 토큰으로부터 아이디와 비밀번호를 조회함
		String userId = token.getName();
		String userPassword = (String) token.getCredentials();

		log.info("AuthenticationProvider 토큰 받을 아이디 : "+userId);

		AccountProviderDto accountProviderDto = new AccountProviderDto();

		// DB에서 아이디로 사용자 조회 -> 비밀번호 일치 여부 체크
		if (!passwordEncoder.matches(userPassword, accountProviderDto.getPassword())) {
			throw new BadCredentialsException(accountProviderDto.getUserid() + " Invalid password");
		}
		
		// principal(접근대상 정보), credential(비밀번호), authorities(권한 목록)를 Token에 담아 반환
		return new UsernamePasswordAuthenticationToken(accountProviderDto, userPassword, accountProviderDto.toAuthentication().getAuthorities());
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}