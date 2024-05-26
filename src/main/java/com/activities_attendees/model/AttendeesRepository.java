package com.activities_attendees.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttendeesRepository extends JpaRepository<AttendeesVO, Integer> {

    @Query(value = "from AttendeesVO  where attendeesName like ?1")
    List<AttendeesVO> findByName(String attendeesName);

    @Query(value = "from AttendeesVO where attendeesEmail like ?1")
    List<AttendeesVO> findByEmail(String attendeesEmail);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM AttendeesVO a WHERE a.attendeesEmail = :attendeesEmail AND a.activityAttendeesId <> :activityAttendeesId")
    boolean existsByEmailExcludingId(@Param("attendeesEmail") String attendeesEmail, @Param("activityAttendeesId") Integer activityAttendeesId);


}

