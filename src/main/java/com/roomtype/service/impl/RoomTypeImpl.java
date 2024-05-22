package com.roomtype.service.impl;



import com.room.model.RoomRepository;
import com.room.model.RoomVO;
import com.roomtype.dto.RoomTypeUpdate;
import com.roomtype.dto.RoomTypeVORequest;
import com.roomtype.model.RoomTypeRepository;
import com.roomtype.model.RoomTypeVO;
import com.roomtype.service.RoomTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class RoomTypeImpl implements RoomTypeService {

    @Autowired
    private RoomTypeRepository roomTyperepository;


    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public RoomTypeVO getRoomTypeById(Integer roomTypeId) {
        return roomTyperepository.getById(roomTypeId);
    }


    @Override
    public void addRoomType(RoomTypeVORequest roomTypeVORequest) {
        RoomTypeVO roomTypeVO = new RoomTypeVO();
        modelMapper.map(roomTypeVORequest, roomTypeVO);
        roomTyperepository.save(roomTypeVO);
    }




    @Override
    public RoomTypeVO getOneRoomType(Integer roomTypeId) {
        Optional<RoomTypeVO> optional = roomTyperepository.findById(roomTypeId);
        return null;
    }


    @Override
    public void updateRoomType(RoomTypeUpdate roomtypeUpdate) {
        RoomTypeVO roomTypeVO = new RoomTypeVO();
        modelMapper.map(roomtypeUpdate, roomTypeVO);

        roomTyperepository.save(roomTypeVO);
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


    @Override
    public List<RoomTypeVO> getRTStatus() {

        List<RoomTypeVO> getall = roomTyperepository.findAll();
        List<RoomTypeVO> list = new ArrayList<>();
//        List<List<RoomTypePicVO>> roomTypePic = new ArrayList<>();
        for (RoomTypeVO r : getall) {
            if (r.getRoomTypeStatus()) {
                list.add(r);
            }
        }
//        System.out.println(list.toString());
        return list;
    }


    @Override
    public List<Object[]> getAvailableRoomTypes(String roomTypeName, Date checkInDate, Date checkOutDate, Integer roomGuestAmount) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(checkOutDate);
        cal.add(Calendar.DATE, -1);
        Date adjustedCheckOutDate = cal.getTime();

        // 打印查询参数
        System.out.println("roomTypeName: " + roomTypeName);
        System.out.println("checkInDate: " + checkInDate);
        System.out.println("checkOutDate: " + adjustedCheckOutDate);
        System.out.println("roomGuestAmount: " + roomGuestAmount);

        List<Object[]> availableRooms = roomTyperepository.findAvailableRoomTypes(roomTypeName, checkInDate, checkOutDate, roomGuestAmount);

        // 打印查詢結果
        for (Object[] room : availableRooms) {
            System.out.println("Room: " + Arrays.toString(room));
        }

        return availableRooms;
    }

    @Override
    public Optional<RoomTypeVO> getRoomTypeIdByName(String roomTypeName) {
        return roomTyperepository.findByRoomTypeName(roomTypeName);
    }

    @Override
    public List<Integer> getRoomGuestAmounts(Integer roomTypeId) {
        List<Integer> guestAmounts = new ArrayList<>();
        List<RoomVO> rooms = roomRepository.findByRoomTypeId(roomTypeId);
        for (RoomVO room : rooms) {
            guestAmounts.add(room.getRoomGuestAmount());
        }
        return guestAmounts;
    }
}
