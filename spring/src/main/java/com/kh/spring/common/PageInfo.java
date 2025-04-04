package com.kh.spring.common;

import lombok.Getter;

@Getter
public class PageInfo {

	/**
	 * 페이징 처리를 위한 준비 작업
	 * 
	 * [필요한 데이터]
	 * + 전체 게시글 갯수
	 * + 현재 페이지 번호 => 사용자로부터 전달될 값!
	 * + 페이징 바의 최대 갯수(표시될 페이지 번호의 갯수)
	 * + 한 페이지 당 표시할 최대 개수
	 * 
	 * + 가장 마지막 페이지 번호 (총 페이지 수)
	 * + 표시되는 페이징 바의 시작 번호
	 * + 표시되는 페이징 바의 마지막 번호
	 */
	
	private int listCount;		// 전체 게시글 수
	private int currPage;		// 현재 페이지 번호
	private int pageLimit;		// 페이징 바의 최대 개수
	private int boardLimit;		// 한 페이지 당 게시글 최대 개수
	// => 위의 4개 데이터를 가지고 아래 3개 데이터를 계산할 것임
	
	private int maxPage;		// 가장 마지막 페이지 번호
	private int startPage;		// 페이징 바의 시작 번호
	private int endPage;		// 페이징 바의 끝 번호
	
	public PageInfo(int listCount, int currPage, int pageLimit, int boardLimit) {
		this.listCount = listCount;
		this.currPage = currPage;
		this.pageLimit = pageLimit;
		this.boardLimit = boardLimit;
	
		/**
		 * 가장 마지막 페이지 번호(maxPage)
		 * 
		 * listCount	boardLimit
		 * 100			10			=> 10
		 * 107			10			=> 11
		 * 110			10			=> 11
		 * 115			10			=> 12
		 * 
		 * => listCount /boardList --> 올림 처리 필요!!
		 */		
		this.maxPage = (int)Math.ceil((double)listCount / boardLimit);
		
		/**
		 * 페이징 바 시작 번호(startPage)
		 * 
		 * currPage		pageLimit
		 * 1			10			=> 1
		 * 5			10			=> 1
		 * 11			10			=> 11
		 * 
		 * 1 ~ 10 --> 1			n : 0
		 * 11 ~ 20 --> 11		n : 1
		 * 21 ~ 30 --> 21		n : 2
		 * 
		 * n * 10 + 1
		 * 
		 * ((currPage - 1) / pageLimit) * pageLimit + 1
		 */
		this.startPage = ((currPage - 1) / pageLimit) * pageLimit + 1;
		
		/**
		 * 페이징 바 마지막 번호(endPage)
		 * if) pageLimit = 10
		 * 
		 * startPage : 1 => endPage : 10
		 * 			 : 11 => 		: 20
		 *  		 : 21 => 		: 30
		 *  
		 * endPage = startPage + pageLimit - 1
		 */
		this.endPage = this.startPage + pageLimit - 1;
		// 만약 마지막 페이지 번호(maxPage)가 13이라면? 끝 번호도 13으로 저장해야 함
		this.endPage = this.endPage > this.maxPage ? this.maxPage : this.endPage;
		
	}
}
