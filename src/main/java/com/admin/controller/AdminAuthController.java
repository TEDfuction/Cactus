package com.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.admin.model.AdminAuthService;
import com.admin.model.AdminAuthVO;
import com.admin.model.AdminAuthorizationService;
import com.admin.model.AdminAuthorizationVO;
import com.admin.model.AdminService;
import com.admin.model.AdminVO;

@Controller
@RequestMapping("/adminAuth")
public class AdminAuthController {
	
	@Autowired
	AdminService adminSvc;
	
	@Autowired
	AdminAuthService adminAuthSvc;
	
	@Autowired
	AdminAuthorizationService adminAuthorizationSvc;

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
	
	
	@GetMapping("/authSetting")
	public String authSetting(ModelMap model) {
		
		List<AdminVO> list1 = adminSvc.getAll();
		model.addAttribute("adminList", list1);
		
		List<AdminAuthorizationVO> list2 = adminAuthorizationSvc.getAll();
		model.addAttribute("adminAuthorizationList", list2);
		
		return "/back_end/admin/authSetting";
	}
	
	@PostMapping("/getAuth")
	@ResponseBody
	public Map< String , List<String> > getAuth(String adminId) {
		Map< String , List<String> > map = new HashMap< String , List<String> >();
		List<String> ids = new ArrayList<String>();
		List<String> names = new ArrayList<String>();
		
		//放入該員工的權限id與名稱
		List<AdminAuthVO> list = adminAuthSvc.findByAdminId( Integer.valueOf(adminId) );
		for(AdminAuthVO aa : list) {
			Integer adminAuthorizationId = aa.getAdminAuthorizationVO().getAdminAuthorizationId();
		    String adminAuthorizationName = aa.getAdminAuthorizationVO().getAdminAuthorizationName();
		    
		    ids.add(adminAuthorizationId.toString());
		    names.add(adminAuthorizationName);
		}
		
		map.put("AuthId", ids);
		map.put("AuthName", names);
		
		
		return map;
	}
	
	@PostMapping("/authSetting")
	public String authChange(ModelMap model,
			HttpSession session,
			@RequestParam("adminId") Integer adminId,
			@RequestParam("adminAuthorizationId")List<Integer> adminAuthorizationIds) {
		
		
		//先刪除所有權限
		
		adminAuthSvc.emptyAuth(adminId);
		System.out.println("刪除成功");
		
		
		//設定權限
		Integer count = 0;
		
		for(Integer aaoId : adminAuthorizationIds) {
			AdminAuthVO adminAuthVO = new AdminAuthVO();

			AdminAuthorizationVO aaoVO = new AdminAuthorizationVO();
			aaoVO.setAdminAuthorizationId(aaoId);
			
			AdminVO aVO = new AdminVO();
			aVO.setAdminId(adminId);
			
			adminAuthVO.setAdminAuthorizationVO(aaoVO);
			adminAuthVO.setAdminVO(aVO);
			
			adminAuthSvc.addAdminAuth(adminAuthVO);
			count++;
			System.out.println("新增成功");

		}
		
		System.out.println("共新增"+count+"筆權限");
		
	
		List<AdminVO> list1 = adminSvc.getAll();
		model.addAttribute("adminList", list1);
		
		List<AdminAuthorizationVO> list2 = adminAuthorizationSvc.getAll();
		model.addAttribute("adminAuthorizationList", list2);
		
		return "/back_end/admin/authSetting";
	}
}
