package com.example.springjwtpractice.repository;

import com.example.springjwtpractice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by cadqe13@gmail.com on 2022-04-15
 * Blog : https://velog.io/@donsco
 * GitHub : https://github.com/wlwlsus
 */
public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
