package com.kh.spring.board.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.board.model.dao.BoardDAO;
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
		return (ArrayList<Board>)bDAO.selectBoardList(pi);
	}

	@Override
	public Board selectBoardDetail(int boardNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertBoard(Board board) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateBoard(Board board) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteBoard(Board board) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertReply(Reply reply) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Reply> selectReplyList(int boardNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int increaseCount(int boardNo) {
		// TODO Auto-generated method stub
		return 0;
	}

}
