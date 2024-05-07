package com.member.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.notification.model.NotificationService;
import com.notification.model.NotificationVO;

@Controller
//@Validated
@RequestMapping("/member")
public class MemberController {

	@Autowired
	MemberService memSvc;
	
	@Autowired
	NotificationService notiSvc;
	
	Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	
	
	
	
	
	
	
	@GetMapping("/memberSearch")
	public String searchingPage(ModelMap model) {
		return "/back_end/member/memberSearch";
	}
	
	
	
	@PostMapping("/findByName")
	public String findByName(
			/**** 1.接收請求參數 - 輸入格式的錯誤處理 ****/
			@RequestParam("memberName") String memberName, 
			ModelMap model) {

			/**** 2.開始查詢資料 ****/
			List<MemberVO> list = memSvc.findByName("%" + memberName + "%");
			model.addAttribute("memberList", list);
	
			return "/back_end/member/memberNameSearch";

	}

	
	
	
	
	
	
	@GetMapping("/listOneMember")
	public String showOneData(
			HttpServletRequest req,
			ModelMap model) {
		
		HttpSession session = req.getSession();
		String email = (String) session.getAttribute("account");
		
		MemberVO memberVO = memSvc.findByEmail(email);	
		
		//供WebSocket隨時調用
		Integer count = notiSvc.getNotiUnread(memberVO.getMemberId());		
		model.addAttribute("UnreadCount",count);
		
		model.addAttribute("memberVO", memberVO);
		
		return "/front_end/member/listOneMember";
	}
	
	
	
	
	
	
	@GetMapping("/memberRegistory")
	public String registory(ModelMap model) {
		MemberVO memberVO = new MemberVO();
		model.addAttribute("memberVO", memberVO);

		return "/front_end/member/memberRegistory";
	}

	
	//練習建構子的錯誤驗證
	@PostMapping("/addMember")
	public String addMember(
			@Valid MemberVO memberVO, 
			//類似0201之errorMsg區塊，作錯誤訊息的收集用
			BindingResult result, 
			ModelMap model,
			@RequestParam("memberPic") MultipartFile[] parts) throws IOException {
		

		/**** 1.接收請求參數 - 輸入格式的錯誤處理 ****/
		
		// 去除BindingResult中圖片上傳欄位的FieldError紀錄 
		result = removeFieldError(memberVO, result, "memberPic");
		
		//圖片不為必填欄位，故不做錯誤處理
		if(!parts[0].isEmpty()) {
			for(MultipartFile multipartFile :parts) {
				byte[] b = multipartFile.getBytes();
				memberVO.setMemberPic(b);
			}
		}
		
		//錯誤訊息內容集合
//		for (ObjectError object :result.getAllErrors()) {
//			System.out.println(object.toString());
//		}
		
		if (result.hasErrors()) {
//			model.addAttribute("memberVO" , memberVO);
			model.addAttribute("status", "failed");

			return "/front_end/member/memberRegistory";
		}
		
		
		/**** 2.開始新增資料 ****/
		
		memSvc.addMember(memberVO);

		
		
		/**** 3.新增完成,準備轉交(Send the Success view) ****/

//		List<MemberVO> list = memSvc.getAll();
//		model.addAttribute("memList", list);
//		model.addAttribute("success","- (新增成功)");
		
		model.addAttribute("status", "success");
		return "front_end/member/memberLogin";
	}

	

	
	
	
	
	@GetMapping("/UpdateMember")
	public String updateMember(HttpServletRequest req,ModelMap model) {
		
		HttpSession session = req.getSession();
		String email = (String) session.getAttribute("account");
		
		MemberVO memberVO = memSvc.findByEmail(email);
		
		//供WebSocket隨時調用
		Integer count = notiSvc.getNotiUnread(memberVO.getMemberId());
		model.addAttribute("UnreadCount",count);
		
		model.addAttribute("memberVO", memberVO);
		
		return "/front_end/member/UpdateMember";
	}
	
	//同新增
	@PostMapping("/update")
	public String update(
			HttpServletRequest req,
			@Valid MemberVO memberVO,
			BindingResult result,
			ModelMap model,
			@RequestParam("memberPic") MultipartFile[] parts) throws IOException {
		
		
		
		//去除BindingResult中upFiles欄位的FieldError紀錄 
		result = removeFieldError(memberVO, result, "memberPic");
				
		
		//有傳圖就改圖,不傳圖就抓原本的圖
		if(!parts[0].isEmpty()) {
			for(MultipartFile multipartFile :parts) {
				byte[] b = multipartFile.getBytes();
				memberVO.setMemberPic(b);
			}
		}else {
			byte[] b = memSvc.findByPK(memberVO.getMemberId()).getMemberPic();
			memberVO.setMemberPic(b);
		}
		
		
		
		if (result.hasErrors()) {
			return "front_end/member/UpdateMember";
		}
		
		memSvc.updateMember(memberVO);
		
		model.addAttribute("memberVO", memberVO);
		HttpSession session = req.getSession();
		session.setAttribute("account", memberVO.getEmail());
		
		return "/front_end/member/listOneMember";
	}
	
	
	

	
	
	

	
	
