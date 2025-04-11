package com.kh.spring.board.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.board.model.dto.SearchDTO;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.board.service.BoardService;
import com.kh.spring.common.MyFileUtils;
import com.kh.spring.common.PageInfo;
import com.kh.spring.member.model.vo.Member;
import com.kh.spring.member.service.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
	private final BoardService bService;
	private final MemberService mService;
	
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
	
	@GetMapping("/enrollForm")
	public String enrollFormPage() {
		return "board/boardEnrollForm";
	}
	
	@PostMapping("/write")
	/**
	 * 
	 * @param board : Board의 필드명과 name 속성의 값이 동일해야
	 * 					스프링에서 해당 객체로 받을 수 있도록
	 * 					처리해준다
	 * @param upfile : Spring Boot Start Web 패키지에 포함된 것!
	 * 					서블릿으로 할 경우 commons-io, commons-fileupload 라이브러리
	 * @param session
	 * @param model
	 * @return
	 */
	public String boardWrite(Board board, MultipartFile upfile, HttpSession session, Model model) {
//		System.out.println(board);
		System.out.println("upfile: " + upfile.isEmpty());
		
		/**
		 * 파일은 경로를 디비에 저장하고 파일은 서버가 가지고 있는다!
		 * => 디비는 데이터를 저장하는 역할인데 쓸데없이 큰 파일을 저장할 필요가 없기 때문이다
		 * 
		 * 
		 * 원본이름은 사용자가 화면상에서 파일명을 보기위해
		 * 변경이름은 서버에 저장할 파일명
		 */
		
		// 첨부파일이 있는 경우 파일에 대한 처리
		if(!upfile.isEmpty()) {
			// 파일명 변경 -> "spring_" + 현재날짜 + 랜덤값 + 확장자
			String changeName = MyFileUtils.saveFile(upfile, session, "/resources/upfile/");
			
			// Board 객체에 파일 관련된 필드 => originName, changeName
			board.setOriginName(upfile.getOriginalFilename()); // 파일 원본명 저장
			board.setChangeName(changeName);
		}
		
		int result = bService.insertBoard(board);
//		System.out.println(result);
		
		if(result > 0) {
			session.setAttribute("alertTitle", "글 등록");
			session.setAttribute("alertIcon", "success");
			session.setAttribute("alertMsg", "글 등록에 성공했습니다!");
			return "redirect:/board/list";
		} else {
			model.addAttribute("errorMsg", "글 등록에 실패했습니다.");
			return "common/errorPage";
		}
	}
	
	@GetMapping("detail")
	public ModelAndView boardDetail(@RequestParam(required = true)int boardNo, ModelAndView mv) {
		System.out.println(boardNo);
		
		// [1] 해당 게시글의 조회수 증가
		int boardCountResult = bService.increaseCount(boardNo);
		
		if(boardCountResult > 0) {
			System.out.println("조회수 증가!");
		}

		// [2] 해당 게시글 정보 조회
		Board board = bService.selectBoardDetail(boardNo);
		
		System.out.println(board.getChangeName());
		// 이렇게 경로를 붙여주거나 아예 DB에 경로를 붙여서 저장하거나 둘 중 한 선택!
		
		// [3] 해당 게시글의 댓글 정보 조회
		ArrayList<Reply> reply = new ArrayList<>();
		reply = bService.selectReplyList(boardNo);
		
		if(board != null) {
			mv.addObject("board", board);
			mv.addObject("reply", reply);
			mv.setViewName("board/boardDetail");
		}
		
		return mv;
	}
	
	@GetMapping("/update-page")
	public ModelAndView boardUpdatePage(@RequestParam(required = true) int boardNo, ModelAndView mv) {
		// [1] 해당 게시글 정보 조회
		Board board = bService.selectBoardDetail(boardNo);

		String image = "/resources/upfile/" + board.getChangeName();
		if(board != null) {
			mv.addObject("board", board);
			mv.addObject("image", image);
			mv.setViewName("board/boardUpdate");
		}
		
		return mv;
	}
	
	@PostMapping("/update")
	public String boardUpdate(@RequestParam(required = true) int boardNo, MultipartFile upfile, Board board, HttpSession session, Model model) {
		System.out.println(boardNo);
		System.out.println(board);
		
		// 첨부파일이 있는 경우 파일에 대한 처리
		if(!upfile.isEmpty()) {
			// 파일명 변경 -> "spring_" + 현재날짜 + 랜덤값 + 확장자
			String changeName = MyFileUtils.saveFile(upfile, session, "/resources/upfile/");
			
			// Board 객체에 파일 관련된 필드 => originName, changeName
			board.setOriginName(upfile.getOriginalFilename()); // 파일 원본명 저장
			board.setChangeName(changeName);
		}
		
		int result = bService.updateBoard(board);
		
		if(result > 0) {
			session.setAttribute("alertTitle", "게시글 수정");
			session.setAttribute("alertIcon", "success");
			session.setAttribute("alertMsg", "게시글을 수정했습니다.");
			return "redirect:/board/list";
		} else {
			model.addAttribute("errorMsg", "게시글 수정에 실패했습니다.");
			return "common/errorPage";
		}
	}
	
	
	@GetMapping("/delete")
	public String boardDelete(@RequestParam(required = true) int boardNo, HttpSession session, Model model) {
		System.out.println(boardNo);
		
		int result = bService.deleteBoard(boardNo);
		
		if(result > 0) {
			session.setAttribute("alertTitle", "게시글 삭제");
			session.setAttribute("alertIcon", "success");
			session.setAttribute("alertMsg", "게시글을 삭제했습니다.");
			return "redirect:/board/list";
		} else {
			model.addAttribute("errorMsg", "게시글 등록에 실패했습니다.");
			return "common/errorPage";
		}
		
	}
}
