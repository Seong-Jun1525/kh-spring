package com.kh.todo.post.model.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.kh.todo.post.model.dto.TodoDTO;
import com.kh.todo.post.model.vo.Todo;

@Repository
public class TodoMapper {
	private final SqlSession sqlSession;
	public TodoMapper(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	public List<Todo> findByTodoAll(String userId) {
		return sqlSession.selectList("TodoMapper.findByTodoAll", userId);
	}
	public int insertTodo(TodoDTO todoDTO) {
		return sqlSession.insert("TodoMapper.insertTodo", todoDTO);
	}
	public Todo selectByLastNo(String userId) {
		return sqlSession.selectOne("TodoMapper.selectByLastNo", userId);
	}
}
