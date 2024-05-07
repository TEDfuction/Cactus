package com.roomorder.controller;


import com.roomorder.model.RoomOrderRepository;
import com.roomorder.model.RoomOrderVO;
import com.roomorder.service.impl.RoomOrderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/roomorder")
public class RoomOrderController {


    @Autowired
    private RoomOrderImpl roomorderImpl;

    @Autowired
    private RoomOrderRepository roomOrderRepository;


    // 獲取所有活動
    @GetMapping("/listAllRoomOrder")
    public String getAllRoomOrder(Model model) {
        List<RoomOrderVO> allRoomOrder = roomOrderRepository.findAll();


        model.addAttribute("allRoomOrder", allRoomOrder);

        return "back_end/roomorder/listAllRoomOrder";
    }



//    @PostMapping("/updateRTS")
//    public String updateRTS(@Valid @ModelAttribute RoomTypeStatus roomTypeStatus,
//                            BindingResult result, Model model,
//                            @RequestParam Integer roomTypeId) {
//        try {
//            RoomTypeVO roomTypeVO = roomTypeDao.findById(roomTypeId).orElse(null);
//            if (roomTypeVO == null) {
//                return "redirect:/roomtype/listAllRoomType";
//            }
//            model.addAttribute("roomTypeVO", roomTypeVO);
//
//            if (result.hasErrors()) {
//                return "back-end/roomtype/listAllRoomType";
//            }
//
//            // Toggle room type status
//            boolean newStatus = !roomTypeVO.getRoomTypeStatus();
//            roomTypeVO.setRoomTypeStatus(newStatus);
//
//            roomTypeDao.save(roomTypeVO);
//        } catch (Exception ex) {
//            System.out.println("Exception: " + ex.getMessage());
//        }
//        return "redirect:/roomtype/listAllRoomType";
//    }
//
//
//
//    @GetMapping("/addRoomType")
//    public String showAddPage(Model model) {
//        RoomTypeVORequest roomTypeVORequest = new RoomTypeVORequest();
//        model.addAttribute("roomTypeVORequest", roomTypeVORequest);
//        return "back-end/roomtype/addRoomType";
//    }
//
//
//    @PostMapping("/addRoomType")
//    public String addRoomType(@Valid @ModelAttribute RoomTypeVORequest roomTypeVORequest,
//                                  BindingResult result, ModelMap model)  {
//
//        if (result.hasErrors()) {
//            return "back-end/roomtype/addRoomType";
//        }
//
//        roomTypeImpl.addRoomType(roomTypeVORequest);
//        RoomTypeVO roomTypeVO = new RoomTypeVO();
//        roomTypeVO.setRoomTypeId(roomTypeVORequest.getRoomTypeId());
//        roomTypeVO.setRoomTypeName(roomTypeVORequest.getRoomTypeName());
//        roomTypeVO.setRoomTypeContent(roomTypeVORequest.getRoomTypeContent());
//        roomTypeVO.setRoomTypePrice(roomTypeVORequest.getRoomTypePrice());
//        roomTypeVO.setRoomTypeStatus(Boolean.TRUE);
//
//        roomTypeDao.save(roomTypeVO);
//
//        return "redirect:/roomtype/listAllRoomType";
//    }

}
