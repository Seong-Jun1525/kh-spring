package com.kh.spring.board.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.common.PageInfo;

@Mapper // MyBatis 에서 제공!
// 메서드명과 mapper의 해당 쿼리 태그의 id 값과 동일해야한다
public interface BoardDAO {
//	ArrayList<Board> selectBoardList();
	ArrayList<Board> selectBoardList(PageInfo pi);
	
	Board selectBoardDetail(int boardNo);
	
	int insertBoard(Board board);
	
	int updateBoard(Board board);
	
	int deleteBoard(Board board);

	int selectBoardCount();
}
