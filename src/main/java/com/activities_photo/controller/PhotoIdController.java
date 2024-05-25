package com.activities_photo.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import com.activities_photo.model.PhotoService;
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

import com.activities_item.model.ItemService;
import com.activities_item.model.ItemVO;
import com.activities_photo.model.PhotoVO;


@Controller
@Validated
@RequestMapping("/activity")
public class PhotoIdController {
	
	@Autowired
	PhotoService phoSvc;
	
	@Autowired
	ItemService  itemSvc;

	/*
	 * This method will be called on select_page.html form submission, handling POST
	 * request It also validates the user input
	 */
	
	
	
	
	@PostMapping("getOne_For_Display")
	public String getOne_For_Display(
		/***************************1.接收請求參數 - 輸入格式的錯誤處理*************************/
		@NotEmpty(message="圖片編號: 請勿空白")
		@Digits(integer = 2, fraction = 0, message = "員工編號: 請填數字-請勿超過{integer}位數")
		@Min(value = 1, message = "員工編號: 不能小於{value}")
		@Max(value = 100, message = "員工編號: 不能超過{value}")
		@RequestParam("activityPhotoId") String activityPhotoId,
		ModelMap model) {
		
		/***************************2.開始查詢資料*********************************************/
//		EmpService empSvc = new EmpService();
		PhotoVO photoVO = phoSvc.getOnePhoto(Integer.valueOf(activityPhotoId));
		
		List<PhotoVO> list = phoSvc.getAll();
		model.addAttribute("photoListData", list);     // for select_page.html 第97 109行用
		model.addAttribute("itemVO", new ItemVO());  // for select_page.html 第133行用
		List<ItemVO> list2 = itemSvc.getAll();
    	model.addAttribute("itemListData",list2);    // for select_page.html 第135行用
		
		if (photoVO == null) {
			model.addAttribute("errorMessage", "查無資料");
			return "back_end/activity/select_photo";
		}
		
		/***************************3.查詢完成,準備轉交(Send the Success view)*****************/
		model.addAttribute("photoVO", photoVO);
		model.addAttribute("getOne_For_Display", "true"); // 旗標getOne_For_Display見select_page.html的第156行 -->
		
//		return "back-end/emp/listOneEmp";  // 查詢完成後轉交listOneEmp.html
		return "back_end/activity/select_photo"; // 查詢完成後轉交select_page.html由其第158行insert listOneEmp.html內的th:fragment="listOneEmp-div
	}

	
	@ExceptionHandler(value = { ConstraintViolationException.class })
	//@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ModelAndView handleError(HttpServletRequest req,ConstraintViolationException e,Model model) {
	    Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
	    StringBuilder strBuilder = new StringBuilder();
	    for (ConstraintViolation<?> violation : violations ) {
	          strBuilder.append(violation.getMessage() + "<br>");
	    }
	    //==== 以下第92~96行是當前面第77行返回 /src/main/resources/templates/back-end/emp/select_page.html用的 ====   
//	    model.addAttribute("empVO", new EmpVO());
//    	EmpService empSvc = new EmpService();
		List<PhotoVO> list = phoSvc.getAll();
		model.addAttribute("photoListData", list);     // for select_page.html 第97 109行用
		model.addAttribute("itemVO", new ItemVO());  // for select_page.html 第133行用
		List<ItemVO> list2 = itemSvc.getAll();
    	model.addAttribute("itemListData",list2);    // for select_page.html 第135行用
		String message = strBuilder.toString();
	    return new ModelAndView("back_end/activity/select_photo", "errorMessage", "請修正以下錯誤:<br>"+message);
	}
	
}