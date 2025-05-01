package com.kh.todo.member.model.vo;

import lombok.Data;

@Data
public class AuthData {
	private String code; // 인증 코드
	private Long time;   // 인증 코드에 대한 유효 시간
	
	private final long LIMIT_TIME = 1 * 1000 * 60 * 3; // 제한시간
	
	// 기본생성자 => 인증코드 생성, 유효시간 저장
	public AuthData() {
		this.code = makeRandomCode(""); // 랜덤 6자리
		this.time = System.currentTimeMillis() + LIMIT_TIME;
		// => 현재시간 + 제한시간
	}
	
	public String makeRandomCode(String code) {
		// 랜덤값 6자리 생성!
		// => 재귀함수로서 사용하여 생성
		
		if(code.length() == 6) return code;
		else {
			int value = (int) (Math.random() * 10); // 0 ~ 9
			
			// 짝수인 경우, 대문자 변경
			if(value % 2 == 0) {
				char chRan = (char)(Math.random() * ('Z' - 'A' + 1) + 'A');
				code += chRan;
			} else code += value;
			
			// 홀수인 경우, 숫자 그대로 사용
			
			return makeRandomCode(code);
		}
	}
}
