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

@Controller
@RequestMapping("/notice") // 주소 시험 나옴!
public class NoticeController {
	private final NoticeService nService;

	@Autowired
	public NoticeController(NoticeService nService) {
		this.nService = nService;
	}

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
	
	@GetMapping("/noticeDetail")
	public ModelAndView noticeDetail(@RequestParam(defaultValue="0") int noticeNo, ModelAndView mv) {
		
		System.out.println(noticeNo);
		
		Notice n = nService.selectNoticeById(noticeNo);
		System.out.println(n);

		mv.addObject("notice", n);
		mv.setViewName("notice/noticeDetail");

		return mv;
	}
	
	@GetMapping("/write")
	public String noticeWritePage() {
		return "notice/enrollForm";
	}
	
	@PostMapping("/insertNotice")
	public String insertNotice(Notice notice, HttpSession session, Model model) {
		
		System.out.println(notice);
		
		int result = nService.insertNotice(notice);
		
		if(result > 0) {
			session.setAttribute("alertMsg", "글 작성에 성공했습니다!");
			return "redirect:/";
		} else {
			model.addAttribute("errorMsg", "게시글 작성에 실패했습니다.");
			return "common/errorPage";
		}
	}
	
	@GetMapping("/updateNoticePage")
	public ModelAndView updateNoticePage(@RequestParam(defaultValue="0") int noticeNo, ModelAndView mv) {
		
		System.out.println(noticeNo);
		
		Notice n = nService.selectNoticeById(noticeNo);
		System.out.println(n);

		mv.addObject("notice", n);
		mv.setViewName("notice/noticeUpdate");

		return mv;
	}
	
	@PostMapping("/updateNotice/*")
	public String updateNotice(@RequestParam(defaultValue="0") int noticeNo, Notice notice, HttpSession session, Model model) {
		System.out.println(notice);
		
		int result = nService.updateNotice(notice);
		if(result > 0) {
			session.setAttribute("alertMsg", "글 작성에 성공했습니다!");
			return "redirect:/";
		} else {
			model.addAttribute("errorMsg", "게시글 작성에 실패했습니다.");
			return "common/errorPage";
		}
	}
}
