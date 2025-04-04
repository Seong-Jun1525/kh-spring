package com.kh.spring.notice.model.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.spring.common.PageInfo;
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
	public List<Notice> selectNoticeList(PageInfo pi) {
		// 공지사항 목록 조회하는 쿼리문 실행하여 결과 반환
		
		// RowBounds 객체 생성 => Mybatis에서 제공되는 객체
		// 시작값과 끝값을 전달하여 조회목록을 쪼개서 조회하도록 도와줌(범위를 지정할 수 있다!)
		int offset = (pi.getCurrPage() - 1) * pi.getBoardLimit();
		RowBounds rb = new RowBounds(offset, pi.getBoardLimit());
		
		return sqlSession.selectList("noticeMapper.selectNoticeList", null, rb);
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

	// 내가 한 검색
//	public List<Notice> selectNoticeByNoticeTitle(String keyword) {
//		return sqlSession.selectList("noticeMapper.selectNoticeByNoticeTitle", keyword);
//	}
	
	// 강사님이랑 한 검색
	public List<Notice> findbyNoticeTitleLike(String keyword, PageInfo pi) {
		int offset = (pi.getCurrPage() - 1) * pi.getBoardLimit();
		RowBounds rb = new RowBounds(offset, pi.getBoardLimit());

		return sqlSession.selectList("noticeMapper.findbyNoticeTitleLike", keyword, rb);
	}

	public int selectNoticeCount() {
		return sqlSession.selectOne("noticeMapper.selectNoticeCount");
	}
	
	/**
	 * 공지사항 검색 시 갯수 조회
	 */
	public int selectByNoticeTitleCount(String keyword) {
		return sqlSession.selectOne("noticeMapper.selectNoticeCount", keyword);
	}
}
