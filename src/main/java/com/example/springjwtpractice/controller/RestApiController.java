package com.example.springjwtpractice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cadqe13@gmail.com on 2022-04-15
 * Blog : https://velog.io/@donsco
 * GitHub : https://github.com/wlwlsus
 */
@RestController
public class RestApiController {


	@GetMapping("home")
	public String home() {
		return "<h1>home<h1>";
	}
}
