package com.kh.todo.post.service;

import org.springframework.stereotype.Service;

import com.kh.todo.post.model.dao.TodoMapper;

@Service
public class TodoService {

	private final TodoMapper todoMapper;
	public TodoService(TodoMapper todoMapper) {
		this.todoMapper = todoMapper;
	}
}
