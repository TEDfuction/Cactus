package com.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.product.model.ProductServiceImpl;
import com.product.model.ProductVO;

@Controller
//@Validated
@RequestMapping("/product")

public class ProductController {
	
	@Autowired
	ProductServiceImpl productService;
	
	
	@GetMapping
	public String product(ModelMap model) {
		List<ProductVO> list = productService.getAll();
		model.addAttribute("productList", list);
		return "/front_end/product/product" ;
	}
}
