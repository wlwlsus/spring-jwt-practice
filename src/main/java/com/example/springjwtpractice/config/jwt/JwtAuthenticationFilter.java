package com.example.springjwtpractice.config.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by cadqe13@gmail.com on 2022-04-15
 * Blog : https://velog.io/@donsco
 * GitHub : https://github.com/wlwlsus
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager;


	// login 요청을 하면 로그인 시도를 위해서 실행되는 함수!!
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		System.out.println("Jwt~: 로그인 시도 중");

		// 1. username, password 받아서 정상인지 로그인시도 해보면
		// 2. PrincipalDetailsService 가 호출 loadUserByUsername() 함수 실행됨
		// 3. 권한 관리를 위해서 PrincipalDetails 를 세션에 담고
		// 4. JWT 토큰을 만들어서 응답해준다.
		return super.attemptAuthentication(request, response);
	}
}
