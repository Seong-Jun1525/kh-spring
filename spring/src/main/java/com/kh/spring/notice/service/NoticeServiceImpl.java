package com.kh.spring.notice.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.common.PageInfo;
import com.kh.spring.notice.model.dao.NoticeDAO;
import com.kh.spring.notice.model.vo.Notice;

@Service // 빈 등록
public class NoticeServiceImpl implements NoticeService {
	
	// 생성자 주입 방식으로 DI 처리
	// lombok을 사용할 경우 @NoRequiredConstrutor 어노테이션 사용
	private final NoticeDAO nDAO;
	
	@Autowired
	public NoticeServiceImpl(NoticeDAO nDAO) {
		this.nDAO = nDAO;
	}

//	@Override
//	public ArrayList<Notice> selectNoticeList() {
//		return (ArrayList<Notice>) nDAO.selectNoticeList();
//	}
	@Override
	public ArrayList<Notice> selectNoticeList(PageInfo pi) {
		return (ArrayList<Notice>) nDAO.selectNoticeList(pi);
	}

	@Override
	public Notice selectNoticeById(int noticeNo) {
		return nDAO.selectNoticeById(noticeNo);
	}

	@Override
	public int insertNotice(Notice notice) {
		return nDAO.insertNotice(notice);
	}

	@Override
	public int updateNotice(Notice notice) {
		return nDAO.updateNotice(notice);
	}

	@Override
	public int deleteNotice(int noticeNo) {
		return nDAO.deleteNotice(noticeNo);
	}

//	@Override
//	public ArrayList<Notice> selectNoticeByNoticeTitle(String keyword) {
//		return (ArrayList<Notice>) nDAO.selectNoticeByNoticeTitle(keyword);
//	}
	@Override
	public ArrayList<Notice> selectNoticeByNoticeTitle(String keyword, PageInfo pi) {
		return (ArrayList<Notice>) nDAO.findbyNoticeTitleLike(keyword, pi);
	}
	
	/**
	 * 공지사항 검색 시 게시글 수
	 */
	@Override
	public int selectByNoticeTitleCount(String keyword) {
		return nDAO.selectByNoticeTitleCount(keyword);
	}

	@Override
	public int selectNoticeCount() {
		return nDAO.selectNoticeCount();
	}

}
