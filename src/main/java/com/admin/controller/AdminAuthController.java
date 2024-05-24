package com.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
			@RequestParam(value = "adminAuthorizationId", required = false)List<Integer> adminAuthorizationIds) {
		
		//若沒選任何權限，就刪除所有權限
		if(adminAuthorizationIds == null) {
			
			List<AdminAuthVO> list = adminAuthSvc.findByAdminId(adminId);
			if(!list.isEmpty()) {
				adminAuthSvc.emptyAuth(adminId);
				System.out.println("刪除成功");
				model.addAttribute("status","successAuth");
				
				List<AdminVO> list1 = adminSvc.getAll();
				model.addAttribute("adminList", list1);
				
				List<AdminAuthorizationVO> list2 = adminAuthorizationSvc.getAll();
				model.addAttribute("adminAuthorizationList", list2);
				
				return "/back_end/admin/authSetting";
			}else {
				model.addAttribute("status","confirmAuth");
				
				List<AdminVO> list1 = adminSvc.getAll();
				model.addAttribute("adminList", list1);
				
				List<AdminAuthorizationVO> list2 = adminAuthorizationSvc.getAll();
				model.addAttribute("adminAuthorizationList", list2);
				
				return "/back_end/admin/authSetting";
			}
				
			
		}
		
		
		//先刪除員工底下所有權限
		List<AdminAuthVO> list = adminAuthSvc.findByAdminId(adminId);

		if(!list.isEmpty()) {
			adminAuthSvc.emptyAuth(adminId);
			System.out.println("刪除成功");
		}
		
		//再做權限設定
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
		
		model.addAttribute("status","successAuth");
		return "/back_end/admin/authSetting";
	}
	
	
	
	//捕捉MissingServletRequestParameterException做處理
		@ExceptionHandler(value = { MissingServletRequestParameterException.class })
		public ModelAndView handleError(
				HttpServletRequest req,
				ConstraintViolationException e,
				Model model) {
			
			//將錯誤訊息收集
		    Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		    
		    StringBuilder strBuilder = new StringBuilder();
		    
		    for (ConstraintViolation<?> errorSet : violations ) {
		    	//把錯誤訊息串接 
		    	strBuilder.append(errorSet.getMessage() + "<br>");
		    }
		    
			String message = strBuilder.toString();
		    return new ModelAndView("back_end/adminAuth/authSetting", "errorMessage", "請修正以下錯誤:<br>"+message);
		}
	
	
}
