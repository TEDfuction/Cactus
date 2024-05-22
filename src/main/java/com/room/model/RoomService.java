package com.room.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RoomService {

    @Autowired
    RoomRepository repository;

    public void addRoom(Room room){
        repository.save(room);
    }

    public void updateRoom(Room room){
        repository.save(room);
    }

    public void deleteRoom(Integer roomId){
        if (repository.existsById(roomId))
            repository.deleteById(roomId);
    }

    public List<Room> getAll(){
        return repository.findAll();
    }

    public Room findByPK(Integer roomId){
        Optional<Room> optional = repository.findById(roomId);
        return optional.orElse(null);
    }

}
