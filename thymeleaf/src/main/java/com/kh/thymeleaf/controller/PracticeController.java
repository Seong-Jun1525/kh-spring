package com.kh.thymeleaf.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.kh.thymeleaf.model.dto.UserDTO;

import jakarta.servlet.http.HttpSession;

@Controller
public class PracticeController {
	/**
	 * thymeleaf 사용 시 기본 설정 값
	 * => classpath : src/main/resources/
	 * - prefix : classpath:templates/
	 * - suffix : .html
	 */
	
	@GetMapping("/go")
	public String go() {
		System.out.println("go!!!");
		return "/practice/page1";
	}
	
	/**
	 * 전달된 값 받는 방법
	 * 1. 객체 생성하여 객체로 받기
	 * 2. 키값과 동일한 변수로 하나하나 받기
	 * 3. HttpServletRequest 로 받기
	 * 
	 * 비즈니스 로직 처리 후 결과를 반환할 때
	 * 1. Model : 스프링에서 제공하는 객체로 request 영역에 데이터 저장
	 */
	
	@PostMapping("/add")
	public String add(UserDTO userDTO, HttpSession session, Model model) {
		
		System.out.println(userDTO);
		
		model.addAttribute("p", userDTO);
		session.setAttribute("user", userDTO);
		
		ArrayList<UserDTO> list = new ArrayList<>();
		
		list.add(new UserDTO("아이유", 20, "2005-03-13"));
		list.add(new UserDTO("가이유", 20, "2005-12-13"));
		list.add(new UserDTO("나이유", 20, "2005-11-13"));
		
		model.addAttribute("list", list);
		
		return "practice/page2";
	}
}
