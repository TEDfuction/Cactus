package com.roomtypepic.model.impl;

import com.roomtypepic.model.RoomTypePicRepository;
import com.roomtypepic.model.RoomTypePicService;
import com.roomtypepic.model.RoomTypePicVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RoomTypePicImpl implements RoomTypePicService {


    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    RoomTypePicRepository roomTypePicRepository;

    @Override
    public void updateRoomTypePic(RoomTypePicVO roomTypePicVO) {roomTypePicRepository.save(roomTypePicVO);}

    public RoomTypePicVO getOneRoomPic(Integer roomTypeId) throws Exception {
        Optional<RoomTypePicVO> optional = roomTypePicRepository.findByRoomTypeId(roomTypeId);
        return optional.orElse(null);
    }
}
