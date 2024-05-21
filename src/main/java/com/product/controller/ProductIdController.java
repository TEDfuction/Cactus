package com.product.controller;

import javax.servlet.http.HttpServletRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import com.product.model.ProductService;
import com.product.model.ProductVO;


@Controller
@Validated
@RequestMapping("/product")
public class ProductIdController {
	
	
	@Autowired
	ProductService productSvc;
	
	@PostMapping("getOne_For_Display")
	public String getOne_For_Display(
		/***************************1.接收請求參數 - 輸入格式的錯誤處理*************************/
		@NotEmpty(message="商品編號: 請勿空白")
		@Digits(integer = 4, fraction = 0, message = "商品編號: 請填數字-請勿超過{integer}位數")
		@Min(value = 1, message = "商品編號: 不能小於{value}")
		@Max(value = 999, message = "商品編號: 不能超過{value}")
//		@RequestParam("productId") String productId,
		@RequestParam("productId") String productId,
		ModelMap model
		) {
		
		/***************************2.開始查詢資料*********************************************/
//		ProductService productSvc = new ProductService();
		ProductVO productVO = productSvc.getOneProduct(Integer.valueOf(productId));
		
		List<ProductVO> list = productSvc.getAll();
		model.addAttribute("productListData", list);  // for select_page.jsp 第96 108行用
//		model.addAttribute("productVO", new ProductVO()); // for select_page.jsp 第94 106行用
		
		if (productVO == null) {//empVO更改成productVO
			model.addAttribute("errorMessage", "查無資料");
			return "back_end/product/select_page";
		}
		
		/***************************3.查詢完成,準備轉交(Send the Success view)*****************/
		model.addAttribute("productVO", productVO);
		model.addAttribute("getOne_For_Display", "true"); // 旗標getOne_For_Display見select_page.jsp的第125行 -->
		
//		return "back-end/product/listOneProduct";  // 查詢完成後轉交listOneEmp.jsp
		return "back_end/product/select_page"; // 查詢完成後轉交select_page.jsp由其第127行include file="listOneEmp-div-fragment.file"
	}

	//方法級別驗證用(後面再研究)//有這個上面的錯誤才可以進行驗證
	@ExceptionHandler(value = { ConstraintViolationException.class })
	//@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ModelAndView handleError(HttpServletRequest req,ConstraintViolationException e,Model model) {
	    Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
	    StringBuilder strBuilder = new StringBuilder();
	    for (ConstraintViolation<?> violation : violations ) {
	          strBuilder.append(violation.getMessage() + "<br>");
	    }
	    //==== 以下第80~83行是當前面第66行返回 /WEB-INF/views/back-end/emp/select_page.jsp用的 ====   
//	    model.addAttribute("empVO", new EmpVO());
//    	ProductService empSvc = new ProductService();
//    	List<ProductVO> list = productSvc.getAll();
//		model.addAttribute("productListData", list);  // for select_page.jsp 第96 108行用
//		model.addAttribute("productVO", new ProductVO()); // for select_page.jsp 第94 106行用
		
		String message = strBuilder.toString();
	    return new ModelAndView("back_end/product/select_page", "errorMessage", "請修正以下錯誤:<br>"+message);
	}
	
}