	@GetMapping("/memberOnlyWeb")
	public String memberOnlyWeb(ModelMap model, HttpServletRequest req) {
		HttpSession session = req.getSession();
		String email = (String) session.getAttribute("account");
		
		MemberVO memberVO = memSvc.findByEmail(email);
		
		//供WebSocket隨時調用
		Integer count = notiSvc.getNotiUnread(memberVO.getMemberId());
		model.addAttribute("UnreadCount",count);
		
		model.addAttribute("memberVO", memberVO);

		return "/front_end/member/memberOnlyWeb";
	}


	
	
	@GetMapping("/checkActivityOrderDetail")
	public String checkActivityOrderDetail(HttpServletRequest req,ModelMap model) {
		
		HttpSession session = req.getSession();
		String email = (String) session.getAttribute("account");
		
		MemberVO memberVO = memSvc.findByEmail(email);
		
		//供WebSocket隨時調用
		Integer count = notiSvc.getNotiUnread(memberVO.getMemberId());		
		model.addAttribute("UnreadCount",count);
		
		model.addAttribute("memberVO", memberVO);
		
		return "/front_end/member/checkActivityOrderDetail";
	}	
	
	
	@GetMapping("/checkProductOrderDetail")
	public String checkProductOrderDetail(HttpServletRequest req,ModelMap model) {
		
		HttpSession session = req.getSession();
		String email = (String) session.getAttribute("account");
		
		MemberVO memberVO = memSvc.findByEmail(email);
		
		//供WebSocket隨時調用
		Integer count = notiSvc.getNotiUnread(memberVO.getMemberId());	
		model.addAttribute("UnreadCount",count);
		
		model.addAttribute("memberVO", memberVO);
		
		return "/front_end/member/checkProductOrderDetail";
	}	
	
	
	@GetMapping("/checkRoomOrderDetail")
	public String checkRoomOrderDetail(HttpServletRequest req,ModelMap model) {
		
		HttpSession session = req.getSession();
		String email = (String) session.getAttribute("account");
		
		MemberVO memberVO = memSvc.findByEmail(email);
		
		//供WebSocket隨時調用
		Integer count = notiSvc.getNotiUnread(memberVO.getMemberId());
		model.addAttribute("UnreadCount",count);
		
		model.addAttribute("memberVO", memberVO);
		
		return "/front_end/member/checkRoomOrderDetail";
	}	
	
	
	@GetMapping("/checkNotification")
	public String checkNotification(HttpServletRequest req,ModelMap model) {
		
		HttpSession session = req.getSession();
		String email = (String) session.getAttribute("account");
		
		MemberVO memberVO = memSvc.findByEmail(email);
		
		//供WebSocket隨時調用
		Integer count = notiSvc.getNotiUnread(memberVO.getMemberId());
		model.addAttribute("UnreadCount",count);
		
		List<NotificationVO> notiList = notiSvc.findByMemberId( memberVO.getMemberId() );
		model.addAttribute("notiList", notiList);
		
		model.addAttribute("memberVO", memberVO);
		
		return "/front_end/member/checkNotification";
	}
	
	
	
	
	
	
	
	@GetMapping("/ajaxUpdateNoti")
	@ResponseBody
	public String ajaxUpdateNoti(HttpServletRequest req,ModelMap model) {
		System.out.println("Enter HERE");
		HttpSession session = req.getSession();
		String email = (String) session.getAttribute("account");
		
		MemberVO memberVO = memSvc.findByEmail(email);
		
		List<NotificationVO> notiList = notiSvc.findByMemberId( memberVO.getMemberId() );
		
		String jsonArray = gson.toJson(notiList);
		System.out.println("CHANGE TO JSON");
		return jsonArray ;
	}
	
	
	
	
	// 去除BindingResult中某個欄位的FieldError紀錄(尚未解釋)
	public BindingResult removeFieldError(
			MemberVO memberVO, 
			BindingResult result, 
			String removedFieldname) {
		
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname)).collect(Collectors.toList());
		
		result = new BeanPropertyBindingResult(memberVO, "memberVO");
		
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}

}
