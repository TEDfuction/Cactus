package com.roomorder.service;

import com.roomorder.model.RoomOrderVO;

import java.time.LocalDate;
import java.util.List;

public interface RoomOrderService {

    List<RoomOrderVO> getAllRoomOrder();

    List<RoomOrderVO> getOneRoomOrder(Integer memberId) throws Exception;

    List<RoomOrderVO> getDateRO(LocalDate start, LocalDate end) throws Exception;

    List<RoomOrderVO> getOrderDateRO(LocalDate ROstart, LocalDate ROend) throws Exception;

    List <RoomOrderVO> addRoomOrder(RoomOrderVO roomOrderVO);
}
