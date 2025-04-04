package com.kh.spring.notice.service;

import java.util.ArrayList;

import com.kh.spring.common.PageInfo;
import com.kh.spring.notice.model.vo.Notice;

public interface NoticeService {
//	ArrayList<Notice> selectNoticeList(); // 페이징 처리 적용 전
	ArrayList<Notice> selectNoticeList(PageInfo pi); // 페이징 처리 적용 후
	
	Notice selectNoticeById(int noticeNo);
	
	int selectNoticeCount();
	
	int insertNotice(Notice notice);
	
	int updateNotice(Notice notice);
	
	int deleteNotice(int noticeNo);

//	ArrayList<Notice> selectNoticeByNoticeTitle(String keyword);
	ArrayList<Notice> selectNoticeByNoticeTitle(String keyword, PageInfo pi);

	/**
	 * 공지사항 검색 시 게시글 수
	 */
	int selectByNoticeTitleCount(String keyword);
}
