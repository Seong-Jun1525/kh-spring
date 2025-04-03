package com.kh.spring.notice.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.notice.model.dao.NoticeDAO;
import com.kh.spring.notice.model.vo.Notice;

import lombok.RequiredArgsConstructor;

@Service // 빈 등록
public class NoticeServiceImpl implements NoticeService {
	
	// 생성자 주입 방식으로 DI 처리
	private final NoticeDAO nDAO;
	
	@Autowired
	public NoticeServiceImpl(NoticeDAO nDAO) {
		this.nDAO = nDAO;
	}

	@Override
	public ArrayList<Notice> selectNoticeList() {
		return (ArrayList<Notice>) nDAO.selectNoticeList();
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

	@Override
	public ArrayList<Notice> selectNoticeByNoticeTitle(String keyword) {
		return (ArrayList<Notice>) nDAO.selectNoticeByNoticeTitle(keyword);
	}

}
