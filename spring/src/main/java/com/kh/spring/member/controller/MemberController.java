package com.kh.spring.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.spring.member.model.vo.Member;
import com.kh.spring.member.service.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/member") // 공통 주소 설정
public class MemberController {
	/**
	 * 기존 객체 생성 방식
	 * private MemberService mService = new MemberServiceImpl();
	 * => 객체 간의 결합도가 높아짐(-> 코드 수정 시 하나하나 모두 바꿔줘야 함)
	 * => 동시에 많은 요청이 될 경우 그 만큼 객체가 생성될 것임
	 * 
	 * DI - 의존성 주입을 통해 보완
	 * 
	 * @Autowired : 의존성 주입 시 사용되는 어노테이션
	 * => 클래스 내에 객체를 직접 생성하지 않고 spring 에서 관리하는 객체를 주입받아(Bean 등록) 사용하도록 해줄 것임
	 * 
	 * [주입 방식]
	 * 1) 필드 주입 방식 : 스프링 컨테이너가 객체를 생성한 후 @Autowired 가 붙은 필드에 의존성을 주입하는 방식
	 * 		=> 간결하고 해당 필드에 대한 처리를 하지 않아도 됨(생성자, setter)
	 * 		=> 테스트가 어려움(객체 생성 시 주입되는 것이 아닌 bean 에 생성된 후 주입받ㄴ는 방식으로 테스트 진행 시 임의의 객체를 생성하기 어려움
	 * 		=> 불변성 보장 문제(객체 생성 시 의존성이 주입되어 고정되지 않으므로 클래스 생성 이후 의존성이 변경될 수 있음)
	 * 
	 * 2) 생성자 주입 방식 : 스프링 컨테이너가 객체를 생성할 때 @Autowired가 붙은 생성자를 통해 필요한 의존성을 주입하는 방식(권장)
	 * 		=> 불변성 보장, 테스트가 편리하다
	 */
	
	// 생성자 주입 방식
	private final MemberService mService;
	private final BCryptPasswordEncoder bcpe;
	
	@Autowired
	public MemberController(MemberService mService, BCryptPasswordEncoder bcpe) {
		this.mService = mService;
		this.bcpe = bcpe;
	}

	/**
	 * 회원가입 페이지 응답
	 * 
	 * @return member/enrollForm
	 */
	@GetMapping("/enrollForm")
	public String enrollFormPage(HttpSession session) {
		// "{prefix}member/enrollForm{suffix}"
		return "member/enrollForm"; // 응답할 페이지 정보(경로) application.properties 파일에 prefix 와 suffix 를 설정
	}
	
	@PostMapping("/login")
	public String login(Member member, HttpSession session, Model model) { // 키값과 매개변수명인 동일 시 @RequestParam ("키값") 생략가능
//		System.out.println(userId);
//		System.out.println(userPwd);
		
		Member loginMember = mService.loginMember(member);
		
//		System.out.println(loginMember);
		
		if(loginMember == null) {
			model.addAttribute("errorMsg", "가입된 회원이 아닙니다.");
			return "common/errorPage";
		} else if(!bcpe.matches(member.getUserPwd(), loginMember.getUserPwd())) {
			
			/**
			 * member : 사용자가 로그인할 때 입력한 비밀번호(평문)
			 * loginMember : DB에서 ID 기준으로 조회한 데이터(암호문)
			 */
			
			model.addAttribute("errorMsg", "비밀번호가 잘못되었습니다.");
			return "common/errorPage";
		} else {
			session.setAttribute("loginMember", loginMember);
			session.setAttribute("alertMsg", "로그인 성공!");
			return "redirect:/";
		}
		
		/*
		 if(loginMember != null) {
			session.removeAttribute("alertMsg");
			
			session.setAttribute("loginMember", loginMember);
			session.setAttribute("alertMsg", "로그인 성공!");
			return "redirect:/";
		} else {
			model.addAttribute("errorMsg", "회원가입에 실패했습니다");
			return "common/errorPage";
		}
		 */
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	/**
	 * [1] HttpServletRequest 객체 사용 가능(기존방식)
	 * [2] @RequestParam 어노테이션 사용
	 *		=> @RequestParam("키값")자료형 변수명 --> 매개변수 위치에 작성
	 * 		=> @RequestParam(value="키값", defaultValue="기본값")자료형 변수명 --> 기본값이 필요할 경우
	 * 		=> request.getParameter("키값") 의 작업을 대신 해주는 어노테이션
	 * [3] @RequestParam 생략
	 * 		=> 주의!! 매개변수 요청 시 전달되는 데이터의 키값과 동일하게 작성해야함
	 * 
	 * [4] Commend Object 방식
	 * 		=> 요청 시 전달되는 데이터를 VO/DTO 클래스 타입으로 받고자 하는 경우
	 * 		=> 주의!! 전달되는 데이터의 키값을 받고자하는 클래스의 필드명과 일치하도록 해야함
	 * 		=> 스프링컨테이너가 해당 클래스 객체를 기본생성자로 생성 후
	 * 			setter 메서드를 사용하여 요청 시 전달 값을 해당 필드에 저장함
	 */
	@PostMapping("/regist")
//	public String register(HttpServletRequest request) { => request.getParameter("키값") [1] 방법
//	public String register(@RequestParam("userId") String userId) { [2] 방법
	public String register(Member m, HttpSession session, Model model) {
//		System.out.println(m);
		
		// 비밀번호 암호화 처리
		// 평문 -> 암호문
		// 암호화 처리 => BCryptPasswordEncoder
		String encPwd = bcpe.encode(m.getUserPwd());
		m.setUserPwd(encPwd); // 암호화된 값으로 변경
		
		int result = mService.insertMember(m);
		
		if(result > 0) {
			session.removeAttribute("alertMsg");
			// 회원 가입 성공
			session.setAttribute("alertMsg", "회원가입에 성공했습니다");
			return "redirect:/";
			
		} else {
			// 회원 가입 실패
			// request 영역에 저장 => Model
			model.addAttribute("errorMsg", "회원가입에 실패했습니다");
			return "common/errorPage";
		}
	}
	
	@GetMapping("/myPage")
	public String myPage() {
		return "member/myPage";
	}
	
	@PostMapping("/updateMember")
	public String updateMember(Member member, HttpSession session, Model model) {
		System.out.println("수정 전 : " + member);
		
		Member updateMember = mService.updateMember(member);
		System.out.println(updateMember);
		
		if(updateMember != null) {
			session.removeAttribute("loginMember");
			session.setAttribute("alertMsg", "회원정보를 수정했습니다");
			session.setAttribute("loginMember", updateMember);
			return "redirect:/";
		} else {
			// 회원 가입 실패
			// request 영역에 저장 => Model
			model.addAttribute("errorMsg", "회원정보에 실패했습니다");
			return "common/errorPage";
		}
	}
}
