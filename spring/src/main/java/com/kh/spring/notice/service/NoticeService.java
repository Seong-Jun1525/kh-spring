package com.kh.spring.notice.service;

import java.util.ArrayList;
import java.util.List;

import com.kh.spring.notice.model.vo.Notice;

public interface NoticeService {
	ArrayList<Notice> selectNoticeList();
	
	Notice selectNoticeById(int noticeNo);
	
	int insertNotice(Notice notice);
	
	int updateNotice(Notice notice);
	
	int deleteNotice(int noticeNo);

	ArrayList<Notice> selectNoticeByNoticeTitle(String keyword);
}
