package com.roompromotion.controller;


import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.roompromotion.model.RoomPromotionVO;
import com.roompromotion.model.RoomPromotionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Controller
@RequestMapping("/roomPromotion")
public class RoomPromotionController {

    @Autowired
    RoomPromotionService roomPromotionService;

    @Autowired
    MemberService memSvc;

    @GetMapping("/searchRoomPromotion")
    public String searchPage() { return "back_end/roomPromotion/roomPromotionSearch"; }




    @GetMapping("/getAllRoomPromotion")
    public String getAll(ModelMap model){

        List<RoomPromotionVO> roomPromotions = roomPromotionService.getAll();

        model.addAttribute("roomPromotions", roomPromotions);

        return "back_end/roomPromotion/showAllRoomPromotions";
    }

    @GetMapping("/addRoomPromotion")
    public String addPromotion(ModelMap model) {
        RoomPromotionVO roomPromotion  = new RoomPromotionVO();

        model.addAttribute("roomPromotion", roomPromotion);
        return "back_end/roomPromotion/addRoomPromotion";
    }


    @PostMapping("/insertRoomPromotion")
    public String insert(
            @Valid RoomPromotionVO roomPromotion,
            BindingResult result,
            ModelMap model){
        if (result.hasErrors()) {
            model.addAttribute("promotionStarted", roomPromotion.getPromotionStarted());
            model.addAttribute("promotionEnd", roomPromotion.getPromotionEnd());
            return "back_end/roomPromotion/addRoomPromotion";
        }

        roomPromotionService.addRoomPromotion(roomPromotion);

        List<RoomPromotionVO> list = roomPromotionService.getAll();
        model.addAttribute("roomPromotions", list);

        model.addAttribute("status" , "success");
        return "back_end/roomPromotion/showAllRoomPromotions";

    }

    @PostMapping("/getOneRoomPromotionUpdate")
    public String getOnePromotionUpdate(@RequestParam("promotionId") String promotionId, ModelMap model) {

        RoomPromotionVO roomPromotion = roomPromotionService.findByPK(Integer.valueOf(promotionId));
        model.addAttribute("roomPromotion", roomPromotion);
        return "back_end/roomPromotion/updateRoomPromotion";
    }


    @PostMapping("/updateRoomPromotion")
    public String updateRoomPromotion(@Valid RoomPromotionVO roomPromotion,
                                  BindingResult result,
                                  ModelMap model){

        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return "back_end/roomPromotion/showAllRoomPromotions";
        }
        roomPromotionService.updateRoomPromotion(roomPromotion);

        model.addAttribute("status" , "success");
        roomPromotion = roomPromotionService.findByPK(roomPromotion.getPromotionId());
        model.addAttribute("promotion" , roomPromotion);
        return "back_end/roomPromotion/roomPromotionIdSearch";
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
    public String getRoomPromotion(
            @RequestParam("roomTypeName") String roomTypeName,
            @RequestParam("roomGuestAmount") String roomGuestAmount,
            @RequestParam("roomSize") String roomSize,
            @RequestParam("roomAmount") Integer roomAmount,
            @RequestParam("selectCheckIn") String selectCheckInStr,
            @RequestParam("selectCheckOut") String selectCheckOutStr,
            @RequestParam("roomTypeId") Integer roomTypeId,
            Model model, HttpSession httpSession) {

        try {
            LocalDate selectCheckIn = LocalDate.parse(selectCheckInStr);
            List<RoomPromotionVO> getRoomPromotion = roomPromotionService.findByCheckInDate(selectCheckIn);
//            httpSession.setAttribute("roomTypeName", roomTypeName);
//            System.out.println(httpSession.getAttribute(roomTypeName));
            // 將查詢結果和其他參數添加到模型中
            model.addAttribute("roomTypeName", roomTypeName);
            model.addAttribute("roomGuestAmount", roomGuestAmount);
            model.addAttribute("roomSize", roomSize);
            model.addAttribute("roomPrice", roomAmount);
            model.addAttribute("selectCheckIn", selectCheckInStr);
            model.addAttribute("selectCheckOut", selectCheckOutStr);
            model.addAttribute("roomTypeId", roomTypeId);
//            System.out.println(roomTypeName);

            String email = (String)httpSession.getAttribute("account");

            MemberVO memberVO = memSvc.findByEmail(email);
            model.addAttribute("memberVO",memberVO);
            model.addAttribute("roomPromotionVO",getRoomPromotion);


            return "/front_end/room/roomOrderFront";  // 確保這個路徑正確
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Invalid date format.");
            return "/front_end/room/roomTypeFront";  // 當日期解析出錯時，返回到主頁面
        }
    }

}
