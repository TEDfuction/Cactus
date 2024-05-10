package com.activities_promotion.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface PromotionRepository extends JpaRepository<PromotionVO, Integer> {

    @Transactional
    @Modifying
    @Query(value = "from PromotionVO where promotionTitle like ?1")
    List<PromotionVO> findByPromotionTitle(String promotionTitle);

    @Transactional
    @Modifying
    @Query(value = "from PromotionVO where ?1 >= promotionStarted and ?2 <= promotionEnd")
    List<PromotionVO> findByStartedAndEnd(Date promotionStarted, Date promotionEnd);

}
