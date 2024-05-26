package com.activities_attendees.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class AttendeesService {

    @Autowired
    AttendeesRepository attendeesRepository;


    public void addAttendees(AttendeesVO attendeesVO){
        attendeesRepository.save(attendeesVO);
    }

    public void updateAttendees(AttendeesVO attendeesVO) throws SQLException {
//        // 检查电子邮件是否已经存在（排除当前记录）
//        if (attendeesRepository.existsByEmailExcludingId(attendeesVO.getAttendeesEmail(), attendeesVO.getActivityAttendeesId())) {
//            throw new SQLException("Duplicate entry for email: " + attendeesVO.getAttendeesEmail());
//        }
        attendeesRepository.save(attendeesVO);
    }

    public boolean existsByEmailExcludingId(String attendeesEmail, Integer activityAttendeesId) {
        return attendeesRepository.existsByEmailExcludingId(attendeesEmail, activityAttendeesId);
    }

    public void deleteAttendees(Integer activityAttendeesId){
        if(attendeesRepository.existsById(activityAttendeesId))
            attendeesRepository.deleteById(activityAttendeesId);
    }

    public AttendeesVO getOneAttendees(Integer activityAttendeesId){
        Optional<AttendeesVO> optional = attendeesRepository.findById(activityAttendeesId);
        return optional.orElse(null);

    }

    public List<AttendeesVO> getAll(){
        return attendeesRepository.findAll();
    }

    public List<AttendeesVO> getAttendeesName(String attendeesName){
        return attendeesRepository.findByName(attendeesName);
    }

    public List<AttendeesVO> getAttendeesEmail(String attendeesEmail){
        return attendeesRepository.findByEmail(attendeesEmail);
    }

}

