package com.activities_attendees.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AttendeesRepository extends JpaRepository<AttendeesVO, Integer> {

    @Query(value = "from AttendeesVO  where attendeesName like ?1")
    List<AttendeesVO> findByName(String attendeesName);

    @Query(value = "from AttendeesVO where attendeesEmail like ?1")
    List<AttendeesVO> findByEmail(String attendeesEmail);

}
