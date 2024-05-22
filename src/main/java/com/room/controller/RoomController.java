package com.room.controller;


import com.room.model.RoomVO;
import com.room.model.RoomService;

import com.roomtype.model.RoomTypeRepository;
import com.roomtype.model.RoomTypeVO;
import com.roomtype.service.impl.RoomTypeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/room")
public class RoomController {

    @Autowired
    RoomService roomService;
    @Autowired
    RoomTypeImpl roomTypeImpl;
    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @GetMapping("/searchRoom")
    public String searchPage() {
        return "back_end/room/roomSearch";
    }


    @GetMapping("/getAllRoom")
    public String getAll(ModelMap model) {

        List<RoomVO> rooms = roomService.getAll();

        model.addAttribute("rooms", rooms);

        return "back_end/room/showAllRoom";
    }

    @GetMapping("/addRoom")
    public String addRoom(ModelMap model) {
        RoomVO room = new RoomVO();
        List<RoomTypeVO> roomType = roomTypeRepository.findAll();

        model.addAttribute("roomTypeListData", roomType);
        model.addAttribute("room", room);
        return "back_end/room/addRoom";
    }

    @PostMapping("/insertRoom")
    public String insert(
            @Valid RoomVO room,
            BindingResult result,
            ModelMap model){

        if (result.hasErrors()) {
            List<RoomTypeVO> roomType = roomTypeRepository.findAll();
            model.addAttribute("roomTypeListData", roomType);
            return "back_end/room/addRoom";
        }

        roomService.addRoom(room);

        List<RoomVO> rooms = roomService.getAll();
        model.addAttribute("rooms", rooms);
        return "redirect:/room/getAllRoom";

    }

    @PostMapping("/getOneRoomUpdate")
    public String getOneRoomUpdate(@RequestParam("roomId") String roomId, ModelMap model) {

        RoomVO room = roomService.findByPK(Integer.valueOf(roomId));
        model.addAttribute("room", room);
        return "back_end/room/updateRoom";
    }

    @PostMapping("/updateRoom")
    public String updateRoom(
            @Valid RoomVO room,
            BindingResult result,
            @RequestParam("roomTypeId") Integer roomTypeId,
            ModelMap model) {

        RoomTypeVO roomTypeVO = roomTypeImpl.getRoomTypeById(roomTypeId);
        room.setRoomTypeVO(roomTypeVO);

        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return "back_end/room/updateRoom";
        }

        roomService.updateRoom(room);

        room = roomService.findByPK(room.getRoomId());
        model.addAttribute("room", room);
        return "back_end/room/roomIdSearch";

    }

    @PostMapping("/updateRoomSaleStatus")
    public String updateRoomSaleStatus(@RequestParam("roomId") String roomId,ModelMap model,
                                       @RequestParam("roomSaleStatus")Byte roomSaleStatus){

        RoomVO room = roomService.findByPK(Integer.valueOf(roomId));
        room.setRoomSaleStatus(roomSaleStatus);
        roomService.updateRoom(room);

        List<RoomVO> rooms = roomService.getAll();
        model.addAttribute("rooms", rooms);
        return "back_end/room/showAllRoom";
    }

}