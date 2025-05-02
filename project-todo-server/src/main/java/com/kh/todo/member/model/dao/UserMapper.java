package com.kh.todo.member.model.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.kh.todo.member.model.vo.UserDTO;

@Repository
public class UserMapper {
	// class => 빈 등록 : @Repository, DB 연동 : SqlSession
	
	// interface => 빈 등록 : @Mapper, DB 연동 : 추상메서드 정의. mapper.xml의 namespace에 해당 인터페이스 전체 경로 설정!
	private final SqlSession sqlSession;
	
	public UserMapper(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	// 전달된 아이디와 일치하는 개수 조회
	public int countByUserId(String userId) {
		return sqlSession.selectOne("UserMapper.countByUserId", userId);
	}

	public int insertUser(UserDTO userDTO) {
		// TODO Auto-generated method stub
		return sqlSession.insert("UserMapper.insertUser", userDTO);
	}
}
