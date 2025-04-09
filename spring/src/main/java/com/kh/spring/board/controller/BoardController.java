package com.kh.spring.board.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.board.model.dto.SearchDTO;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.service.BoardService;
import com.kh.spring.common.PageInfo;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
	private final BoardService bService;
	
//	@Autowired
//	public BoardController(BoardService bService) {
//		this.bService = bService;
//	}
//	=> Lombok을 사용 시 생성자 부분은 @RequiredArgsConstructor 으로 대체할 수 있다
//	하지만 필드부분은 꼭 작성해야한다
//	그리고 이렇게만 하면 콘솔에 Service가 빈 등록이 되어 있지 않았다는 오류가 발생할 수 있다
//	그래서 Service 클래스에 @Service 을 사용하여 빈 등록을 해야한다
	
	@GetMapping("/list")
	public ModelAndView boardList(
						SearchDTO searchDTO,
						@RequestParam(value="cpage", defaultValue="1") int currPage, 
						ModelAndView mv) {
		// 검색
//		System.out.println("검색 조건 : " + searchDTO.getCondition());
//		System.out.println("keyword : " + searchDTO.getKeyword());
		
		// 전체 게시글 수 조회
//		int listCount = bService.selectBoardCount();
		int listCount = bService.selectBoardCount(searchDTO);
//		System.out.println(listCount);
		
		// 보여줄 행 수와 페이지 수
		int pageLimit = 5;
		int boardLimit = 10;
		
		PageInfo pi = new PageInfo(listCount, currPage, pageLimit, boardLimit);
		
		ArrayList<Board> bList = bService.selectBoardList(pi, searchDTO);
		
//		for(Board b : bList) System.out.println(b);
		
		mv.addObject("pi", pi);
		mv.addObject("bList", bList);
		mv.addObject("condition", searchDTO.getCondition());
		mv.addObject("keyword", searchDTO.getKeyword());
		mv.setViewName("board/boardList");
		
		return mv;
	}
}
