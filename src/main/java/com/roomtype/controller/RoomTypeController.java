package com.roomtype.controller;

import com.roomtype.dto.RoomTypeVORequest;
import com.roomtype.dto.RoomTypeUpdate;
import com.roomtype.model.RoomTypeVO;
import com.roomtype.service.impl.RoomTypeImpl;
import com.roomtype.dto.RoomTypeStatus;
import com.roomtype.model.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/roomtype")
public class RoomTypeController {


    @Autowired
    private RoomTypeImpl roomTypeImpl;

    @Autowired
    private RoomTypeRepository roomTypeRepository;


    // 獲取所有活動
    @GetMapping("/listAllRoomType")
    public String getAllRoomTypes(Model model) {
        List<RoomTypeVO> allRoomTypes = roomTypeRepository.findAll();

        model.addAttribute("allRoomTypes", allRoomTypes);

        return "back_end/roomtype/listAllRoomType";
    }


    @PostMapping("/updateRoomType")
    public ResponseEntity<?> updateRoomType(@Valid @ModelAttribute RoomTypeUpdate roomTypeUpdate,
                                            BindingResult result,
                                            @RequestParam(value = "roomTypeId", required = false) Integer roomTypeId) {
        System.out.println("你有在嗎1");
        if (result.hasErrors()) {
            System.out.println("你有在嗎2");
            // 直接返回驗證錯誤
            return ResponseEntity.badRequest().body("Validation errors");
        }
        try {
            System.out.println("你有在嗎3");
            RoomTypeVO roomTypeVO = roomTypeRepository.findById(roomTypeId).orElse(null);
            System.out.println("你有在嗎4");
            if (roomTypeVO == null) {
                System.out.println("你有在嗎5");
                // 找不到指定的房型
                return ResponseEntity.notFound().build();
            }
            System.out.println("你有在嗎6");
            // 更新房型資訊
            roomTypeVO.setRoomTypeName(roomTypeUpdate.getRoomTypeName());
            roomTypeVO.setRoomTypeContent(roomTypeUpdate.getRoomTypeContent());
            roomTypeVO.setRoomTypePrice(roomTypeUpdate.getRoomTypePrice());

            roomTypeRepository.save(roomTypeVO);

        } catch (DataAccessException daEx) {
            System.out.println("數據庫訪問出錯：" + daEx.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database access error: " + daEx.getMessage());
        } catch (Exception ex) {
            System.out.println("你有在嗎7");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating room type: " + ex.getMessage());
        }
        System.out.println("你有在嗎8");
        // 成功更新
        return ResponseEntity.ok("Room type updated successfully");
    }

//    @PostMapping("/updateRoomType")
//    public ResponseEntity<?> updateRoomType(@RequestBody RoomTypeUpdate roomTypeUpdate,
//                                            BindingResult result) {
//        System.out.println("你有在嗎1");
//        if (result.hasErrors()) {
//            System.out.println("你有在嗎2");
//            return ResponseEntity.badRequest().body("Validation errors");
//        }
//        try {
//            System.out.println("你有在嗎3");
//            Integer roomTypeId = roomTypeUpdate.getRoomTypeId(); // 假設你已經在 RoomTypeUpdate 中包含了 roomTypeId
//            RoomTypeVO roomTypeVO = roomTypeDao.findById(roomTypeId).orElse(null);
//            System.out.println("你有在嗎4");
//            if (roomTypeVO == null) {
//                System.out.println("你有在嗎5");
//                return ResponseEntity.notFound().build();
//            }
//            System.out.println("你有在嗎6");
//            // 更新房型資訊
//            roomTypeVO.setRoomTypeName(roomTypeUpdate.getRoomTypeName());
//            roomTypeVO.setRoomTypeContent(roomTypeUpdate.getRoomTypeContent());
//            roomTypeVO.setRoomTypePrice(roomTypeUpdate.getRoomTypePrice());
//
//            roomTypeDao.save(roomTypeVO);
//            System.out.println("你有在嗎8");
//            return ResponseEntity.ok("Room type updated successfully");
//        } catch (Exception ex) {
//            System.out.println("你有在嗎7");
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating room type: " + ex.getMessage());
//        }
//    }



    @PostMapping("/updateRTS")
    public String updateRTS(@Valid @ModelAttribute RoomTypeStatus roomTypeStatus,
                            BindingResult result, Model model,
                            @RequestParam Integer roomTypeId) {
        try {
            RoomTypeVO roomTypeVO = roomTypeRepository.findById(roomTypeId).orElse(null);
            if (roomTypeVO == null) {
                return "redirect:/roomtype/listAllRoomType";
            }
            model.addAttribute("roomTypeVO", roomTypeVO);

            if (result.hasErrors()) {
                return "back_end/roomtype/listAllRoomType";
            }

            // Toggle room type status
            boolean newStatus = !roomTypeVO.getRoomTypeStatus();
            roomTypeVO.setRoomTypeStatus(newStatus);

            roomTypeRepository.save(roomTypeVO);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return "redirect:/roomtype/listAllRoomType";
    }



    @GetMapping("/addRoomType")
    public String showAddPage(Model model) {
        RoomTypeVORequest roomTypeVORequest = new RoomTypeVORequest();
        model.addAttribute("roomTypeVORequest", roomTypeVORequest);
        return "back_end/roomtype/addRoomType";
    }


    @PostMapping("/addRoomType")
    public String addRoomType(@Valid @ModelAttribute RoomTypeVORequest roomTypeVORequest,
                                  BindingResult result, ModelMap model)  {

        if (result.hasErrors()) {
            return "back_end/roomtype/addRoomType";
        }

        roomTypeImpl.addRoomType(roomTypeVORequest);
        RoomTypeVO roomTypeVO = new RoomTypeVO();
        roomTypeVO.setRoomTypeId(roomTypeVORequest.getRoomTypeId());
        roomTypeVO.setRoomTypeName(roomTypeVORequest.getRoomTypeName());
        roomTypeVO.setRoomTypeContent(roomTypeVORequest.getRoomTypeContent());
        roomTypeVO.setRoomTypePrice(roomTypeVORequest.getRoomTypePrice());
        roomTypeVO.setRoomTypeStatus(Boolean.TRUE);

        roomTypeRepository.save(roomTypeVO);

        return "redirect:/roomtype/listAllRoomType";
    }


    @GetMapping("/getOneRoomType")
    public String getOneRoomType(@Valid @ModelAttribute RoomTypeVORequest roomTypeVORequest,
                                 BindingResult result, Model model,
                                 @RequestParam Integer roomTypeId) {
        Optional<RoomTypeVO> getOneRoomType = roomTypeRepository.findById(roomTypeId);
        model.addAttribute("roomTypeVO", getOneRoomType);


        return "back_end/roomtype/listAllRoomType";
    }

}
