package com.activities_session.model;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.activities_session.model.SessionRepository;
import com.activities_session.model.SessionVO;
import com.activity.hibernate.util.CompositeQuery.Session_Compositegory;

@Service
public class SessionService {
	
	@Autowired
	SessionRepository sessionRepository;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void addSession(SessionVO sessionVO) {
		sessionRepository.save(sessionVO);
	}
	
	public void updateSession(SessionVO sessionVO) {
		sessionRepository.save(sessionVO);
	}
	
	public void deleteSession(Integer activitySessionId) {
		if(sessionRepository.existsById(activitySessionId)) {
			sessionRepository.deleteById(activitySessionId);
		}
	}
	
	public SessionVO getOneSession(Integer activitySessionId) {
		Optional<SessionVO> optional = sessionRepository.findById(activitySessionId);
		return optional.orElse(null);// public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
		
	}
	
	public List<SessionVO> getAll(){
		return sessionRepository.findAll();
	}
	
//	public List<SessionVO> getAll(Map<String, String[]> map){
//		return Session_Compositegory.getAllSessionVOs(map, sessionFactory.openSession());
//	}

	public List<SessionVO> getActivityDateBetween(Date start, Date end){
		return sessionRepository.findByActivityDateBetween(start, end);
	}



}
