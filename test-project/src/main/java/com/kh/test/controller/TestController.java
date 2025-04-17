package com.kh.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	@GetMapping("test1")
	public String test1() {
		return "테스트 1이 호출됨";
	}
	
	@GetMapping("test2")
	public String test2(String name) {
		return name + "님 반갑습니다.";
	}
}
