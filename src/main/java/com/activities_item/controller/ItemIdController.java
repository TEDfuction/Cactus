package com.activities_item.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Digits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.activities_category.model.CategoryVO;
import com.activities_category.model.CategoryService;
import com.activities_item.model.ItemVO;
import com.activities_item.model.ItemService;

@Controller
@Validated
@RequestMapping("/item")
public class ItemIdController {
	
	@Autowired
	ItemService itemService;
	
	@Autowired
	CategoryService categoryService;
	
	/*
	 * This method will be called on select_item.html form submission, handling POST
	 * request It also validates the user input
	 */
	@PostMapping("getOne_For_Display")
	public String getOne_For_Display(
			/***************************1.接收請求參數 - 輸入格式的錯誤處理*************************/
			@Digits(integer = 2, fraction = 0, message = "活動編號：請填寫數字－－勿超過{integer}位數")
			@RequestParam("activityId") String activityId, ModelMap model) {
		/***************************2.開始查詢資料*********************************************/
		ItemVO itemVO = itemService.getOneItem(Integer.valueOf(activityId));
		List<ItemVO> list = itemService.getAll();
		model.addAttribute("itemListData", list);

		model.addAttribute("categoryVO", new CategoryVO());
		List<CategoryVO> list2 = categoryService.getAll();
		model.addAttribute("categoryListData2", list2);
		
		if(itemVO == null) {
			model.addAttribute("errorMessage", "查無資料");
			return "back_end/item/select_item";
		}
		/***************************3.查詢完成,準備轉交(Send the Success view)*****************/
		model.addAttribute("itemVO", itemVO);
		model.addAttribute("getOne_For_Display", "true");
		
		return "back_end/item/select_item";
	}
	
	@ExceptionHandler(value = {ConstraintViolationException.class})
	public ModelAndView handleError(HttpServletRequest req, ConstraintViolationException e , Model model) {
		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		StringBuilder strBuilder = new StringBuilder();
		for(ConstraintViolation<?> violation : violations) {
			strBuilder.append(violation.getMessage() + "<br>");
		}
		
		//==== 以下是當前面第64行返回 /src/main/resources/templates/back_end/item/select_item.html用的 ====   

		model.addAttribute("itemVO", new ItemVO()); //設定name
//		ItemService itemService = new ItemService();
		List<ItemVO> list = itemService.getAll();
		model.addAttribute("itemListData", list);     // 設定value  for select_item.html 
		model.addAttribute("categoryVO", new CategoryVO());
		List<CategoryVO> list2 = categoryService.getAll();
		model.addAttribute("categoryListData2", list2);
		String message = strBuilder.toString();
		return new ModelAndView("back_end/item/select_item", "errorMessage", "請修正以下錯誤:<br>"+message);
			
			
			
		
	}
	
	

}
