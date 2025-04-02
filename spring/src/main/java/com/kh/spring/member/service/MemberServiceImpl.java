package com.kh.spring.member.service;

import org.springframework.stereotype.Service;

import com.kh.spring.member.model.dao.MemberDAO;
import com.kh.spring.member.model.vo.Member;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor // 필드로 선언된 객체(빈)를 생성자 방식으로 주입 기능
@Service // @Component 어노테이션 보다 좀 더 구체화된 객체를 의미 => 서비스빈
public class MemberServiceImpl implements MemberService {

	/**
	 * DAO 객체에 대한 DI 처리
	 */
	
	private final MemberDAO mDAO;
	
	/* lombok을 쓰지 않을 경우 생성자 주입 방식
	 	@Autowired
		public MemberServiceImpl(MemberDAO mDAO) {
			this.mDAO = mDAO;
		}
	 */

	@Override
	public int insertMember(Member m) {
		return mDAO.insertMember(m);
	}

	@Override
	public Member loginMember(Member m) {
		return mDAO.loginMember(m);
	}

	@Override
	public Member updateMember(Member m) {
		int result = mDAO.updateMember(m);
		if(result > 0) {
			Member member = mDAO.selectMemberById(m.getUserId());
			return member;
		}
		return null;
	}

	@Override
	public int deleteMember(String userId) {
		// TODO Auto-generated method stub
		return 0;
	}

}
