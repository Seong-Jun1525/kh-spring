package com.kh.spring.member.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.kh.spring.member.model.vo.Member;

@Mapper // Mybatis를 사용해서 빈을 등록
public interface MemberDAO {

	/* 회원가입 */
	int insertMember(Member member);

	/* 로그인 */
	Member loginMember(Member m);

	/* 회원 정보 수정 */
	int updateMember(Member m);

	Member selectMemberById(String userId);

	int deleteMember(String userId);
	
}
