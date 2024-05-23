package com.roompromotion.controller;

import com.roompromotion.model.RoomPromotionVO;
import com.roompromotion.model.RoomPromotionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
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

        RoomPromotionVO roomPromotion =  roomPromotionService.findByPK(Integer.valueOf(promotionId));

//        List<RoomPromotion> list = roomPromotionService.getAll();
//        model.addAttribute("promotionListData", list);

        if(roomPromotion == null){
            model.addAttribute("errorMessage","查無資料");
            return "back_end/roomPromotion/roomPromotionSearch";
        }

        model.addAttribute("promotion" , roomPromotion);
        return "back_end/roomPromotion/roomPromotionIdSearch";
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
        return new ModelAndView("back_end/roomPromotion/roomPromotionSearch", "errorMessage", "請修正以下錯誤:<br>"+message);
    }
}
