package com.example.springjwtpractice.controller;

import com.example.springjwtpractice.config.auth.PrincipalDetails;
import com.example.springjwtpractice.model.User;
import com.example.springjwtpractice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Created by cadqe13@gmail.com on 2022-04-15
 * Blog : https://velog.io/@donsco
 * GitHub : https://github.com/wlwlsus
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/")
public class RestApiController {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("home")
	public String home() {
		return "<h1>home<h1>";
	}


	// JWT 를 사용하면 UserDetailsService 를 호출하지 않기 때문에 @AuthenticationPrincipal 사용 불가능.
	// 왜냐하면 @AuthenticationPrincipal 은 UserDetailsService 에서 리턴될 때 만들어 지기 때문이다.
	@GetMapping("user")
	public String user(Authentication authentication) {
		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
		System.out.println("1 principalDetails: " + principalDetails.getUser().getId());
		System.out.println("2 principalDetails: " + principalDetails.getUser().getUsername());
		System.out.println("3 principalDetails: " + principalDetails.getUser().getPassword());

		return "user";
	}

	@GetMapping("manager")
	public String manage() {
		return "manager!";
	}

	@GetMapping("admin")
	public String admin() {
		return "admin";
	}

	@PostMapping("join")
	public String join(@RequestBody User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles("ROLE_USER");
		userRepository.save(user);
		return "회원 가입 완료";
	}
}
