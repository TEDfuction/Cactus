package com.roomorder.service.impl;

import com.roomorder.model.RoomOrderVO;
import com.roomorder.service.RoomOrderService;
import com.roomtype.model.RoomTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

// 搭配 JSP / Thymeleaf 後端渲染畫面，將交易動作至於 view filter

@Component
public class RoomOrderImpl implements RoomOrderService {
	// 一個 service 實體對應一個 dao 實體
	@Autowired
	private RoomTypeRepository roomTypeRepository;

//	@Autowired
//	private ModelMapper modelMapper;


	@Override
	public List<RoomOrderVO> getAllRoomOrder() {
		return null;
	}

}
