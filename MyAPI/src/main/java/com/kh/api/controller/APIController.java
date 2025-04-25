package com.kh.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.api.model.dto.UserDTO;

/**
 * @CrossOrigin : 스프링에서 제공하는 어노테이션으로 CORS 설정을 위해 사용
 * 					=> 클래스, 메서드 단위에 설정
 * 
 * - CORS (Cross-Origin Resource Sharing)
 * 		=> 클라이언트와 서버가 서로 다른 출처(origin)에서 요청을 주고 받을 때 발생하는 보안 정책
 * 		=> 이 문제를 해결하기 위해 서버 쪽에 @CrossOrigin을 사용하여 허용할 출처(origin)를 명시함
 * 
 * 		* origin : 프로토콜 + 도메인 + 포트
 */

//@CrossOrigin(origins="http://localhost:3000/") // 여러 개의 출처를 지정할 수 있다.
@RestController
public class APIController {
	
	@GetMapping("/server")
	public String getServer() {
		return "MyAPI";
	}
	
	@GetMapping("/users")
	public List<UserDTO> getUserList() {
		
		List<UserDTO> list = new ArrayList<>();
		list.add(new UserDTO("임비타", 45, "010-1234-1234"));
		list.add(new UserDTO("김비타", 33, "010-2131-2131"));
		list.add(new UserDTO("홍비타", 22, "010-3213-3213"));
		
		return list;
	
	}
}
