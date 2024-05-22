package com.roompromotion.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RoomPromotionService {

    @Autowired
    RoomPromotionRepository repository;

    public void addRoomPromotion(RoomPromotion roomPromotion){
        repository.save(roomPromotion);
    }

    public void updateRoomPromotion(RoomPromotion roomPromotion){
        repository.save(roomPromotion);
    }

    public void deleteRoomPromotion(Integer promotionId){
        if (repository.existsById(promotionId))
            repository.deleteById(promotionId);
    }

    public List<RoomPromotion> getAll(){
        return repository.findAll();
    }

    public RoomPromotion findByPK(Integer promotionId){
        Optional<RoomPromotion> optional = repository.findById(promotionId);
        return optional.orElse(null);
    }



}
