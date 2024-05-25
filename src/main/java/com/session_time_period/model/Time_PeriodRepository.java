package com.session_time_period.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
public interface Time_PeriodRepository extends JpaRepository<Time_PeriodVO, Integer>{



    @Transactional
    @Modifying
    @Query("SELECT tp FROM Time_PeriodVO tp WHERE tp.sessionVO.activitySessionId = :activitySessionId")
    List<Time_PeriodVO> findByActivitySessionId(@Param("activitySessionId") Integer activitySessionId);




}
