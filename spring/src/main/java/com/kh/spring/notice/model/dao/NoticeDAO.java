package com.kh.spring.notice.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.spring.notice.model.vo.Notice;

@Repository	// @Component 보다 좀 더 구체화 된 객체를 의미
			// 데이터베이스와 직접 연결되는 클래스를 지정
public class NoticeDAO {

	// SqlSession 객체 DI 처리(생성자 주입방식) -> Mybatis에서 제공해주는 객체
	private final SqlSession sqlSession;
	
	@Autowired
	public NoticeDAO(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	/**
	 * 공지사항 목록 조회
	 * 
	 * @return sqlSession의 selectList 메서드로 전체 공지사항을 출력하는 쿼리문을 실행하여 그 결과를 반환
	 */
	public List<Notice> selectNoticeList() {
		// 공지사항 목록 조회하는 쿼리문 실행하여 결과 반환
		return sqlSession.selectList("noticeMapper.selectNoticeList");
	}

	public Notice selectNoticeById(int noticeNo) {
		return sqlSession.selectOne("noticeMapper.selectNotice", noticeNo);
	}

	public int insertNotice(Notice notice) {
		// 스프링에서 내부적으로 commit도 해줌
		return sqlSession.insert("noticeMapper.insertNotice", notice);
	}

	public int updateNotice(Notice notice) {
		return sqlSession.update("noticeMapper.updateNotice", notice);
	}

	public int deleteNotice(int noticeNo) {
		return sqlSession.update("noticeMapper.deleteNotice", noticeNo);
	}

	public List<Notice> selectNoticeByNoticeTitle(String keyword) {
		return sqlSession.selectList("noticeMapper.selectNoticeByNoticeTitle", keyword);
	}
}
