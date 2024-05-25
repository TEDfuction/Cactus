package com.member.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MemberService {
	
	@Autowired
	MemberRepository repository;
	
	
	//複合查詢才需要	
//	@Autowired 
//	private SessionFactory sessionFactory;
	
	
	public void addMember(MemberVO memVO) {
		repository.save(memVO);
	}
	
	public void updateMember(MemberVO memVO) {
		repository.save(memVO);
	}
	
	public List<MemberVO> getAll() {
		return repository.findAll();
	}
	
	public MemberVO findByPK(Integer memberId){
		Optional<MemberVO> optional = repository.findById(memberId);
		return optional.orElse(null); //orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}
	
	public List<MemberVO> findByName(String memberName){
		return repository.findByName(memberName);
	}

	public MemberVO findByEmail(String email) {
		return repository.findByEmail(email);

	}
	
	public void updatePassword(String newPassword, Integer memberId) {
		repository.updatePassword(newPassword, memberId);
	}

}
