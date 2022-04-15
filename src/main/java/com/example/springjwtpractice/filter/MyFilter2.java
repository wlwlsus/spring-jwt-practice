package com.example.springjwtpractice.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by cadqe13@gmail.com on 2022-04-15
 * Blog : https://velog.io/@donsco
 * GitHub : https://github.com/wlwlsus
 */
public class MyFilter2 implements Filter {
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		System.out.println("필터2");
		filterChain.doFilter(servletRequest, servletResponse);
	}
}
