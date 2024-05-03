package com.member.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface MemberRepository extends JpaRepository<MemberVO, Integer> {
	
	//姓名模糊查詢用
	@Query(value = "from MemberVO where memberName like ?1 ")
	List<MemberVO> findByName(String memberName);
	
	
	//Email查詢，session存取用
	@Query(value = "from MemberVO where email=?1 ")
	MemberVO findByEmail(String email);
	
	
	//忘記密碼重改用
	@Transactional
	@Modifying
	@Query(value = "update MemberVO m set m.password=?1 where m.memberId=?2")
	void updatePassword(String newPassword ,Integer memberId );
}
