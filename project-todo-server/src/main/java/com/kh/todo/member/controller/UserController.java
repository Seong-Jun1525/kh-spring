package com.kh.todo.member.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kh.todo.member.model.vo.User;
import com.kh.todo.member.model.vo.UserDTO;
import com.kh.todo.member.service.MailService;
import com.kh.todo.member.service.UserService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController // @Controller + @ResponseBody
@RequiredArgsConstructor // 롬복을 이용하여 생성자 주입 처리
public class UserController {
	private final MailService mailService;
	private final UserService userService;
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
		
		String id = (String) requestBody.get("userId");
		System.out.println(id);
		 
		boolean result = userService.checkId(id);
		System.out.println(result);
		
		return result ? "NNNNY" : "NNNNN";
	}
	
	/**
	 * 회원가입
	 * 
	 * @param UserDTO 회원정보 {userId, userPwd, nickname, email}
	 * @return "success" : 가입성공, "failed" : 가입 실패
	 */
	@PostMapping("user") 
	public String registerUser(@RequestBody UserDTO userDTO) {
		
		System.out.println(userDTO);
		
		int result = userService.register(userDTO);
		System.out.println("result : " + result);
		return result > 0 ? "success" : "failed";
	}
	
	/**
	 * 로그인(회원정보 조회)
	 * 
	 * [POST] /login
	 * 
	 * @param UserDTO 로그인 정보 {userId: 아이디, userPwd: 비밀번호}
	 * @return "success" : 로그인 성공, "failed" : 로그인 실패
	 */
	@PostMapping("/login")
	public String login(@RequestBody UserDTO userDTO, HttpSession session) {
		System.out.println(userDTO);
		
		User user = userService.login(userDTO);

		if(user != null) {
			session.setAttribute("userInfo", user);
			// session => 서버에서만 관리 클라이언트는 접근할 수 없음!
			return "success";
		}
		return "failed";
	}
}
