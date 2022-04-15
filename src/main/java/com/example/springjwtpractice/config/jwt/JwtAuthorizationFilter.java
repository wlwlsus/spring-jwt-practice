package com.example.springjwtpractice.config.jwt;

/**
 * Created by cadqe13@gmail.com on 2022-04-16
 * Blog : https://velog.io/@donsco
 * GitHub : https://github.com/wlwlsus
 */

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.springjwtpractice.config.auth.PrincipalDetails;
import com.example.springjwtpractice.model.User;
import com.example.springjwtpractice.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 시큐리티가 filter를 가지고 이쓴ㄴ데 그 필터 중에 BasicAuthenticationFilter 가 있다.
 * 권한이나 인증이 필요한 특정 주소를 요청했을 때 위 필터를 무조건 찾게 된다.
 * 만약 권한이나 인증이 필요한 주소가 아니라면 이 필터를 안 거친다.
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private final UserRepository userRepository;

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
		super(authenticationManager);
		this.userRepository = userRepository;
	}

	// 인증이나 권한이 필요한 주소 요청이 있을 때 해당 필터를 타게된다.
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("인증이나 권한이 필요한 주소 요청이 됨");

		String jwtHeader = request.getHeader(JwtProperties.HEADER_STRING);
		System.out.println("jwtHeader: " + jwtHeader);

		if (jwtHeader == null || !jwtHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}

		// JWT 토큰 검증을 해서 정상적인 사용자인지 확인
		String jwtToken = jwtHeader.replace(JwtProperties.TOKEN_PREFIX, "");

		String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(jwtToken).getClaim("username").asString();

		// 서명이 정상적으로 됨
		if (username != null) {
			User userEntity = userRepository.findByUsername(username);

			PrincipalDetails principalDetails = new PrincipalDetails(userEntity);

			// jwt 토큰 서명을 통해서 서명이 정상이면 Auth 객체를 만들어준다.
			Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

			// 강제로 시큐리티 세션에 접근하여 Authentication 객체 저장
			SecurityContextHolder.getContext().setAuthentication(authentication);

			chain.doFilter(request, response);
		}
	}
}
