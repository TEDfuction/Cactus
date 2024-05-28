package com.room.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Component
public class RoomService {

    @Autowired
    RoomRepository repository;

    public void addRoom(RoomVO room){
        repository.save(room);
    }

    public void updateRoom(RoomVO room){
        repository.save(room);
    }

    public void deleteRoom(Integer roomId){
        if (repository.existsById(roomId))
            repository.deleteById(roomId);
    }

    public List<RoomVO> getAll(){
        return repository.findAll();
    }

    public RoomVO findByPK(Integer roomId){
        Optional<RoomVO> optional = repository.findById(roomId);
        return optional.orElse(null);
    }

//    public RoomVO findByRoomTypeId(Integer roomTypeId) {
//        List<RoomVO> rooms = repository.findByRoomTypeId(roomTypeId);
//        if (rooms.isEmpty()) {
//            throw new EntityNotFoundException("No rooms found with RoomTypeId: " + roomTypeId);
//        }
//        return rooms.get(0);
//    }

    public RoomVO getByRoomPrice(Integer roomPrice){
        RoomVO rooomlist = repository.findByRoomPrice(roomPrice);
        return rooomlist;
    }

}
