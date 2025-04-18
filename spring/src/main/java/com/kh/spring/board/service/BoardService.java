package com.kh.spring.board.service;

import java.util.ArrayList;

import com.kh.spring.board.model.dto.SearchDTO;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.common.PageInfo;

public interface BoardService {

	/* 게시글 전체 목록 조회 (사용 x) */
//	ArrayList<Board> selectBoardList();

	/* Board selectBoardDetail */
	Board selectBoardDetail(int boardNo);
	
	int insertBoard(Board board);
	
	int updateBoard(Board board);
	
	int deleteBoard(int boardNo);
	
	/* 댓글 등록 */
	int insertReply(Reply reply);
	
	/* 댓글 목록 조회 */
	ArrayList<Reply> selectReplyList(int boardNo);
	
	/* 조회수 증가 */
	int increaseCount(int boardNo);

	/* 전체 게시글 수 조회 (사용 x) */
	int selectBoardCount();

	/* 페이징 처리 적용한 게시글 목록 조회 */
	ArrayList<Board> selectBoardList(PageInfo pi);

	/* 검색 조건에 따른 목록 조회 */
	ArrayList<Board> selectBoardListLike(PageInfo pi, String condition, String keyword);

	/* 전체 게시글 수 조회 */
	int selectBoardCount(SearchDTO searchDTO);

	/* 게시글 전체 목록 조회 */
	ArrayList<Board> selectBoardList(PageInfo pi, SearchDTO searchDTO);

	/* 게시글 순위 */
	ArrayList<Board> selectBoardRankList();
}
