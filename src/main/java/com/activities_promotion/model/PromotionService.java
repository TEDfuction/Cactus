package com.activities_promotion.model;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Service
public class PromotionService {

    @Autowired
    PromotionRepository promotionRepository;

    @Autowired
    private SessionFactory sessionFactory;

    public void addPromotion(PromotionVO promotionVO){
        promotionRepository.save(promotionVO);
    }

    public void updatePromotion(PromotionVO promotionVO){
        promotionRepository.save(promotionVO);
    }

    public void deletePromotion(Integer promotionId){
        if(promotionRepository.existsById(promotionId))
            promotionRepository.deleteById(promotionId);
    }

    public PromotionVO getOnePromotion(Integer promotionId){
        Optional<PromotionVO> optional = promotionRepository.findById(promotionId);
        return optional.orElse(null);
    }

    public List<PromotionVO> getAll(){
        return promotionRepository.findAll();
    }

    public List<PromotionVO> getAll(Map<String, String[]> map){
        return Promotion_Compositegory.getAllPromotionVOs(map, sessionFactory.openSession());
    }

    public List<PromotionVO> getPromotionTitle(String promotionTitle){
        return promotionRepository.findByPromotionTitle(promotionTitle);
    }

    public List<PromotionVO> getStartedAndEnd(Date promotionStarted, Date promotionEnd){

        return promotionRepository.findByStartedAndEnd(promotionStarted, promotionEnd);
    }

}
