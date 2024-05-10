package com.activities_order.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface ActivityOrderRepository extends JpaRepository<ActivityOrderVO, Integer> {

    @Transactional
    @Modifying
    @Query(value = "from ActivityOrderVO  where orderTime between :start and :end")
    List<ActivityOrderVO> findByOrderTimeBetween(@Param("start") Date start, @Param("end") Date end);


}
