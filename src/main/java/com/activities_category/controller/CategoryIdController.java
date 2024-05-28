package com.activities_category.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Digits;

import com.activities_category.model.CategoryService;
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


@Controller
@Validated
@RequestMapping("/category")
public class CategoryIdController {
	
	@Autowired
	CategoryService categoryService;
	
	/*
	 * This method will be called on select_category.html form submission, handling POST
	 * request It also validates the user input
	 */
	@PostMapping("getOne_For_Display")
	public String getOne_For_Display(
			/***************************1.接收請求參數 - 輸入格式的錯誤處理*************************/
			@Digits(integer = 1, fraction = 0, message = "活動類別編號：請填寫數字－－勿超過{integer}位數")
			@RequestParam("activityCategoryId") String activityCategoryId, 
			ModelMap model) {
		/***************************2.開始查詢資料*********************************************/
		CategoryVO categoryVO = categoryService.getOneCategory(Integer.valueOf(activityCategoryId));
		
		List<CategoryVO> list = categoryService.getAll();
		model.addAttribute("categoryListData", list);
		
		if(categoryVO == null) {
			model.addAttribute("errorMessage", "查無資料");
			return "back_end/category/select_category";
		}
		
		/***************************3.查詢完成,準備轉交(Send the Success view)*****************/
		model.addAttribute("categoryVO", categoryVO);
		model.addAttribute("getOne_For_Display", "true");
		
		return "back_end/category/select_category"; // 查詢完成後轉交select_page.html由其第158行insert listOneEmp.html內的th:fragment="listOneEmp-div
	}
	
	@ExceptionHandler(value = {ConstraintViolationException.class})
	public ModelAndView handleError(HttpServletRequest req, ConstraintViolationException e , Model model) {
		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		StringBuilder strBuilder = new StringBuilder();
		for(ConstraintViolation<?> violation : violations) {
			strBuilder.append(violation.getMessage() + "<br>");
			
		}
		
		//==== 以下是當前面第59行返回 /src/main/resources/templates/back_end/emp/select_page.html用的 ====   

		List<CategoryVO> list = categoryService.getAll();
		model.addAttribute("categoryListData", list);     // for select_page.html 第97 109行用
		
		String message = strBuilder.toString();
	    return new ModelAndView("back_end/category/select_category", "errorMessage", "請修正以下錯誤:<br>"+message);

	}
}
