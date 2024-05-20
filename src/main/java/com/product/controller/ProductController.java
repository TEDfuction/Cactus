package com.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.product.model.ProductServiceImpl;
import com.product.model.ProductVO;
import com.productcategory.model.ProductCategoryService;
import com.productcategory.model.ProductCategoryVO;

@Controller
//@Validated
@RequestMapping("/product")

public class ProductController {
	
	@Autowired
	ProductServiceImpl productSvc;
	
	@Autowired
	ProductCategoryService productCategorySvc;

	
	@GetMapping("listAllProduct")
	public String listAllProduct(ModelMap model) {
		
		List<ProductVO> list1 = productSvc.getAll();
		model.addAttribute("productList", list1);
		
		List<ProductCategoryVO> list2 = productCategorySvc.getAll();
		model.addAttribute("productCategoryList", list2);
		return "/front_end/product/shop_index" ;
	}
	
	@GetMapping("listOneProduct")
	public String listOneProduct(ModelMap model,
			@RequestParam("productId") String productId) {
		ProductVO productVO = productSvc.findById( Integer.valueOf(productId) );
		model.addAttribute("productVO", productVO);
		return "/front_end/product/shop_single" ;
	}
	
	
	
	
/*******************************************************************/
	
	
	@GetMapping("/listAllTest")
	public String listAllTest(ModelMap model) {
		List<ProductVO> list = productSvc.getAll();
		model.addAttribute("productList", list);
		return "/front_end/product/shop_indexORI";
	}

    @GetMapping("/listOneTest/{productId}")
    public String listOneTest(@PathVariable Integer productId, ModelMap model) {
    	System.out.println("aaa");
        ProductVO productVO = productSvc.findById(productId);
        model.addAttribute("productVO", productVO);
        return "/front_end/product/shop_singleORI";
    }
	
	
	

	
	
}
