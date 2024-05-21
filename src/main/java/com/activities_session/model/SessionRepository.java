package com.activities_session.model;


import com.session_time_period.model.Time_PeriodVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
public interface SessionRepository extends JpaRepository<SessionVO, Integer>{

    @Transactional
    @Modifying
    @Query(value = "from SessionVO where activityDate between :start and :end ")
    List<SessionVO> findByActivityDateBetween(@Param("start") Date start, @Param("end") Date end);

//    @Transactional
//    @Modifying
//    @Query("SELECT tp.timePeriod FROM SessionVO s JOIN s.time_periodVO tp WHERE s.activityDate = :activityDate")
//    List<Time> findTimePeriodByActivityDate(@Param("activityDate") Date activityDate);

//    @Transactional
//    @Modifying
//    @Query("SELECT tp FROM SessionVO s JOIN s.time_periodVO tp WHERE s.activityDate = :activityDate")
//    List<Time_PeriodVO> findTimePeriodByActivityDate(@Param("activityDate") Date activityDate);

    @Transactional
    @Modifying
    @Query("SELECT tp FROM Time_PeriodVO tp JOIN tp.sessionVO s WHERE s.activityDate = :activityDate")
    List<Time_PeriodVO> findTimePeriodsByActivityDate(@Param("activityDate") Date activityDate);

    @Transactional
    @Modifying
    @Query("SELECT DISTINCT s.activityDate FROM SessionVO s")
    List<Date> findDistinctActivityDates();

    @Transactional
    @Modifying
    List<SessionVO> findByItemVO_ActivityId(Integer activityId);





}
