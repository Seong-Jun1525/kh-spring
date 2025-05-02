package com.kh.todo.member.service;

import org.springframework.stereotype.Service;

import com.kh.todo.member.model.dao.UserMapper;
import com.kh.todo.member.model.vo.UserDTO;

@Service
public class UserService { // 비즈니스 로직 담당
	private final UserMapper userMapper;
	
	public UserService(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	/**
	 * 아이디 중복체크
	 * 
	 * @param userId
	 * @return true: 사용가능, false : 중복불가
	 */
	public boolean checkId(String userId) {
		// DB에서 아이디와 일치하는 개수를 조회하여
		// 0인 경우는 true 리턴, 0보다 큰 경우 false 리턴
		return userMapper.countByUserId(userId) == 0;
	}

	public int register(UserDTO userDTO) {
		// TODO Auto-generated method stub
		return userMapper.insertUser(userDTO);
	}
}
