package com.kh.todo.post.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.todo.post.model.dao.TodoMapper;
import com.kh.todo.post.model.dto.TodoDTO;
import com.kh.todo.post.model.vo.Todo;

@Service
public class TodoService {

	private final TodoMapper todoMapper;
	public TodoService(TodoMapper todoMapper) {
		this.todoMapper = todoMapper;
	}
	public List<Todo> findByTodoAll(String userId) {
		return todoMapper.findByTodoAll(userId);
	}
	public Todo insertTodo(TodoDTO todoDTO) {
		int result = todoMapper.insertTodo(todoDTO);
		
		Todo todo = result > 0 ? todoMapper.selectByLastNo(todoDTO.getUserId()) : null;
		
		return todo;
	}
}
