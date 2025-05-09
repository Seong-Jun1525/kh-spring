package com.kh.todo.post.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kh.todo.post.model.dto.TodoDTO;
import com.kh.todo.post.model.vo.Todo;
import com.kh.todo.post.service.TodoService;
import com.kh.todo.user.model.vo.User;

import jakarta.servlet.http.HttpSession;

@RestController // @Controller + @ResponseBody
public class TodoController {

	private final TodoService todoService;
	public TodoController (TodoService todoService) {
		this.todoService = todoService;
	}
	
	/**
	 * 할일 목록 조회
	 * [GET] /todo
	 * @return List<Todo> 할일 목록 전체
	 */
	@GetMapping("/todo")
	public List<Todo> getAllTodoList(HttpSession session) {
		User user = (User)session.getAttribute("userInfo");
		String userId = user.getUserId();
		List<Todo> todoList = todoService.findByTodoAll(userId);
		
		return todoList != null ? todoList : null;
	}
	
	
	/**
	 * 할일 추가
	 * [POST] /todo
	 * @param {content: 할 일 내용} :: application/json
	 * @return Todo 추가된 할일 정보
	 */
	@PostMapping("/todo")
	public ResponseEntity<Object> insertTodo(@RequestBody TodoDTO todoDTO, HttpSession session) {
		/**
		 * ResponseEntity를 커스텀해서 사용할 수 있다
		 * 
		 * ResponseTodo<Todo>
		 * 
		 * ResponseTodo<T> {
		 * 	private int code;
		 *  private String message;
		 *  private T data;
		 * }
		 */
		System.out.println(todoDTO);
		User user = (User)session.getAttribute("userInfo");
		todoDTO.setUserId(user.getUserId());
		
		Todo todo = todoService.insertTodo(todoDTO);
		
		if(todo != null) {
			return ResponseEntity.status(HttpStatus.OK).body(todo);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("할 일 추가에 실패했습니다.");
			// 클라이언트 쪽에서는 자세한 오류 내용을 알 필요가 없다
		}
	}
}
