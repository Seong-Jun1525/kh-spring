package com.kh.spring.board.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Board {
	private int boardNo;			// 게시글 번호
	private String boardTitle;		// 게시글 제목
	private String boardWriter;		// 작성자 아이디
	private String boardContent;	// 게시글 내용
	private String originName;		// 원래 첨부파일 이름
	private String changeName;		// 변경된 첨부파일 이름
	private int count;				// 게시글 조회수
	private String createDate;		// 게시글 작성일
	private String status;			// 게시글 상태값
}
