package com.roompromotion.controller;

import com.roompromotion.model.RoomPromotion;
import com.roompromotion.model.RoomPromotionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Controller
@Validated
@RequestMapping("/roomPromotion")
public class RoomPromotionIdController {

    @Autowired
    RoomPromotionService roomPromotionService;

    @PostMapping("/findRoomPromotionById")
    public String findById(
            @NotEmpty(message="促銷編號: 請勿空白")
            @Digits(integer = 3, fraction = 0, message = "促銷編號: 請填數字-請勿超過{integer}位數")
            @RequestParam("roomPromotionId") String promotionId,
            ModelMap model){

        RoomPromotion roomPromotion =  roomPromotionService.findByPK(Integer.valueOf(promotionId));

//        List<RoomPromotion> list = roomPromotionService.getAll();
//        model.addAttribute("promotionListData", list);

        if(roomPromotion == null){
            model.addAttribute("errorMessage","查無資料");
            return "/roomPromotion/roomPromotionSearch";
        }

        model.addAttribute("promotion" , roomPromotion);
        return "/roomPromotion/roomPromotionIdSearch";
    }
    @ExceptionHandler(value = { ConstraintViolationException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ModelAndView handleError(HttpServletRequest req, ConstraintViolationException e, Model model) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        StringBuilder strBuilder = new StringBuilder();
        for (ConstraintViolation<?> violation : violations ) {
            strBuilder.append(violation.getMessage() + "<br>");
        }

        String message = strBuilder.toString();
        return new ModelAndView("roomPromotion/roomPromotionSearch", "errorMessage", "請修正以下錯誤:<br>"+message);
    }
}
