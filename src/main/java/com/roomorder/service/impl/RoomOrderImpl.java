package com.roomorder.service.impl;

import com.roomorder.model.RoomOrderRepository;
import com.roomorder.model.RoomOrderVO;
import com.roomorder.service.RoomOrderService;
import com.roomtype.model.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static groovyjarjarantlr4.v4.gui.Trees.save;

// 搭配 JSP / Thymeleaf 後端渲染畫面，將交易動作至於 view filter

@Component
public class RoomOrderImpl implements RoomOrderService {

	@Autowired
	private RoomTypeRepository roomTypeRepository;
	@Autowired
	private RoomOrderRepository roomOrderRepository;

//	@Autowired
//	private ModelMapper modelMapper;

	@Override
	public RoomOrderVO getOneRoomOrderById(Integer roomOrderId) {
		Optional<RoomOrderVO> optional = roomOrderRepository.findById(roomOrderId);
		return optional.orElse(null);
	}

	@Override
	public void updateOneRoomOrder(RoomOrderVO roomOrderVO){
		roomOrderRepository.save(roomOrderVO);
	}


	@Override
	public List<RoomOrderVO> getAllRoomOrder() {
		return roomOrderRepository.findAll();
	}

	@Override
	public List<RoomOrderVO> getOneRoomOrder(Integer memberId) throws Exception {
		List<RoomOrderVO> getOneList = roomOrderRepository.findByMemberId(memberId);
		return getOneList;
//				.orElseThrow(() -> new Exception("Room order not found for member ID: " + memberId));
	}


	public List<RoomOrderVO> getDateRO(LocalDate start, LocalDate end) throws Exception{
		List<RoomOrderVO> findByCheckInDate = roomOrderRepository.findByCheckInDate(start,end);
		return findByCheckInDate;
	}

	public List<RoomOrderVO> getOrderDateRO(LocalDate ROstart, LocalDate ROend) throws Exception{
		List<RoomOrderVO> findByRoomOrderDate = roomOrderRepository.findByRoomOrderDate(ROstart,ROend);
		return findByRoomOrderDate;
	}

	@Override
	public List<RoomOrderVO> addRoomOrder(RoomOrderVO roomOrderVO) {
		List<RoomOrderVO> insertRoomOrder = Collections.singletonList(roomOrderRepository.save(roomOrderVO));
		return insertRoomOrder;
	}

}
