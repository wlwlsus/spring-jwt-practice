package com.example.springjwtpractice.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.springjwtpractice.config.auth.PrincipalDetails;
import com.example.springjwtpractice.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

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

		try {
			ObjectMapper om = new ObjectMapper();
			User user = om.readValue(request.getInputStream(), User.class);


			// 1. username, password 받아서 정상인지 로그인시도 해보면
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

			// 2. PrincipalDetailsService 의 loadUserByUsername()가 함수 실행됨
			Authentication authentication = authenticationManager.authenticate(authenticationToken);

			// authentication 객체가 session 영역에 저장됨 => 로그인 되었다는 뜻
			PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
			System.out.println("로그인 완료: " + principalDetails);

			return authentication;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		System.out.println("successfulAuthentication" + "실행 됨 : 인증 완료!");

		PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();


		// RSA 방식은 아니고 HMAC512
		String jwtToken = JWT.create()
						.withSubject(principalDetails.getUsername())
						.withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
						.withClaim("id", principalDetails.getUser().getId())
						.withClaim("username", principalDetails.getUser().getUsername())
						.sign(Algorithm.HMAC512(JwtProperties.SECRET));

		response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);
	}
}
