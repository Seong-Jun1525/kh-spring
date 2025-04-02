package com.kh.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration		// 설정 클래스 정의 시 사용되는 어노테이션
@EnableWebSecurity
public class SecurityConfig {
	/**
	 * BCryptPasswordEncoder 객체의 경우 외부 라이브러리 객체이므로
	 * 직접 클래스 구현부에 @Component 등 어노테이션을 사용하여 빈으로 등록할 수 없음!
	 * 
	 * 따라서 해당 객체를 만들어서 리턴하는 메서드를 정의하여 해당 메서드를 빈에 등록 후 객체를 사용 
	 */
	
	@Bean // 메서드를 Bean으로 등록하거나 외부라이브러리 객체를 등록하고자 할 때 사용하는 어노테이션	
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * spring security 에서 기본적으로 제공하는 기능 비활성화
	 * @throws Exception 
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.formLogin(AbstractHttpConfigurer::disable) // 로그인 폼 비활성
			.csrf(AbstractHttpConfigurer::disable); // Cross-Site Request Forgery 비활성화
		return http.build();
	}
}
