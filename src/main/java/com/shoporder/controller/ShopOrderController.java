package com.shoporder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.shoporder.model.ShopOrderService;
import com.shoporder.model.ShopOrderVO;

@Controller
@RequestMapping("/shopOrder")
public class ShopOrderController {
	
	
	@Autowired
	MemberService memSvc;
	
	@Autowired
	ShopOrderService shopOrderSvc;
	
	

	@GetMapping("/shopOrderSearch")
	public String shopOrderSearch(ModelMap model) {
		List<MemberVO> list = memSvc.getAll();
		model.addAttribute("memberList", list);
		return "/back_end/product/shopOrderSearch";
	}
	
	@GetMapping("/showAllShopOrder")
	public String showAllShopOrder(ModelMap model) {
		List<ShopOrderVO> list = shopOrderSvc.getAll();
		model.addAttribute("shopOrderList", list);
		return "/back_end/product/showAllShopOrder";
	}
	
	@PostMapping("getShopOrderDetail")
	@ResponseBody
	public String getShopOrderDetail() {
		
		return "";
	}
	
	@PostMapping("/showOneShopOrder")
	public String showOneShopOrder() {
		
		return "/back_end/product/showOneShopOrder";
	}
}
