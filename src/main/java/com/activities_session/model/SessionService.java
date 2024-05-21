package com.activities_session.model;

import java.sql.Time;
import java.util.*;
import java.util.stream.Collectors;

import com.session_time_period.model.Time_PeriodVO;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class SessionService {
	
	@Autowired
	SessionRepository sessionRepository;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public SessionVO addSession(SessionVO sessionVO) {

		return sessionRepository.save(sessionVO);
	}
	
	public SessionVO updateSession(SessionVO sessionVO) {

		return sessionRepository.save(sessionVO);
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

//	public List<Time> getTimePeriodByActivityDate(Date activityDate){
//		return sessionRepository.findTimePeriodByActivityDate(activityDate);
//	}

//	public List<Time_PeriodVO> getTimePeriodByActivityDate(Date activityDate){
//		return sessionRepository.findTimePeriodByActivityDate(activityDate);
//	}
//	public List<TimePeriodDTO> getTimePeriodsByActivityDate(Date activityDate) {
//		List<Time_PeriodVO> timePeriodVOs = sessionRepository.findTimePeriodsByActivityDate(activityDate);
//		List<TimePeriodDTO> dtos = new ArrayList<>();
//		for (Time_PeriodVO vo : timePeriodVOs) {
//			TimePeriodDTO dto = new TimePeriodDTO();
//			dto.setSessionTimePeriodId(vo.getSessionTimePeriodId());
//			dto.setTimePeriod(vo.getTimePeriod());
//			dtos.add(dto);
//		}
//		return dtos;
//	}

	//透過日期取得時段
	public List<Time_PeriodVO> getTimePeriodsByActivityDate (Date activityDate) {
		return sessionRepository.findTimePeriodsByActivityDate(activityDate);
	}

	//取得時段的id
	public Set<Integer> getTimePeriodId(Integer activitySessionId){
		SessionVO sessionVO = sessionRepository.findById(activitySessionId)
				.orElseThrow(() -> new RuntimeException("activitySessionId not found"));
		return sessionVO.getTime_periodVO().stream().map(Time_PeriodVO::getSessionTimePeriodId)
				.collect(Collectors.toSet());

	}

	//取得所有的時段
	public Set<Time> getTimePeriods(Integer activitySessionId){
		SessionVO sessionVO = sessionRepository.findById(activitySessionId)
				.orElseThrow(() -> new RuntimeException("activitySessionId not found"));
		return sessionVO.getTime_periodVO().stream()
				.map(Time_PeriodVO::getTimePeriod)
				.collect(Collectors.toSet());
	}



	public List<Date> getAvailableDates() {
		return sessionRepository.findDistinctActivityDates();
	}

	//透過activityId取得所有的日期
	public List<SessionVO> getSessionsByActivityId(Integer activityId) {
		return sessionRepository.findByItemVO_ActivityId(activityId);
	}







}
