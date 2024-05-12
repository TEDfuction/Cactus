package com.session_time_period.model;

import com.session_time_period.model.Time_PeriodRepository;
import com.session_time_period.model.Time_PeriodVO;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Time_PeriodService {

    @Autowired
    Time_PeriodRepository timePeriodRepository;

//    @Autowired
//    private SessionFactory sessionFactory;

    public void addTimePeriod(Time_PeriodVO time_PeriodVO){

        timePeriodRepository.save(time_PeriodVO);
    };

    public void updateTimePeriod(Time_PeriodVO time_PeriodVO){
        timePeriodRepository.save(time_PeriodVO);
    }

    public void deleteTimePeriod(Integer sessionTimePeriodId){
        if (timePeriodRepository.existsById(sessionTimePeriodId)){
            timePeriodRepository.deleteById(sessionTimePeriodId);
        }
    }

    public Time_PeriodVO getOneTimePeriod(Integer sessionTimePeriodId){
        Optional<Time_PeriodVO> optional = timePeriodRepository.findById(sessionTimePeriodId);
        return optional.orElse(null);
    }

    public List<Time_PeriodVO> getAll(){
        return timePeriodRepository.findAll();
    }

    public List<Time_PeriodVO> getActivitySessionId(Integer activitySessionId){
        return timePeriodRepository.findByActivitySessionId(activitySessionId);
    }


}
