package com.kh.spring.board.service;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.board.model.dao.BoardDAO;
import com.kh.spring.board.model.dto.SearchDTO;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.common.PageInfo;

@Service // 여기에 @Service 빈 등록!
public class BoardServiceImpl implements BoardService {

	private final BoardDAO bDAO;
	
	@Autowired // 명시하면 좋고 생략도 가능하다!
	public BoardServiceImpl(BoardDAO bDAO) {
		this.bDAO = bDAO;
	}
	
	// 필드 주입 방식 : 권장되지 않음
	//	@Autowired
	//	private BoardDAO bDAO;
	
//	@Override
//	public ArrayList<Board> selectBoardList() {
//		return (ArrayList<Board>)bDAO.selectBoardList();
//	}

	@Override
	public int selectBoardCount() {
		return bDAO.selectBoardCount();
	}

	@Override
	public ArrayList<Board> selectBoardList(PageInfo pi) {
		int offset = (pi.getCurrPage() - 1) * pi.getBoardLimit();
		RowBounds rb = new RowBounds(offset, pi.getBoardLimit());
		
		return bDAO.selectBoardList(rb);
	}

	@Override
	public Board selectBoardDetail(int boardNo) {
		return bDAO.selectBoardDetail(boardNo);
	}

	@Override
	public int insertBoard(Board board) {
		return bDAO.insertBoard(board);
	}

	@Override
	public int updateBoard(Board board) {
		return bDAO.updateBoard(board);
	}

	@Override
	public int deleteBoard(int boardNo) {
		return bDAO.deleteBoard(boardNo);
	}

	@Override
	public int insertReply(Reply reply) {
		return bDAO.insertReply(reply);
	}

	@Override
	public ArrayList<Reply> selectReplyList(int boardNo) {
		return bDAO.selectReplyList(boardNo);
	}

	@Override
	public int increaseCount(int boardNo) {
		return bDAO.increaseCount(boardNo);
	}

	/* 검색 조건에 따른 목록 조회 */
	@Override
	public ArrayList<Board> selectBoardListLike(PageInfo pi, String condition, String keyword) {
		int offset = (pi.getCurrPage() - 1) * pi.getBoardLimit();
		RowBounds rb = new RowBounds(offset, pi.getBoardLimit());
		return bDAO.findBoardListLike(condition, keyword, rb);
	}

	@Override
	public int selectBoardCount(SearchDTO searchDTO) {
		return bDAO.selectBoardCount(searchDTO);
	}

	@Override
	public ArrayList<Board> selectBoardList(PageInfo pi, SearchDTO searchDTO) {
		int offset = (pi.getCurrPage() - 1) * pi.getBoardLimit();
		RowBounds rb = new RowBounds(offset, pi.getBoardLimit());
		return bDAO.findBoardListLike(rb, searchDTO);
	}

	@Override
	public ArrayList<Board> selectBoardRankList() {
		// RowBounds 객체 활용 => Rowbounds(시작위치, 개수)
		
		// 게시글 목록 조회 시 사용했던 쿼리문 사용
		// SearchDTO
		RowBounds rb = new RowBounds(0, 5);
		SearchDTO searchDTO = new SearchDTO();
		searchDTO.setOrderby("count");
		
		return bDAO.findBoardListLike(rb, searchDTO);
	}

}
