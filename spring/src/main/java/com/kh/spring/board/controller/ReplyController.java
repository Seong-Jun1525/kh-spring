package com.kh.spring.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.spring.board.model.vo.Reply;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyController {

	@PostMapping("/write")
	public String replyWrite(Reply reply) {
		System.out.println("ReplyController!!");
		
		System.out.println(reply);
		
		return "board/boardList";
	}
}
