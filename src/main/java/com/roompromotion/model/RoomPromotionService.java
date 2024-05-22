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

    public void addRoomPromotion(RoomPromotion roomPromotion){
        roomPromotionRepository.save(roomPromotion);
    }

    public void updateRoomPromotion(RoomPromotion roomPromotion){
        roomPromotionRepository.save(roomPromotion);
    }

    public void deleteRoomPromotion(Integer promotionId){
        if (roomPromotionRepository.existsById(promotionId))
            roomPromotionRepository.deleteById(promotionId);
    }

    public List<RoomPromotion> getAll(){
        return roomPromotionRepository.findAll();
    }

    public RoomPromotion findByPK(Integer promotionId){
        Optional<RoomPromotion> optional = roomPromotionRepository.findById(promotionId);
        return optional.orElse(null);
    }


    public List<String> findByCheckInDate(LocalDate selectCheckIn) {
        return roomPromotionRepository.findBySelectCheckIn(selectCheckIn);
    }
}
