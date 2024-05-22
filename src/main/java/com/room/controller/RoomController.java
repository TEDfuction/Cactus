package com.room.controller;


import com.room.model.Room;
import com.room.model.RoomService;
import com.roomType.model.RoomType;
import com.roomType.model.RoomTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/room")
public class RoomController {

    @Autowired
    RoomService roomService;
    @Autowired
    RoomTypeService roomTypeService;

    @GetMapping("/searchRoom")
    public String searchPage() {
        return "/room/roomSearch";
    }


    @GetMapping("/getAllRoom")
    public String getAll(ModelMap model) {

        List<Room> rooms = roomService.getAll();

        model.addAttribute("rooms", rooms);

        return "/room/showAllRoom";
    }

    @GetMapping("/addRoom")
    public String addRoom(ModelMap model) {
        Room room = new Room();
        List<RoomType> roomType = roomTypeService.getAll();

        model.addAttribute("roomTypeListData", roomType);
        model.addAttribute("room", room);
        return "/room/addRoom";
    }

    @PostMapping("/insertRoom")
    public String insert(
            @Valid Room room,
            BindingResult result,
            ModelMap model){

        if (result.hasErrors()) {
            List<RoomType> roomType = roomTypeService.getAll();
            model.addAttribute("roomTypeListData", roomType);
            return "/room/addRoom";
        }

        roomService.addRoom(room);

        List<Room> rooms = roomService.getAll();
        model.addAttribute("rooms", rooms);
        return "redirect:/room/getAllRoom";

    }

    @PostMapping("/getOneRoomUpdate")
    public String getOneRoomUpdate(@RequestParam("roomId") String roomId, ModelMap model) {

        Room room = roomService.findByPK(Integer.valueOf(roomId));
        model.addAttribute("room", room);
        return "/room/updateRoom";
    }

    @PostMapping("/updateRoom")
    public String updateRoom(
            @Valid Room room,
            BindingResult result,
            @RequestParam("roomTypeId") Integer roomTypeId,
            ModelMap model) {

        RoomType roomType = roomTypeService.getById(roomTypeId);
        room.setRoomType(roomType);

        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return "/room/updateRoom";
        }

        roomService.updateRoom(room);

        room = roomService.findByPK(room.getRoomId());
        model.addAttribute("room", room);
        return "/room/roomIdSearch";

    }

    @PostMapping("/updateRoomSaleStatus")
    public String updateRoomSaleStatus(@RequestParam("roomId") String roomId,ModelMap model,
                                       @RequestParam("roomSaleStatus")Byte roomSaleStatus){

        Room room = roomService.findByPK(Integer.valueOf(roomId));
        room.setRoomSaleStatus(roomSaleStatus);
        roomService.updateRoom(room);

        List<Room> rooms = roomService.getAll();
        model.addAttribute("rooms", rooms);
        return "/room/showAllRoom";
    }

}