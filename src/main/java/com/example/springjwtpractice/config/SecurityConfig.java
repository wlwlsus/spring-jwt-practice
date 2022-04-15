package com.example.springjwtpractice.config;

import com.example.springjwtpractice.config.jwt.JwtAuthenticationFilter;
import com.example.springjwtpractice.config.jwt.JwtAuthorizationFilter;
import com.example.springjwtpractice.filter.MyFilter1;
import com.example.springjwtpractice.filter.MyFilter3;
import com.example.springjwtpractice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.filter.CorsFilter;

/**
 * Created by cadqe13@gmail.com on 2022-04-15
 * Blog : https://velog.io/@donsco
 * GitHub : https://github.com/wlwlsus
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final CorsFilter corsFilter;
	private final UserRepository userRepository;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(new MyFilter3(), SecurityContextPersistenceFilter.class);
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
						.and()
						.addFilter(corsFilter) // @CrossOrigin 인증 X, 시큐리티 필터에 등록 인증 O
						.formLogin().disable()
						.httpBasic().disable()
						.addFilter(new JwtAuthenticationFilter(authenticationManager())) // AuthenticationManager
						.addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository))
						.authorizeRequests()
						.antMatchers("/api/v1/user/**").access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
						.antMatchers("/api/v1/manager/**").access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
						.antMatchers("/api/v1/admin/**").access("hasRole('ROLE_ADMIN')")
						.anyRequest().permitAll();

	}
}
