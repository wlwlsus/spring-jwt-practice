package com.example.springjwtpractice.config.auth;

import com.example.springjwtpractice.model.User;
import com.example.springjwtpractice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by cadqe13@gmail.com on 2022-04-15
 * Blog : https://velog.io/@donsco
 * GitHub : https://github.com/wlwlsus
 */
// http://localhost:8080/login
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("@@loadUserByUsername");
		User userEntity = userRepository.findByUsername(username);

		return new PrincipalDetail(userEntity);
	}
}
