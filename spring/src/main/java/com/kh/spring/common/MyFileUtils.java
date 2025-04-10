package com.kh.spring.common;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;

public class MyFileUtils {
	
	/**
	 * 업로드되는 파일의 이름을 변경하여 전달된 경로에 저장
	 * 		* 변경되는 파일명 형식 : spring_{현재날짜시간}{랜덤값}.{확장자}
	 * 
	 * @param file		: 업로드되는 파일 정보
	 * @param session	: 물리적 경로를 얻기 위해 사용되는 객체
	 * @param path		: 저장할 경로
	 * @return			: 변경된 파일명
	 */
	public static String saveFile(MultipartFile file, HttpSession session, String path) {
		// 확장자 추출
		// -> 원본명을 가지고 추출
		
		String originName = file.getOriginalFilename(); // => ex) test.jpg, test.docx 등
		String ext = originName.substring(originName.lastIndexOf("."));
		
		// 현재 날짜 시간 추출(yyyyMMddHHmmss)
		String now = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		// 참고) System.currentTimeMillis() : 현재 날짜 시간에 대한 ms 단위
		
		// 랜덤 값 추출 (5자리)
		int randomValue = (int)(Math.random() * 99999) + 10000;
		
		String changeName = "spring_" + now + randomValue + ext;
		
		// 물리적 경로 조회
		String savePath = session.getServletContext().getRealPath(path);
		
		try {
			// 저장할 경로가 존재 하지 않을 경우 오류가 발생하므로 폴더 생성하기!
			File folder = new File(savePath);
			if(!folder.exists()) {
				System.out.println("파일 저장 경로가 존재하지 않아 생성 " + savePath);
				folder.mkdirs();
			}
			
			file.transferTo(new File(savePath + changeName));
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return changeName;
	}
}
