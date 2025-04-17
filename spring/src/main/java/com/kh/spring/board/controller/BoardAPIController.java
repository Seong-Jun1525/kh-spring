package com.kh.spring.board.controller;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.board.service.BoardService;

@RestController // @Controller + @ResponseBody => 모든 요청에 대한 응답을 페이지가 아닌 데이터로 응답함
@RequestMapping("/api/board")
public class BoardAPIController {

	private final BoardService bService;
	
	public BoardAPIController(BoardService bService) {
		this.bService = bService;
	}
	
	// 댓글 등록
	@PostMapping("/reply")
	@ResponseBody // 페이지가 아닌 데이터 형식으로 응답하고자 할 때 사용
	public String replyInsert(Reply reply) {
		System.out.println("ReplyController!!");
		
		System.out.println(reply);
		
		int result = bService.insertReply(reply);
		
		if(result > 0) {
			return "success";
		} else {
			return "failed";
		}
	}
	
	@GetMapping("/reply")
	@ResponseBody
	public ArrayList<Reply> selectReplyList(@RequestParam(required=true) int boardNo) {
		return bService.selectReplyList(boardNo);
	}
	
	@GetMapping("/top5")
	@ResponseBody
	public ArrayList<Board> selectBoardRank() {
		return bService.selectBoardRankList();
	}
}
