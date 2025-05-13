package com.kh.opendata.common;

import lombok.Getter;

@Getter
public class PageInfo {
	// 전체 항목 수
	// 현재 페이지 번호
	// 페이징 바 갯수
	// 한 페이지 당 보여줄 항목 수
	
	// 끝 페이지 번호
	// 페이징 바의 시작 번호
	// 페이징 바의 끝 번호
	
	private int listCount;
	private int currentPageNo;
	private int pageLimit;
	private int itemLimitCount;
	
	private int maxPageNo;
	private int startPageNo;
	private int endPageNo;
	
	public PageInfo(int listCount, int currentPageNo, int pageLimit, int itemLimitCount) {
		this.listCount = listCount;
		this.currentPageNo = currentPageNo;
		this.pageLimit = pageLimit;
		this.itemLimitCount = itemLimitCount;
		
		// 가장 마지막 페이지 번호
		this.maxPageNo = (int)Math.ceil((double)listCount / itemLimitCount);
		
		// 페이징 바의 시작 번호
		this.startPageNo = ((currentPageNo - 1) / pageLimit) * pageLimit + 1;
		
		// 페이징 바의 끝 번호
		this.endPageNo = this.startPageNo + pageLimit - 1;
		this.endPageNo = this.endPageNo > this.maxPageNo ? this.maxPageNo : this.endPageNo;
	}
}
