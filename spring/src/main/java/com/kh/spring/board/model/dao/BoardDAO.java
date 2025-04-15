package com.kh.spring.board.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.kh.spring.board.model.dto.SearchDTO;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;

@Mapper // MyBatis 에서 제공!
// 메서드명과 mapper의 해당 쿼리 태그의 id 값과 동일해야한다
public interface BoardDAO {
//	ArrayList<Board> selectBoardList();
	ArrayList<Board> selectBoardList(RowBounds rb);
	
	Board selectBoardDetail(int boardNo);
	
	int insertBoard(Board board);
	
	int updateBoard(Board board);
	
	int deleteBoard(int boardNo);

	// 사용 x
	int selectBoardCount();

	// 사용 x
	ArrayList<Board> findBoardListLike(String condition, String keyword, RowBounds rb);

	int selectBoardCount(SearchDTO searchDTO);

	ArrayList<Board> findBoardListLike(RowBounds rb, SearchDTO searchDTO);

	// 게시글 조회수 증가
	int increaseCount(int boardNo);

	// 게시글의 댓글 정보 조회
	ArrayList<Reply> selectReplyList(int boardNo);

	int insertReply(Reply reply);

	ArrayList<Board> selectBoardRankList();
}
