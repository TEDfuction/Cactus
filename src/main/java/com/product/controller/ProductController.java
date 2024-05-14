package com.product.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.product.model.ProductServiceImpl;
import com.product.model.ProductVO;

@Controller
//@Validated
@RequestMapping("/product")

public class ProductController {
	
	@Autowired
	ProductServiceImpl productSvc;

	
	@GetMapping("listAllProduct")
	public String listAllProduct(ModelMap model) {
		List<ProductVO> list = productSvc.getAll();
		model.addAttribute("productList", list);
		return "/front_end/product/listAllProduct" ;
	}
	
	@GetMapping("listOneProduct")
	public String listOneProduct(ModelMap model,
			@RequestParam("productId") String productId) {
		ProductVO productVO = productSvc.findById( Integer.valueOf(productId) );
		model.addAttribute("productVO", productVO);
		return "/front_end/product/listOneProduct" ;
	}
	
	
	
	
	
	
	
	
	// 商城首頁/全商品瀏覽
    @GetMapping("/shopAllProduct")
    public String allProduct(ModelMap model) {
        List<ProductVO> productList = productSvc.getAll();
        model.addAttribute("productList", productList);
        return "front_end/product/shop_index";
    }

    // 單一品項瀏覽
    @GetMapping("/shopSingleProduct/{productId}")
    public String singleProduct(@PathVariable Integer productId, ModelMap model) {
        ProductVO productVO = productSvc.findById(productId);
        model.addAttribute("product", productVO);

//        model.addAttribute("imagePaths", getImageList(productId));

        return "front_end/product/shop_single";
    }

//    @GetMapping("/shopCategoryProduct/{categoryId}")
//    public String categoryProduct(@PathVariable Integer categoryId, Model model) {
//        List<ProductVO> productList = productService.findByCategoryId(categoryId);
//        model.addAttribute("productList", productList);
//        return "frontend/product/shop_index";
//    }
	
	
	
	
	
}
