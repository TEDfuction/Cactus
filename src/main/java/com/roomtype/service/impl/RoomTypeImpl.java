package com.roomtype.service.impl;

import com.roomtype.dto.RoomTypeVORequest;
import com.roomtype.dto.RoomTypeUpdate;
import com.roomtype.model.RoomTypeVO;
import com.roomtype.service.RoomTypeService;
import com.roomtype.model.RoomTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class RoomTypeImpl implements RoomTypeService {

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void addRoomType(RoomTypeVORequest roomTypeVORequest) {
        RoomTypeVO roomTypeVO = new RoomTypeVO();
        modelMapper.map(roomTypeVORequest,roomTypeVO);

    }

        public RoomTypeVO getOneRoomType(Integer roomTypeId) {
            Optional<RoomTypeVO> optional = roomTypeRepository.findById(roomTypeId);
            return null;
        }



    @Override
    public void updateRoomType(RoomTypeUpdate roomtypeUpdate){
        RoomTypeVO roomTypeVO = new RoomTypeVO();
        modelMapper.map(roomtypeUpdate,roomTypeVO);

        roomTypeRepository.save(roomTypeVO);
    }

//    @Override
//    public void updateRTS(Integer roomTypeId, Boolean roomTypeStatus) {
//        RoomTypeVO roomTypeVO = new RoomTypeVO();
//        modelMapper.map(roomTypeVO,roomTypeVO);
//        roomTypeDao.save(roomTypeVO);
//    }

    @Override
    public List<RoomTypeVO> getAllRoomTypes() {
        return null;
    }
}
