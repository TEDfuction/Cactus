package com.activities_session.model;


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


}
