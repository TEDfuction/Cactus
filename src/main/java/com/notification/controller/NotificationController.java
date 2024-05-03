package com.notification.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.notification.model.NotificationService;
import com.notification.model.NotificationVO;

@Controller
@RequestMapping("/notification")
//@Validated
public class NotificationController {
	
	@Autowired
	MemberService memSvc;

	@Autowired
	NotificationService notiSvc;
	
	
	
	@GetMapping("showAllMessage")
	public String showAllMessage(ModelMap model) {
		
		List<NotificationVO> notiList = notiSvc.getAll();
		model.addAttribute("notiList",notiList);
		
		return "/back_end/notification/showAllMessage";
	}
	
	
	
	
	@GetMapping("/sendMessage")
	public String sendingPage(ModelMap model) {
		
		NotificationVO notificationVO = new NotificationVO();
		model.addAttribute("notificationVO",notificationVO);
		
		model.addAttribute("memList",memSvc.getAll());
		
		return "/back_end/notification/sendMessage";
	}
	
	@PostMapping("/sendMessage")
	public String sendMessage(
			@Valid NotificationVO notificationVO,
			BindingResult result,
			@RequestParam("memberId") Integer memberId,
			ModelMap model) throws IOException {
		
		if(result.hasErrors()) {
			model.addAttribute("status", "failed");
			model.addAttribute("memList",memSvc.getAll());
			return "/back_end/notification/sendMessage";
		}
		
		MemberVO memberVO = new MemberVO();
		memberVO.setMemberId(memberId);
		notificationVO.setMember(memberVO);
		
		notiSvc.sendMsg(notificationVO);
		
		
		
		model.addAttribute("status","success");
		return "redirect:/notification/showAllMessage" ;
	}
	
	
	
	
}
