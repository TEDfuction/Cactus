package com.admin.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.admin.model.AdminService;
import com.admin.model.AdminVO;

import oracle.jdbc.proxy.annotation.Post;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	AdminService adminSvc;
	
	
	
	@GetMapping("/adminLogin")
		public String adminLogin() {
			return "back_end/admin/adminLogin";
		}
	
	@PostMapping("/adminLogin")
	public String memberLogin(HttpServletRequest req, HttpServletResponse res,		
		@RequestParam("adminAccount") String adminAccount,
		@RequestParam("adminPassword") String adminPassword, ModelMap model) {
			
	    AdminVO adminVO = adminSvc.findByAccount(adminAccount);
	    		
	    //查無此帳號
	    if (adminVO == null) {
	    	model.addAttribute("status", "wrong");
	    	return "/back_end/admin/adminLogin";
	    }
	    		
	    //帳號密碼輸入錯誤
	    if (!adminVO.getAdminAccount().equals(adminAccount) || !adminVO.getAdminPassword().equals(adminPassword)) {
	    	model.addAttribute("status", "failed");
	    	return "/back_end/admin/adminLogin";
	    }
	    
	    //帳號被停權
	    if(adminVO.getAdminStatus() == 0) {
	    	model.addAttribute("status", "reject");
	    	return "/back_end/admin/adminLogin";
	    }
	    		
	    //驗證通過,將資訊存至session給過濾器做驗證
	    HttpSession session = req.getSession();
	    model.addAttribute("adminVO", adminVO);
	    session.setAttribute("adminVO", adminVO);
	    		
	    		
	    //檢查有無來源地址,若沒有就到會員專區頁面
	    try {
	    	String location = (String) session.getAttribute("admin_location");
	    	if (location != null) {
	    		session.removeAttribute("admin_location"); // 看看有無來源網頁 (-->如有來源網頁:則重導至來源網頁)
	    		return "redirect:" + location;
//	    		res.sendRedirect(location);
//	    		return;
	    	}
	    }catch (Exception ignored) {
	    	ignored.printStackTrace();
	    }

	    return "/back_end/admin/backendAdminIndex";	 
	    
		}
	
	
	@GetMapping("/backendAdminIndex")
	public String backendAdminIndex() {
	    return "/back_end/admin/backendAdminIndex";	 
	}
	
	
	
	@GetMapping("/adminRegistory")
	public String adminRegistory(ModelMap model) {
		AdminVO adminVO = new AdminVO();
		model.addAttribute("adminVO", adminVO);
		return "/back_end/admin/adminRegistory";
	}
	
	@PostMapping("/adminRegistory")
	public String addAdmin(@Valid AdminVO adminVO,BindingResult result,ModelMap model) {		
		
		if(result.hasErrors()) {
			model.addAttribute("status", "failed");
			model.addAttribute("adminVO", adminVO);
			return "back_end/admin/adminRegistory";
		}
		
		List<AdminVO> list = adminSvc.getAll();
		for(AdminVO dbAdmin : list) {
			String existAccount = dbAdmin.getAdminAccount();
			String inputAccount = adminVO.getAdminAccount();
			if(existAccount.equals(inputAccount)) {
				model.addAttribute("status", "sameAccount");
				model.addAttribute("adminVO", adminVO);
				return "back_end/admin/adminRegistory";
			}
		}
		
		adminSvc.addAdmin(adminVO);
		
		model.addAttribute("status", "success");
		
		return "/back_end/admin/adminLogin";
	}
	
	@GetMapping("/adminUpdate")
	public String adminUpdate(HttpSession session,ModelMap model) {
		AdminVO adminVO = (AdminVO)session.getAttribute("adminVO");
		model.addAttribute("adminVO",adminVO);
		return "/back_end/admin/adminUpdate";
	}
	
	@PostMapping("/adminUpdate")
	public String updateData(@Valid AdminVO adminVO,BindingResult result,ModelMap model,HttpSession session) {
		
		if (result.hasErrors()) {
			model.addAttribute("adminVO",adminVO);
			return "/back_end/admin/adminUpdate";
		}
			
		AdminVO sessionAdminVO = (AdminVO)session.getAttribute("adminVO");
		String sessionAccount = sessionAdminVO.getAdminAccount();
		
		List<AdminVO> list = adminSvc.getAll();
		for(AdminVO dbAdmin : list) {
			String existAccount = dbAdmin.getAdminAccount();
			String inputAccount = adminVO.getAdminAccount();
			
			if(!sessionAccount.equals(inputAccount) && existAccount.equals(inputAccount)) {
				model.addAttribute("status", "sameAccount");
				model.addAttribute("adminVO", adminVO);
				return "back_end/admin/adminUpdate";
			}
		}
		
		adminSvc.updateAdmin(adminVO);
		session.setAttribute("adminVO", adminVO);
		
		return "/back_end/admin/backendAdminIndex";
	}
	
	
	
	@GetMapping("/adminLogout")
	public String adminLogout(HttpSession session, ModelMap model) {
		
		if (session != null) {
			//移除session上之屬性,使對應網頁重新進入過濾器控制範圍
			session.removeAttribute("adminVO");
		}


		return "back_end/admin/adminLogin";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	@GetMapping("/getSessionAdmin")
	@ResponseBody
	public String getSessionAdmin(HttpSession session) {
		AdminVO adminVO = (AdminVO)session.getAttribute("adminVO");
		
		if(adminVO == null) {
			return null;
		}else {
			return adminVO.getAdminAccount();
		}
	}
	
	
	@PostMapping("/setAdminLocation")
	@ResponseBody
	public String setAdminLocation(HttpSession session, @RequestBody Map<String, String> jsonData ) {
        
		String location = jsonData.get("admin_location");
        System.out.println("Received string from frontend: " + location);
        session.setAttribute("admin_location", location);
		
		return location;
	}
	
}


