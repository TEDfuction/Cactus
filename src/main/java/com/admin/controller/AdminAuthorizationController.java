package com.admin.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.admin.model.AdminAuthService;
import com.admin.model.AdminService;
import com.admin.model.AdminVO;

@Controller
@RequestMapping("/adminAuth")
public class AdminAuthorizationController {
	
	@Autowired
	AdminService adminSvc;
	
	@Autowired
	AdminAuthService adminAuthSvc;

	@GetMapping("/listAllAdmin")
	public String listAllAdmin(ModelMap model) {
		List<AdminVO> list = adminSvc.getAll();
		model.addAttribute("adminList",list);
		return "/back_end/admin/listAllAdmin";
	}
	
	
	@PostMapping("/changeAdminStatus")
	public String changeAdminStatus(AdminVO adminVO, ModelMap model, HttpSession session) {
		Integer adminStatus = adminVO.getAdminStatus();
		Integer adminId = adminVO.getAdminId();
		
		AdminVO sessionAdminVO = (AdminVO)session.getAttribute("adminVO");
		Integer sessionAdminId = sessionAdminVO.getAdminId();
		
		if(adminId.equals(sessionAdminId)) {
			model.addAttribute("status", "cannotChange");
			
			List<AdminVO> list = adminSvc.getAll();
			model.addAttribute("adminList",list);
			
			return "/back_end/admin/listAllAdmin";
		}
		
		if(adminStatus == 1) {     //進行停權動作
			adminVO.setAdminStatus(0);
			adminSvc.updateAdmin(adminVO);
			model.addAttribute("status", "closeAdmin");
		}else {     //進行復權動作
			adminVO.setAdminStatus(1);
			adminSvc.updateAdmin(adminVO);
			model.addAttribute("status", "openAdmin");
		}
		
		List<AdminVO> list = adminSvc.getAll();
		model.addAttribute("adminList",list);
		return "/back_end/admin/listAllAdmin";
	}
}
