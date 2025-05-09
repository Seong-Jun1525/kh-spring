package com.kh.todo.interceptor;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kh.todo.user.model.vo.UserDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * 세션에 로그인 정보가 있을 경우, 컨트롤러를 동작.
 * 없을 경우, 응답헤더에 401 코드를 담아서 컨트롤러를 동작시키지 않음
 * 
 * 401 : 인증 오류
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws IOException {
		
		// 세션 정보를 가져와야 함
		HttpSession session = request.getSession();
		
		if((UserDTO)session.getAttribute("user") != null) return true;
		else {
			// 헤더 설정
//			response.setStatus(401);
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			
			response.getWriter().print("로그인 후 이용 가능합니다.");
			
			return false;
		}
	}
	
}
