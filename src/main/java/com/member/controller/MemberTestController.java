package com.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class MemberTestController {
	
	@GetMapping("/member")               
    public String index(Model model) {
        return "member";                
    }
	
}
