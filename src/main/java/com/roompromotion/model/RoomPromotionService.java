package com.roompromotion.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class RoomPromotionService {

    @Autowired
    RoomPromotionRepository roomPromotionRepository;

    public void addRoomPromotion(RoomPromotionVO roomPromotion){
        roomPromotionRepository.save(roomPromotion);
    }

    public void updateRoomPromotion(RoomPromotionVO roomPromotion){
        roomPromotionRepository.save(roomPromotion);
    }

    public void deleteRoomPromotion(Integer promotionId){
        if (roomPromotionRepository.existsById(promotionId))
            roomPromotionRepository.deleteById(promotionId);
    }

    public List<RoomPromotionVO> getAll(){
        return roomPromotionRepository.findAll();
    }

    public RoomPromotionVO findByPK(Integer promotionId){
        Optional<RoomPromotionVO> optional = roomPromotionRepository.findById(promotionId);
        return optional.orElse(null);
    }


    public List<RoomPromotionVO> findByCheckInDate(LocalDate selectCheckIn) {
        return roomPromotionRepository.findBySelectCheckIn(selectCheckIn);
    }

    public Integer getByPromotionTitle(String promotionTitle){
        Integer promotionId = roomPromotionRepository.findPromotionIdByTitle(promotionTitle);
        return promotionId != null ? promotionId : null;
    }
}
