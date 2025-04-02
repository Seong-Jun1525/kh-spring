package com.kh.spring.member.service;

import com.kh.spring.member.model.vo.Member;

public interface MemberService {
	int insertMember(Member m);
	Member loginMember(Member m);
	Member updateMember(Member m);
	int deleteMember(String userId);
}
