package com.example.springjwtpractice.controller;

import com.example.springjwtpractice.model.User;
import com.example.springjwtpractice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cadqe13@gmail.com on 2022-04-15
 * Blog : https://velog.io/@donsco
 * GitHub : https://github.com/wlwlsus
 */
@RestController
@RequiredArgsConstructor
public class RestApiController {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("home")
	public String home() {
		return "<h1>home<h1>";
	}

	@GetMapping("token")
	public String token() {
		return "<h1>token<h1>";
	}

	@PostMapping("join")
	public String join(@RequestBody User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles("ROLE_USER");
		userRepository.save(user);
		return "회원 가입 완료";
	}
}
