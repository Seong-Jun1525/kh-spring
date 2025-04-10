package com.kh.spring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.kh.spring.interceptor.LoginInterceptor;

/**
 * 웹 관련 설정을 위한 클래스
 * 인터셉터나 필더 등
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 인터셉터 등록
		registry.addInterceptor(new LoginInterceptor())
//		.addPathPatterns("/member/myPage", "/board/enrollForm");
		.addPathPatterns("/member/myPage", "/notice/**", "/board/**")
		.excludePathPatterns("/notice/list", "/board/list");
	}
	
}
