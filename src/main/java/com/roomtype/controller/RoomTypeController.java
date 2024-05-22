package com.roomtype.controller;

import com.roomtype.dto.RoomTypeStatus;
import com.roomtype.dto.RoomTypeUpdate;
import com.roomtype.dto.RoomTypeVORequest;
import com.roomtype.model.RoomTypeRepository;
import com.roomtype.model.RoomTypeVO;
import com.roomtype.service.impl.RoomTypeImpl;
import com.roomtypepic.model.RoomTypePicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/roomtype")
public class RoomTypeController {


    @Autowired
    private RoomTypeImpl roomTypeImpl;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private RoomTypePicRepository roomTypePicRepository;



    // 獲取所有活動
    @GetMapping("/listAllRoomType")
    public String getAllRoomTypes(Model model) {
        List<RoomTypeVO> allRoomTypes = roomTypeRepository.findAll();
        RoomTypeVO roomTypeVO = new RoomTypeVO();
        model.addAttribute("allRoomTypes", allRoomTypes);
        return "back_end/roomtype/listAllRoomType";

    }



    @PostMapping("/updateRTS")
    public String updateRTS(@Valid @ModelAttribute RoomTypeStatus roomTypeStatus,
                            BindingResult result, Model model,
                            @RequestParam Integer roomTypeId) {
        try {
            RoomTypeVO roomTypeVO = roomTypeRepository.findById(roomTypeId).orElse(null);
            if (roomTypeVO == null) {
                return "redirect:back_end/roomtype/listAllRoomType";
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

        return "redirect:/back_end/roomtype/listAllRoomType";
    }


    @GetMapping("/getOneRoomType")
    public String getOneRoomType(@Valid @ModelAttribute RoomTypeVORequest roomTypeVORequest,
                                 BindingResult result, Model model,
                                 @RequestParam Integer roomTypeId) {
        Optional<RoomTypeVO> getOneRoomType = roomTypeRepository.findById(roomTypeId);
        model.addAttribute("roomTypeVO", getOneRoomType);


        return "back_end/roomtype/listAllRoomType";
    }

    @PostMapping("/updateRoomTypeForm")
    public String updateRoomTypeForm(@RequestParam("roomTypeId") Integer roomTypeId,
                                     @Valid @ModelAttribute RoomTypeUpdate roomTypeUpdate,
                                     BindingResult result,
                                     RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("status", "error");
            redirectAttributes.addFlashAttribute("message", "驗證錯誤");
            return "redirect:/back_end/roomtype/listAllRoomType"; // 假設有一個顯示錯誤的頁面
        }

        RoomTypeVO roomTypeVO = roomTypeRepository.findById(roomTypeId).orElse(null);
        if (roomTypeVO == null) {
            redirectAttributes.addFlashAttribute("status", "error");
            redirectAttributes.addFlashAttribute("message", "找不到指定的房型");
            return "redirect:/back_end/roomtype/listAllRoomType"; // 假設有一個顯示「未找到」的頁面
        }


        roomTypeVO.setRoomTypeName(roomTypeUpdate.getRoomTypeName());
        roomTypeVO.setRoomTypeContent(roomTypeUpdate.getRoomTypeContent());
        roomTypeVO.setRoomTypePrice(roomTypeUpdate.getRoomTypePrice());
        roomTypeRepository.save(roomTypeVO);

        redirectAttributes.addFlashAttribute("status", "success");
        redirectAttributes.addFlashAttribute("message", "資料已更新");
        return "redirect:/roomtype/listAllRoomType"; // 成功後重定向到另一個 URL
    }

    @GetMapping("/roomTypeFront")
    public String getByRTFront(Model model) {
        List<RoomTypeVO> listAllRTFront = roomTypeImpl.getRTStatus();
        model.addAttribute("listAllRTFront", listAllRTFront);
        if (roomTypePicRepository == null) {
            // Handle the case where roomTypePicVO is not found
            return "redirect:/error-page"; // or some error handling
        }
        model.addAttribute("roomTypePicVO", roomTypePicRepository);
        return "front_end/room/roomTypeFront";

    }

    @PostMapping("/selectRoomType")
    public String selectRoomType(@RequestParam("roomTypeName") String roomTypeName,
                                 @RequestParam("selectCheckIn") @DateTimeFormat(pattern = "yyyy-MM-dd") Date checkInDate,
                                 @RequestParam("selectCheckOut") @DateTimeFormat(pattern = "yyyy-MM-dd") Date checkOutDate,
                                 @RequestParam(value = "roomGuestAmount", required = false) Integer roomGuestAmount,
                                 @Valid @ModelAttribute RoomTypeVO roomTypeVO,
                                 BindingResult result, Model model) {

        List<Object[]> getSelect = roomTypeImpl.getAvailableRoomTypes(roomTypeName, checkInDate, checkOutDate, roomGuestAmount);
        model.addAttribute("selectRoom", getSelect);

        return "front_end/room/roomFront";
    }

    @GetMapping("/getRoomGuestAmounts")
    @ResponseBody
    public List<Integer> getRoomGuestAmounts(@RequestParam("roomTypeName") String roomTypeName) {
        Optional<RoomTypeVO> roomTypeOptional = roomTypeImpl.getRoomTypeIdByName(roomTypeName);
        if (roomTypeOptional.isPresent()) {
            Integer roomTypeId = roomTypeOptional.get().getRoomTypeId();
            return roomTypeImpl.getRoomGuestAmounts(roomTypeId);
        }
        return new ArrayList<>();
    }

    @PostMapping("/selectRoom")
    public String selectRoom(@RequestParam("roomTypeName") String roomTypeName,
                                 @RequestParam("selectCheckIn") @DateTimeFormat(pattern = "yyyy-MM-dd") Date checkInDate,
                                 @RequestParam("selectCheckOut") @DateTimeFormat(pattern = "yyyy-MM-dd") Date checkOutDate,
                                 @RequestParam(value = "roomGuestAmount", required = false) Integer roomGuestAmount,
                                 @Valid @ModelAttribute RoomTypeVO roomTypeVO,
                                 BindingResult result, Model model) {

        List<Object[]> getSelect = roomTypeImpl.getAvailableRoomTypes(roomTypeName, checkInDate, checkOutDate, roomGuestAmount);
        model.addAttribute("select", getSelect);

        return "front_end/room/roomFront";
    }

}