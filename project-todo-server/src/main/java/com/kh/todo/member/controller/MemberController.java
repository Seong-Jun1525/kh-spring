package com.kh.todo.member.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kh.todo.member.service.MailService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@RestController // @Controller + @ResponseBody
@RequiredArgsConstructor // 롬복을 이용하여 생성자 주입 처리
public class MemberController {
	private final MailService mailService;
	/**
	 * 이메일을 전달받아 인증코드를 메일로 전송
	 * @param email
	 * @return "ok" : 인증코드 발송, "nok" : 인증코드 발송 실패
	 */
	
	@PostMapping("/email/send")
	public String sendEmailCode(@RequestBody Map<String, Object> requestBody) {
		/**
		 * 폼 요청 시 전송 형식과 axios fetch 요청 시 전송 형식이 달라
		 * 요청 본문에서 전달 데이터를 추출해야함
		 * 
		 * @RequestBody : 요청 본문. 전송 형식이 application/json
		 * @RequestParam : 쿼리 파라미터 또는 폼 데이터. 단일 데이터 처리
		 * @ModelAttribute : 쿼리 파라미터 또는 폼 데이터. 객체 바인딩 처리
		 */
		
		System.out.println("email : " + requestBody.get("email"));
		String email = (String) requestBody.get("email");
		
		try {
			mailService.sendMail(email);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "nok";
		}
		
		return "ok";
	}
	/**
	 * 이메일에 대한 인증 코드 검증
	 * @param requestBody
	 * @return "success"
	 */
	@PostMapping("/email/verify")
	public String verifyEmailCode(@RequestBody Map<String, Object> requestBody) { // 프로젝트에서는 DTO로 만들기!
		String email = (String) requestBody.get("email");
		String code = (String) requestBody.get("code");
		
		System.out.println(email);
		System.out.println(code);
		
		boolean result = mailService.verify(email, code);
		
		return result ? "success" : "failed";
	}
	
	/**
	 * 아이디 중복체크
	 * 
	 * [POST] / checkId
	 * @param 아이디
	 * @return "NNNNN" : 중복된 아이디, "NNNNY" : 사용 가능한 아이디
	 */
	@PostMapping("/checkId")
	public String checkId(@RequestBody Map<String, Object> requestBody) {
		
		String id = (String) requestBody.get("id");
		 
		int result = 0;
		
		return result > 0 ? "NNNNN" : "NNNNY";
	}
	
}
