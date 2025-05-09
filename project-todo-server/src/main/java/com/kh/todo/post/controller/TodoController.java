package com.kh.todo.post.controller;

import org.springframework.web.bind.annotation.RestController;

import com.kh.todo.post.service.TodoService;

@RestController // @Controller + @ResponseBody
public class TodoController {

	private final TodoService todoService;
	public TodoController (TodoService todoService) {
		this.todoService = todoService;
	}
}
