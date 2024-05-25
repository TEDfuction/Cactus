package com.notification.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.member.model.MemberVO;

@Component
public class NotificationService {

		@Autowired
		NotificationRepository repository;
		
		public void sendMsg(NotificationVO notificationVO) {
			repository.save(notificationVO);
		}
		
		public void orderSuccess(Integer memberId, Integer type, String content) {
			MemberVO memberVO = new MemberVO();
			memberVO.setMemberId(memberId);
			
			NotificationVO notificationVO = new NotificationVO();
			notificationVO.setMember(memberVO);
			notificationVO.setType(type);
			
			switch(type) {
				case 0:
					notificationVO.setTitle("房間訂單成立!");
					break;
				case 1:
					notificationVO.setTitle("活動訂單成立!");
					break;
				case 2:
					notificationVO.setTitle("商品訂單成立!");
					break;
				}
			
			notificationVO.setContent(content);
			
			repository.save(notificationVO);
		}
		
		public void orderCancel(Integer memberId, Integer type, String content) {
			MemberVO memberVO = new MemberVO();
			memberVO.setMemberId(memberId);
			
			NotificationVO notificationVO = new NotificationVO();
			notificationVO.setMember(memberVO);
			notificationVO.setType(type);
			
			switch(type) {
				case 0:
					notificationVO.setTitle("房間訂單取消成功!");
					break;
				case 1:
					notificationVO.setTitle("活動訂單取消成功!");
					break;
				case 2:
					notificationVO.setTitle("商品訂單取消成功!");
					break;
				}
			
			notificationVO.setContent(content);
			
			repository.save(notificationVO);
		}
		
		public List<NotificationVO> findByMemberId(Integer memberId){
			return repository.findByMemberIdByOrderByTimeDesc(memberId);
		}
		
		public List<NotificationVO> getAll() {
			return repository.getAllByOrderByTimeDesc();
		}
		
		public void readMsg(Integer notiId) {
			repository.changeStatus(notiId);
		}
		
		public Integer getNotiUnread(Integer memberId) {
			return repository.getNotiUnread(memberId);
		}
}
