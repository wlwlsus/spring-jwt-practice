package com.example.springjwtpractice.config.jwt;

/**
 * Created by cadqe13@gmail.com on 2022-04-16
 * Blog : https://velog.io/@donsco
 * GitHub : https://github.com/wlwlsus
 */
public interface JwtProperties {
	String SECRET = "cos";
	int EXPIRATION_TIME = 60000 * 10; // 10분
	String TOKEN_PREFIX = "Bearer ";
	String HEADER_STRING = "Authorization";
}
