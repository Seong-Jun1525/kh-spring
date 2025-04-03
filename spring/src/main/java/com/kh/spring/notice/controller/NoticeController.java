package com.kh.spring.notice.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.notice.model.vo.Notice;
import com.kh.spring.notice.service.NoticeService;

import jakarta.servlet.http.HttpSession;

/**
 * 해당 클래스를 Controller로 등록하기 위해서 @Controller 어노테이션으로 사용하여 등록한다!
 * 
 * 해당 컨트롤러로 들어오는 매핑주소가 공통 경로로 들어올 경우 @RequestMapping("/공통경로명")
 * 어노테이션을 사용한다!
 */

@Controller
@RequestMapping("/notice") // 주소 시험 나옴!
public class NoticeController {

	/**
	 * 생성자 주입 방식으로 서비스를 생성한다
	 * 
	 * 생성자 주입 방식
	 * => 생성자를 통해 의존 관계를 주입하는 방식
	 */
	private final NoticeService nService;

	@Autowired
	public NoticeController(NoticeService nService) {
		this.nService = nService;
	}

	// 공지사항 목록 페이지 연결
	@GetMapping("/list") // 주소 시험 나옴!
	public ModelAndView noticeList(ModelAndView mv) {
		
		ArrayList<Notice> nList = nService.selectNoticeList();
		
		System.out.println(nList);
		
		// ModelAndView : 스프링에서 제공해주는 객체
		// - Model : 데이터를 key-value 형태로 저장할 수 있는 공간(단독 사용)
		// - View : 응답 페이지에 대한 정보를 저장할 수 있는 공간 ( 단독 사용 불가 => ModelAndView )
		
		mv.addObject("nList", nList);
		mv.setViewName("notice/noticeList");

		return mv;
	}
	
	// 공지사항 상세페이지 연결
	@GetMapping("/noticeDetail")
	public ModelAndView noticeDetail(@RequestParam(defaultValue="0") int noticeNo, ModelAndView mv) {
		
		System.out.println(noticeNo);
		
		Notice n = nService.selectNoticeById(noticeNo);
//		System.out.println(n);

		mv.addObject("notice", n);
		mv.setViewName("notice/noticeDetail");

		return mv;
	}
	
	// 공지사항 등록 페이지 연결
	@GetMapping("/write")
	public String noticeWritePage() {
		return "notice/enrollForm";
	}
	
	// 공지사항 등록 요청
	@PostMapping("/insertNotice")
	public String insertNotice(Notice notice, HttpSession session, Model model) {
		
		System.out.println(notice);
		
		int result = nService.insertNotice(notice);
		
		if(result > 0) {
			session.setAttribute("alertTitle", "공지사항 작성");
			session.setAttribute("alertMsg", "공지사항 작성에 성공했습니다!");
			return "redirect:/";
		} else {
			model.addAttribute("errorMsg", "공지사항 작성에 실패했습니다.");
			return "common/errorPage";
		}
	}
	
	// 공지사항 수정 페이지 연결
	@GetMapping("/updateNoticePage")
	public ModelAndView updateNoticePage(@RequestParam(defaultValue="0") int noticeNo, ModelAndView mv) {
		
		System.out.println(noticeNo);
		
		Notice n = nService.selectNoticeById(noticeNo);
		System.out.println(n);

		mv.addObject("notice", n);
		mv.setViewName("notice/noticeUpdate");

		return mv;
	}
	
	// 공지사항 수정 요청
	@PostMapping("/updateNotice")
	public String updateNotice(@RequestParam(defaultValue="0") int noticeNo, Notice notice, HttpSession session, Model model) {
		System.out.println("post notice : " + notice);
		
		int result = nService.updateNotice(notice);
		if(result > 0) {
			session.setAttribute("alertTitle", "공지사항 수정");
			session.setAttribute("alertMsg", "공지사항 수정에 성공했습니다!");
			return "redirect:/notice/noticeDetail?noticeNo=" + noticeNo;
		} else {
			model.addAttribute("errorMsg", "공지사항 수정에 실패했습니다.");
			return "common/errorPage";
		}
	}
	
	// 공지사항 삭제 요청
	@GetMapping("/deleteNotice")
	public String deleteNotice(@RequestParam(defaultValue="0") int noticeNo, HttpSession session, Model model) {
		System.out.println(noticeNo);
		
		int result = nService.deleteNotice(noticeNo);
		
		if(result > 0) {
			session.setAttribute("alertTitle", "공지사항 삭제");
			session.setAttribute("alertMsg", "공지사항 삭제에 성공했습니다!");
			return "redirect:/notice/list";
		} else {
			model.addAttribute("errorMsg", "공지사항 삭제에 실패했습니다.");
			return "common/errorPage";
		}
	}
	
	// 공지사항 검색 요청
	@GetMapping("/search")
	public ModelAndView searchNotice(@RequestParam String keyword, ModelAndView mv) {
		// System.out.println(keyword);
		
		ArrayList<Notice> nSearchList = nService.selectNoticeByNoticeTitle(keyword);
		
		mv.addObject("nList", nSearchList);
		mv.setViewName("notice/noticeList");

		return mv;
	}
}
