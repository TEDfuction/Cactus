package com.shoporder.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.shoporder.model.ShopOrderService;
import com.shoporder.model.ShopOrderVO;
import com.shoporderdetail.model.ShopOrderDetailService;
import com.shoporderdetail.model.ShopOrderDetailVO;

@Controller
@RequestMapping("/shopOrder")
public class ShopOrderController {
	
	
	@Autowired
	MemberService memSvc;
	
	@Autowired
	ShopOrderService shopOrderSvc;
	
	@Autowired
	ShopOrderDetailService shopOrderDetailSvc;
	
	

	@GetMapping("/shopOrderSearch")
	public String shopOrderSearch(ModelMap model) {
		
		//給下拉選單用
		List<MemberVO> list = memSvc.getAll();
		model.addAttribute("memberList", list);
		return "/back_end/product/shopOrderSearch";
	}
	
	@GetMapping("/showAllShopOrder")
	public String showAllShopOrder(ModelMap model) {
		
		//listAll用
		List<ShopOrderVO> list = shopOrderSvc.getAll();
		model.addAttribute("shopOrderList", list);
		return "/back_end/product/showAllShopOrder";
	}
	
	
	@PostMapping("/showOneShopOrder") //單一下拉式選單搜尋
	public String showOneShopOrder(ModelMap model,
			@RequestParam("memberId")Integer memberId) {
		
		//listOne用
		List<ShopOrderVO> list = shopOrderSvc.findByMemberId(memberId);
		
		if(list.isEmpty()) {
			model.addAttribute("shopOrderList", null);
			return "/back_end/product/showOneShopOrder";
		}
		model.addAttribute("shopOrderList", list);
		return "/back_end/product/showOneShopOrder";
	}
	
	
	@PostMapping("/orderDetailFromShowAll")  //顯示訂單詳情內容 - 從全部訂單
	public String orderDetailFromShowAll(ModelMap model,
			@RequestParam("shopOrderId") Integer shopOrderId) {
				
		ShopOrderVO shopOrderVO = shopOrderSvc.findById(shopOrderId).get();
		model.addAttribute("shopOrderVO", shopOrderVO);
		
		Set<ShopOrderDetailVO> shopOrderDetailSet = shopOrderVO.getShopOrderDetailVO();
		model.addAttribute("shopOrderDetailSet",shopOrderDetailSet);
		
		//listAll用
		List<ShopOrderVO> list = shopOrderSvc.getAll();
		model.addAttribute("shopOrderList", list);
		
		model.addAttribute("orderDetailFromAll", "true");
		return "/back_end/product/showAllShopOrder";
//		return "/back_end/product/orderDetailFromShowAll";
	}
	
	@PostMapping("/orderDetailFromShowOne")  //顯示訂單詳情內容 - 從單一訂單
	public String orderDetailFromShowOne(ModelMap model,
			@RequestParam("shopOrderId") Integer shopOrderId,
			@RequestParam("memberId") Integer memberId) {
		
		
		ShopOrderVO shopOrderVO = shopOrderSvc.findById(shopOrderId).get();
		model.addAttribute("shopOrderVO", shopOrderVO);
		
		Set<ShopOrderDetailVO> shopOrderDetailSet = shopOrderVO.getShopOrderDetailVO();
		model.addAttribute("shopOrderDetailSet",shopOrderDetailSet);
		
		//listOne用
		List<ShopOrderVO> list = shopOrderSvc.findByMemberId(memberId);
				
		if(list.isEmpty()) {
			model.addAttribute("shopOrderList", null);
			return "/back_end/product/showOneShopOrder";
		}
		model.addAttribute("shopOrderList", list);
		
		model.addAttribute("orderDetailFromOne", "true");

		return "/back_end/product/showOneShopOrder";
	}
	
	@PostMapping("/changeStatus")
	@ResponseBody
	public String changeStatus (@RequestBody Map<String, Object> requestData) {
		String orderIdString = (String) requestData.get("orderId");
		Integer orderId = Integer.parseInt(orderIdString);
		
		String orderStatusString = (String) requestData.get("orderStatus");
		Integer orderStatus = Integer.parseInt(orderStatusString);
		
		ShopOrderVO shopOrderVO = shopOrderSvc.findById(orderId).get();
		shopOrderVO.setOrderStatus(orderStatus);
		shopOrderSvc.updateOrder(shopOrderVO);
		
//		System.out.println(orderId);
//		System.out.println(orderStatus);
		
		return "修改成功";
	}

}
