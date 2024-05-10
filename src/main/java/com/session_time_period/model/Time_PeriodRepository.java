package com.session_time_period.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface Time_PeriodRepository extends JpaRepository<Time_PeriodVO, Integer>{

    @Transactional
    @Modifying
    @Query(value = "from Time_PeriodVO where sessionVO = ?1")
    List<Time_PeriodVO> findByActivitySessionId(Integer activitySessionId);

}
