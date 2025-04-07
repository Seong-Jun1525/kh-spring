package com.kh.spring.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Interceptor
 * - Spring MVC 컨트롤러 실행 전 후로 동작하는 작업을 설정
 * - DispatcherServlet(요청 받은 작업을 매핑이 알맞는 Controller에 작업 전달함)
 *      이후 단계에서 실행 됨
 *      
 * 요청 <-> DispatcherServlet <- (여기서 실행됨) -> Controller
 * 
 * 장점
 * - Spring MVC 컨트롤러 실행 전후로 동작하기 때문에
 *   특정 URL 단위로 비즈니스 로직을 분석할 수 있음
 * 
 * 단점
 * - 요청 본문(body)를 직접 읽고 조작하기 어려움 -> 실제 처리 작업은 Controller에서 처리해줘야 함
 * - 보통 API(URL) 요청 별로 어떤 작업을 수행하기 위해 사용
 * 		(인증/권한 체크)
 */

// [1] HandlerInterceptor 구현 처리
// [2] 처리할 내용에 따라 메서드 오버라이딩
public class LoginInterceptor implements HandlerInterceptor {

	/**
	 * 요청 전 처리 : preHandle
	 * 응답 전 처리 : postHandle
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		/**
		 * return true : 정상적으로 동작시키겠다
		 * return false : 해당 요청을 처리하지 않겠다
		 */

		// 로그인 하지 않았을 경우 해당 요청을 처리하지 않도록
		// 로그인 정보 => 세션 영역에 있음
		HttpSession session = request.getSession();

		// 로그인이 되어 있을 경우 정상 동작 처리
		if(session.getAttribute("loginMember") != null) return true;
		// 로그인이 되어 있지 않을 경우 해당 요청을 처리하지 않음
		else {
			// 요청을 처리하지 않은 경우 대신 처리할 작업 설정
			session.setAttribute("alertTitle", "접속 불가!");
			session.setAttribute("alertMsg", "로그인 후 이용할 수 있습니다.");
			session.setAttribute("alertIcon", "warning");
			response.sendRedirect("/"); // redirect:/로 하면 요청은 되지만 알림창이 뜨지 않으므로 redirect:를 생략한다!
			return false;
		}
	}
	
}
