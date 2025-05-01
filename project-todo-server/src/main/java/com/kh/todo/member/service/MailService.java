package com.kh.todo.member.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.kh.todo.member.model.vo.AuthData;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {

	// mail 관련 객체 추가(주입)
	private final JavaMailSender sender;
	public MailService(JavaMailSender sender) {
		this.sender = sender;
		
		// 스케줄러 추가! => 1분마다 인증 정보를 정리
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(() -> clearData(), 0, 1, TimeUnit.MINUTES); // 실행할 작업, 초기지연시간, 실행 주기, 시간단위
	}
	
	private Map<String, AuthData> authInfo = new HashMap<>();
	
	public void sendMail(String email) throws MessagingException {
		// 인증정보생성
		AuthData authData = new AuthData();
		String code = authData.getCode();
		
		// 메일 내용 작성
		// 제목, 보낸이, 받는이, 내용 등등
		
		String subject = "[TODO MANAGER] 테스트 메일";
		String[] to = {email}; // 받는 사람은 여럿일 수 있으므로 배열
		String content = "<p>아래의 인증코드를 입력해주세요</p>"
				+ "<h3>" + code + "</h3>"
						+ "<p>인증코드는 3분간 유효합니다.</p>";
		
		// 메일 전송
		MimeMessage mm = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mm); // 추가로 매개변수에 multipart 첨부파일이 들어간다
		
		helper.setSubject(subject);
		helper.setTo(to);
		helper.setText(content, true);
		/**
		 void org.springframework.mail.javamail.MimeMessageHelper.setText(
			String text,
			boolean html
			) throws MessagingException
			
			Set the given text directly as content in non-multipart mode or as default body part in multipart mode. The "html" flag determines the content type to apply.
			
			NOTE: Invoke addInline after setText; else, mail readers might not be able to resolve inline references correctly.
		 */
		
		authInfo.put(email, authData);
		sender.send(mm);
	}

	public boolean verify(String email, String code) {
		// Map에서 이메일에 해당하는 인증 정보가 있는 지 체크
		AuthData authData = authInfo.get(email);
		
		if(authData == null) return false;
		
		// 인증 정보가 있다면 코드값과 전달된 코드를 비교하여 동일한 지 체크
		// 제한 시간도 체크 => 현재시간 > 제한시간
		
		/** 시간처리 부분은 따로 스케줄러를 등록
		 long currTime = System.currentTimeMillis();
		if(currTime > authData.getTime()) {
			authInfo.remove(email);
			return false;
		}
		if(code.equals(authData.getCode())) {
			authInfo.remove(email);
			return true;
		} else return false;
		 */
		
		if(code.equals(authData.getCode())) authInfo.remove(email);
		return true;
	}
	
	private void clearData() {
		long currTime = System.currentTimeMillis();
		
		authInfo.entrySet().removeIf(entry -> currTime > entry.getValue().getTime());
	}
}
