package com.roomtypepic.controller;


import com.roomtypepic.model.impl.RoomTypePicImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/roompic")
public class RoomTypePicController {

    @Autowired
    RoomTypePicImpl roomTypePicImpl;


}
