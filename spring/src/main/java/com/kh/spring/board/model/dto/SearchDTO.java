package com.kh.spring.board.model.dto;

import lombok.Data;

/**
 * DTO Data Transfer Object
 * - 통신 / 흐름 간에 저장되어야할 데이터
 * - 데이터 전송 체계로 프로세스 간에 데이터를 전달하는 객체를 의미
 */

@Data
public class SearchDTO {
	private String condition;
	private String keyword;
	
	// 정렬기준 추가
	private String orderby;
}
