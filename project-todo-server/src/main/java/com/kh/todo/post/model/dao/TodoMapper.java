package com.kh.todo.post.model.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class TodoMapper {
	private final SqlSession sqlSession;
	public TodoMapper(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
}
