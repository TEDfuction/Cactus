package com.roompromotion.controller;


import com.roompromotion.model.RoomPromotion;
import com.roompromotion.model.RoomPromotionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Controller
@RequestMapping("/roomPromotion")
public class RoomPromotionController {

    @Autowired
    RoomPromotionService roomPromotionService;

    @GetMapping("/searchRoomPromotion")
    public String searchPage() { return "/roomPromotion/roomPromotionSearch"; }




    @GetMapping("/getAllRoomPromotion")
    public String getAll(ModelMap model){

        List<RoomPromotion> roomPromotions = roomPromotionService.getAll();

        model.addAttribute("roomPromotions", roomPromotions);

        return "roomPromotion/showAllRoomPromotions";
    }

    @GetMapping("/addRoomPromotion")
    public String addPromotion(ModelMap model) {
        RoomPromotion roomPromotion  = new RoomPromotion();

        model.addAttribute("roomPromotion", roomPromotion);
        return "/roomPromotion/addRoomPromotion";
    }


    @PostMapping("/insertRoomPromotion")
    public String insert(
            @Valid RoomPromotion roomPromotion,
            BindingResult result,
            ModelMap model){
        if (result.hasErrors()) {
            model.addAttribute("promotionStarted", roomPromotion.getPromotionStarted());
            model.addAttribute("promotionEnd", roomPromotion.getPromotionEnd());
            return "/roomPromotion/addRoomPromotion";
        }

        roomPromotionService.addRoomPromotion(roomPromotion);

        List<RoomPromotion> list = roomPromotionService.getAll();
        model.addAttribute("roomPromotions", list);

        model.addAttribute("status" , "success");
        return "roomPromotion/showAllRoomPromotions";

    }

    @PostMapping("/getOneRoomPromotionUpdate")
    public String getOnePromotionUpdate(@RequestParam("promotionId") String promotionId, ModelMap model) {

        RoomPromotion roomPromotion = roomPromotionService.findByPK(Integer.valueOf(promotionId));
        model.addAttribute("roomPromotion", roomPromotion);
        return "roomPromotion/updateRoomPromotion";
    }


    @PostMapping("/updateRoomPromotion")
    public String updateRoomPromotion(@Valid RoomPromotion roomPromotion,
                                  BindingResult result,
                                  ModelMap model){

        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return "roomPromotion/showAllRoomPromotions";
        }
        roomPromotionService.updateRoomPromotion(roomPromotion);

        model.addAttribute("status" , "success");
        roomPromotion = roomPromotionService.findByPK(roomPromotion.getPromotionId());
        model.addAttribute("promotion" , roomPromotion);
        return "roomPromotion/roomPromotionIdSearch";
    }

//    @GetMapping("/deletePromotion")
//    public String deletePromotion(@RequestParam("promotionId") Integer promotionId, ModelMap model) {
//        roomPromotionService.deleteRoomPromotion(promotionId);
//        List<RoomPromotion> roomPromotions = roomPromotionService.getAll();
//        model.addAttribute("roomPromotionsListData", roomPromotions);
//        model.addAttribute("status" , "success");
//        return "/member/showAllPromotions";
//    }

    @PostMapping("/OrderList")
    public String getRoomPromotion(@RequestParam("selectCheckIn") String selectCheckInStr, Model model) {
        try {
            LocalDate selectCheckIn = LocalDate.parse(selectCheckInStr);
            List<String> getRoomPromotion = roomPromotionService.findByCheckInDate(selectCheckIn);

            model.addAttribute("getRoomPromotion", getRoomPromotion);

            return "/front_end/roomorder/roomOrderFront";  // 確保這個路徑正確
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Invalid date format.");
            return "index";  // 當日期解析出錯時，返回到主頁面
        }
    }

}
