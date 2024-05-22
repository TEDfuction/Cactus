package com.room.controller;

import com.room.model.Room;
import com.room.model.RoomService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Controller
@Validated
@RequestMapping("/room")
public class RoomIdController {

    @Autowired
    RoomService roomService;

    @PostMapping("/findRoomById")
    public String findById(
            @NotEmpty(message = "房間編號: 請勿空白")
            @Digits(integer = 3, fraction = 0, message = "房間編號: 請填數字-請勿超過{integer}位數")
            @RequestParam("roomId") String roomId,
            Model model){

        Room room = roomService.findByPK(Integer.valueOf(roomId));

        if(room == null){
            model.addAttribute("errorMessage","查無資料");
            return "/room/roomSearch";
        }

        model.addAttribute("room", room);
        return "/room/roomIdSearch";
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
        return new ModelAndView("room/roomSearch", "errorMessage", "請修正以下錯誤:<br>"+message);
    }

}
