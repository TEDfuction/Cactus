package com.member.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.activities_attendees.model.AttendeesVO;
import com.activities_order.model.ActivityOrderService;
import com.activities_order.model.ActivityOrderVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.member.model.TaiwanCity;
import com.notification.model.NotificationService;
import com.notification.model.NotificationVO;
import com.shoporder.model.ShopOrderService;
import com.shoporder.model.ShopOrderVO;
import com.shoporderdetail.model.ShopOrderDetailVO;

@Controller
//@Validated
@RequestMapping("/member")
public class MemberController {

	@Autowired
	MemberService memSvc;
	
	@Autowired
	NotificationService notiSvc;
	
	@Autowired
	ShopOrderService shopOrderSvc;
	
	@Autowired
	ActivityOrderService activityOrderSvc;
	
	Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	
	
	@GetMapping("/LoginTest")
	public String LoginTest(ModelMap model) {
		return "/front_end/member/TestUse" ;
	}
	
	
	@GetMapping("/splitAddress")
	@ResponseBody
	public String splitAddress(HttpSession session) {
		String email = (String)session.getAttribute("account");
		MemberVO memberVO = memSvc.findByEmail(email);
		String address = memberVO.getAddress();
		String[] addressSplit = address.split("-");
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("memcity", addressSplit[0]);
		map.put("memtown", addressSplit[1]);
		map.put("memroad", addressSplit[2]);
		
		String mapJsonString = gson.toJson(map);
		
		return mapJsonString ;
	}
	
	
	@GetMapping("/getEnums")
	@ResponseBody
	public String getEnums() {
		
		List< Map<String,Object> > list = new ArrayList();	
    	for(TaiwanCity tc : TaiwanCity.values()) {
    		Map<String,Object> map = new HashMap();

    		map.put("city", tc.getcity());
    		map.put("towns", tc.getTownships());
    		
    		list.add(map);
    	}
    	
//    	System.out.println(list);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = null;
    	try {
			jsonString = objectMapper.writeValueAsString(list);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	
    	return jsonString;
		
	}
	
	
	
	
	@GetMapping("/getSessionAccount")
	@ResponseBody
	public String getSessionAccount(HttpSession session) {
		String account = (String)session.getAttribute("account");
		
		if(account == null) {
			return null;
		}else {
			return account;
		}
	}
	
	
	@PostMapping("/setLocation")
	@ResponseBody
	public String setLocation(HttpSession session, @RequestBody Map<String, String> jsonData ) {
        
		String location = jsonData.get("location");
        System.out.println("Received string from frontend: " + location);
        session.setAttribute("location", location);
		
		return location;
	}
	
	
	
	
	
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
			@RequestParam("memberPic") MultipartFile[] parts,
			@RequestParam("town")String citytown) throws IOException {
		

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
		System.out.println(citytown);
		String address = citytown + memberVO.getAddress();
		System.out.println(address);
		memberVO.setAddress(address);
		
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
			@RequestParam("memberPic") MultipartFile[] parts,
			@RequestParam("city") String city,
			@RequestParam("town") String town
			) throws IOException {
			
		
		String address = city + "-" + town + "-" + memberVO.getAddress();
		System.out.println(address);
		memberVO.setAddress(address);
		
		
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
		
		if(city == null || town == null) {
			result.addError(new FieldError("memberVO","city","請選擇完整地址"));
		}
		
		if (result.hasErrors()) {
			return "front_end/member/UpdateMember";
		}
		
		memSvc.updateMember(memberVO);
		
		//供WebSocket隨時調用
		Integer count = notiSvc.getNotiUnread(memberVO.getMemberId());
		model.addAttribute("UnreadCount",count);
		
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

	
	
/*************************************************************************/	

	
	
	@GetMapping("/checkActivityOrderDetail")
	public String checkActivityOrderDetail(HttpServletRequest req,ModelMap model) {
		
		HttpSession session = req.getSession();
		String email = (String) session.getAttribute("account");
		
		MemberVO memberVO = memSvc.findByEmail(email);
		
		//供WebSocket隨時調用
		Integer count = notiSvc.getNotiUnread(memberVO.getMemberId());	
		model.addAttribute("UnreadCount",count);
		
		model.addAttribute("memberVO", memberVO);
		
		List<ActivityOrderVO> activityOrderList = activityOrderSvc.findByMemberId(memberVO.getMemberId());
		
		if(activityOrderList.isEmpty()) {
			model.addAttribute("activityOrderList", null);
			return "/front_end/member/checkActivityOrderDetail";
		}
		
		model.addAttribute("activityOrderList", activityOrderList);
		
		return "/front_end/member/checkActivityOrderDetail";
	}	
	
	
	
	@PostMapping("/showActivityOrderDetail")  //顯示訂單詳情內容
	public String showActivityOrderDetail(ModelMap model,
			@RequestParam("activityOrderId") Integer activityOrderId,
			HttpServletRequest req) {
		
		HttpSession session = req.getSession();
		String email = (String) session.getAttribute("account");
		MemberVO memberVO = memSvc.findByEmail(email);
		Integer memberId = memberVO.getMemberId();
		
		//供WebSocket隨時調用
		Integer count = notiSvc.getNotiUnread(memberVO.getMemberId());	
		model.addAttribute("UnreadCount",count);
		
		ActivityOrderVO activityOrderVO = activityOrderSvc.getOneOrder(activityOrderId);
		model.addAttribute("activityOrderVO", activityOrderVO);
		
		Set<AttendeesVO> attendeesSet = activityOrderVO.getAttendeesVO();
		model.addAttribute("attendeesSet",attendeesSet);
		
		List<ActivityOrderVO> activityOrderList = activityOrderSvc.findByMemberId(memberId);
				
		if(activityOrderList.isEmpty()) {
			model.addAttribute("activityOrderList", null);
			return "/front_end/member/checkActivityOrderDetail";
		}
		
		model.addAttribute("activityOrderList", activityOrderList);
		model.addAttribute("memberVO", memberVO);
		
		model.addAttribute("showActivityOrderDetail", "true");

		return "/front_end/member/checkActivityOrderDetail";
	}
	
	
	
/*************************************************************************/	
	
	
	
	@GetMapping("/checkProductOrderDetail")
	public String checkProductOrderDetail(HttpServletRequest req,ModelMap model) {
		
		HttpSession session = req.getSession();
		String email = (String) session.getAttribute("account");
		
		MemberVO memberVO = memSvc.findByEmail(email);
		
		//供WebSocket隨時調用
		Integer count = notiSvc.getNotiUnread(memberVO.getMemberId());	
		model.addAttribute("UnreadCount",count);
		
		model.addAttribute("memberVO", memberVO);
		
		List<ShopOrderVO> shopOrderList = shopOrderSvc.findByMemberId(memberVO.getMemberId());
		
		if(shopOrderList.isEmpty()) {
			model.addAttribute("shopOrderList", null);
			return "/front_end/member/checkProductOrderDetail";
		}
		
		model.addAttribute("shopOrderList", shopOrderList);
		
		return "/front_end/member/checkProductOrderDetail";
	}	
	
	@PostMapping("/showShopOrderDetail")  //顯示訂單詳情內容
	public String showShopOrderDetail(ModelMap model,
			@RequestParam("shopOrderId") Integer shopOrderId,
			HttpServletRequest req) {
		
		HttpSession session = req.getSession();
		String email = (String) session.getAttribute("account");
		MemberVO memberVO = memSvc.findByEmail(email);
		Integer memberId = memberVO.getMemberId();
		
		//供WebSocket隨時調用
		Integer count = notiSvc.getNotiUnread(memberVO.getMemberId());	
		model.addAttribute("UnreadCount",count);
		
		ShopOrderVO shopOrderVO = shopOrderSvc.findById(shopOrderId).get();
		model.addAttribute("shopOrderVO", shopOrderVO);
		
		Set<ShopOrderDetailVO> shopOrderDetailSet = shopOrderVO.getShopOrderDetailVO();
		model.addAttribute("shopOrderDetailSet",shopOrderDetailSet);
		
		List<ShopOrderVO> shopOrderList = shopOrderSvc.findByMemberId(memberId);
				
		if(shopOrderList.isEmpty()) {
			model.addAttribute("shopOrderList", null);
			return "/front_end/member/checkProductOrderDetail";
		}
		
		model.addAttribute("shopOrderList", shopOrderList);
		model.addAttribute("memberVO", memberVO);
		
		model.addAttribute("showShopOrderDetail", "true");

		return "/front_end/member/checkProductOrderDetail";
	}
	
	
	@PostMapping("/cancelShopOrder")
	public String cancelShopOrder(ModelMap model,
			@RequestParam("shopOrderId") Integer shopOrderId,
			HttpServletRequest req){
		
		
		HttpSession session = req.getSession();
		String email = (String) session.getAttribute("account");
		MemberVO memberVO = memSvc.findByEmail(email);
		Integer memberId = memberVO.getMemberId();
		
		
		ShopOrderVO shopOrderVO = shopOrderSvc.findById(shopOrderId).get();
		shopOrderVO.setOrderStatus(0);
		shopOrderSvc.updateOrder(shopOrderVO);
			
		model.addAttribute("memberVO", memberVO);
		
		List<ShopOrderVO> shopOrderList = shopOrderSvc.findByMemberId(memberVO.getMemberId());
		
		if(shopOrderList.isEmpty()) {
			model.addAttribute("shopOrderList", null);
			return "/front_end/member/checkProductOrderDetail";
		}
		
		model.addAttribute("shopOrderList", shopOrderList);
		
		Date date = new Date();
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTime = formatter1.format(date);
		
		//發送通知給會員
		notiSvc.orderCancel(memberId, 2,
				"親愛的"+ memberVO.getMemberName() +"，您好，您的訂單(編號:"+shopOrderVO.getShopOrderId()+")已於"+ nowTime +"取消，希望能再次為您服務，造成您的不便敬請見諒!");

		System.out.println("message has send");
		
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		
		//供WebSocket隨時調用
		Integer count = notiSvc.getNotiUnread(memberVO.getMemberId());	
		model.addAttribute("UnreadCount",count);
		
		return "/front_end/member/checkProductOrderDetail";

	}
	
	
	
/*************************************************************************/	
	
	
	
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
	
	
	
	
/*************************************************************************/	

	
	
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
	
	
	
	
/*************************************************************************/	
	
	
	
	@GetMapping("/ajaxUpdateNoti")
	@ResponseBody
	public String ajaxUpdateNoti(HttpServletRequest req,ModelMap model) {
		HttpSession session = req.getSession();
		String email = (String) session.getAttribute("account");
		
		MemberVO memberVO = memSvc.findByEmail(email);
		
		List<NotificationVO> notiList = notiSvc.findByMemberId( memberVO.getMemberId() );
		
		String jsonArray = gson.toJson(notiList);
		return jsonArray ;
	}
	
	
	
	
	// 去除BindingResult中某個欄位的FieldError紀錄
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
