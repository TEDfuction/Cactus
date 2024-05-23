package com.product.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.product.model.*;
import com.productcategory.model.ProductCategoryService;
import com.productcategory.model.ProductCategoryVO;

@Controller
@RequestMapping("/product")//網頁網址式/product/addProduct才會出現
public class ProductController {
	
	@Autowired
	ProductService productSvc;
	
	@Autowired
	ProductCategoryService ProductCategorySvc;
	
	
	@GetMapping("/historyProduct")
	public String historyProduct(Model model) {
		return "back_end/product/historyProduct";
	}
	
	@GetMapping("/select_page")
	public String select_page(Model model) {
		return "back_end/product/select_page";
	}

	@GetMapping("/shop_index")
	public String shop(Model model) {
		return "front_end/product/shop_index";
	}

	@GetMapping("/listAllProduct1")
	public String listAllEmp(Model model) {
		return "back_end/product/listAllProduct";
	}


	
	@ModelAttribute("productListData") // for select_page.jsp 第96 108行用 // for listAllEmp.jsp 第68行用//ChatGpt鍵是empListData，值是list
		protected List<ProductVO> referenceListProductName(Model model) {
//	   	model.addAttribute("productVO", new ProductVO());
//	   	ProductService productSvc = new ProductService();
			List<ProductVO> list = productSvc.getAll();
			return list;
		}



	@ModelAttribute("productCategoryListData") // for select_page.jsp 第96 108行用 // for listAllEmp.jsp 第68行用//ChatGpt鍵是empListData，值是list
	protected List<ProductCategoryVO> referenceListData_productCategory(Model model) {
		model.addAttribute("productCategoryVO", new ProductCategoryVO());
//		ProductService productSvc = new ProductService();
		List<ProductCategoryVO> list = ProductCategorySvc.getAll();
		return list;
	}
	

	@GetMapping("addProduct")
	public String addEmp(ModelMap model) {
		ProductVO productVO = new ProductVO();
		model.addAttribute("productVO", productVO);
		return "back_end/product/addProduct";
	}
	
	@PostMapping("insert")//對應到addEmp  56行 name名稱
	public String insert(@Valid ProductVO productVO, BindingResult result, ModelMap model,
			@RequestParam("productPhoto") MultipartFile[] parts) throws IOException {
		
		/***************************1.接收請求參數 - 輸入格式的錯誤處理******************/
		result = removeFieldError(productVO, result, "productPhoto");
		
		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的圖片時
			System.out.println("上傳照片");
			model.addAttribute("errorMessage", "商品照片: 請上傳照片");
		} else {
			System.out.println("圖片新增");
			for (MultipartFile multipartFile : parts) {
				byte[] buf = multipartFile.getBytes();
				productVO.setProductPhoto(buf);
			}
		}
		
//		 if (!productPhoto.isEmpty()) {
//		        try {
//		            // 讀取文件內容到字節數組
//		            byte[] bytes = productPhoto.getBytes();
//		            productVO.setProductPhoto(bytes);
//		        } catch (IOException e) {
//		            // 處理讀取文件時的錯誤
//		            model.addAttribute("errorMessage", "讀取文件錯誤");
//		            return "back-end/product/addProduct";
//		        }
//		    } else {
//		        model.addAttribute("errorMessage", "商品照片: 請上傳照片");
//		    }
		
//		if (result.hasErrors()) {
//			System.out.println("有錯誤");
//			return "back-end/product/addProduct";
//		}
		
		if (result.hasErrors()) {
		    System.out.println("驗證錯誤：");
		    for (FieldError error : result.getFieldErrors()) {
		        System.out.println(error.getField() + ": " + error.getDefaultMessage());
		    }
		    return "back_end/product/addProduct";
		}
		/***************************2.開始新增資料***************************************/
//		ProductService productSvc = new ProductService();
		productSvc.addProduct(productVO);
		/***************************3.新增完成,準備轉交(Send the Success view)***********/
		List<ProductVO> list = productSvc.getAll();
		model.addAttribute("productListData", list);
		model.addAttribute("success", "- (新增成功)");
		System.out.println("成功新增");
		return "back_end/product/listAllProduct"; // 新增成功後轉交listAllProduct.jsp
	}
	
	/*
	 * This method will be called on listAllEmp.jsp form submission, handling POST request It also validates the user input
	 */
	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("productId") String productId, ModelMap model) {
		/***************************1.接收請求參數 - 輸入格式的錯誤處理******************/
		/***************************2.開始查詢資料***************************************/
//		ProductService empSvc = new ProductService();
		ProductVO productVO = productSvc.getOneProduct(Integer.valueOf(productId));
		
		/***************************3.查詢完成,準備轉交(Send the Success view)***********/
		model.addAttribute("productVO", productVO);
		model.addAttribute("getOne_For_Update", "true");
		
		return "back_end/product/update_product_input"; // 查詢完成後轉交update_product_input.jsp
//		return "back-end/product/listAllProduct";
	}
	
	
	/*
	 * This method will be called on update_emp_input.jsp form submission, handling POST request It also validates the user input
	 */
	@PostMapping("update")
	public String update(@Valid ProductVO productVO, BindingResult result, ModelMap model,
			@RequestParam("productPhoto") MultipartFile[] parts) throws IOException {
		 // 使用者未選擇要上傳的新圖時
//		if(productVO.getProductPhoto().length==0) {
//			// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第151行
//			result = removeFieldError(productVO, result, "productPhoto");//把錯誤訊息給去掉,修改保持原圖也可以上傳
//	        // 從DB取出原upFiles的原byte[]置入empVO (用舊圖去update資料庫)
//	        ProductService empSvc = new ProductService();
////			byte[] upFiles = empSvc.getOneProduct(productVO.getProduct_id()).getProductPhoto();
////			productVO.setProductPhotobyte(upFiles);
////		}
		
		result = removeFieldError(productVO, result, "productPhoto");
        if (parts[0].isEmpty()) { // 使用者未選擇要上傳的新圖片時
        	byte[] upFiles = productSvc.getOneProduct(productVO.getProductId()).getProductPhoto();
        	productVO.setProductPhoto(upFiles);
		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] upFiles = multipartFile.getBytes();
				productVO.setProductPhoto(upFiles);
			}
		}

		/***************************1.接收請求參數 - 輸入格式的錯誤處理******************/
		if (result.hasErrors()) {
			return "back_end/product/update_product_input";
		}
		/***************************2.開始修改資料***************************************/
//		ProductService productSvc = new ProductService();
		productSvc.updateProduct(productVO);

		/***************************3.修改完成,準備轉交(Send the Success view)***********/
		model.addAttribute("success", "修改成功");
		productVO = productSvc.getOneProduct(Integer.valueOf(productVO.getProductId()));
		model.addAttribute("productVO", productVO);
		return "back_end/product/listOneProduct"; // 修改成功後轉交listOneEmp.jsp
	}
	
	
	/*
	 * 【 第二種作法 】 Method used to populate the Map Data in view.
	 *  如 : <form:select path="deptno" id="deptno" items="${mapData}" />
	 */
	@ModelAttribute("mapData") //
	protected Map<Boolean, String> referenceMapData() {
		Map<Boolean, String> mapData = new LinkedHashMap<Boolean, String>();
		mapData.put(true, "上架");
		mapData.put(false, "下架");
		return mapData;
	}
	
	@ModelAttribute("productCategoryListData")
	protected List<ProductCategoryVO> referenceListData() {
		// DeptService deptSvc = new DeptService();
		List<ProductCategoryVO> list = ProductCategorySvc.getAll();
		return list;
	}
	
	
	// 去除BindingResult中某個欄位的FieldError紀錄
		public BindingResult removeFieldError(ProductVO productVO, BindingResult result, String removedFieldname) {
			List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
					.filter(fieldname -> !fieldname.getField().equals(removedFieldname))
					.collect(Collectors.toList());
			result = new BeanPropertyBindingResult(productVO, "productVO");
			for (FieldError fieldError : errorsListToKeep) {
				result.addError(fieldError);
			}
			return result;
		}
		
		/*
		 * This method will be called on select_page.html form submission, handling POST request
		 */
		@PostMapping("listProducts_ByCompositeQuery")
		public String listAllEmp(HttpServletRequest req, Model model) {
			Map<String, String[]> map = req.getParameterMap();
			List<ProductVO> list = productSvc.getAll(map);//關鍵行
			model.addAttribute("productListData", list); // for listAllEmp.html 第85行用
			return "back_end/product/listAllProduct";
		}
		
		
		
		
		
		
		

		@GetMapping("/listAllProduct")
		public String listAllProduct(ModelMap model) {
			
			List<ProductVO> list1 = productSvc.getAll();
			model.addAttribute("productList", list1);
			
			List<ProductCategoryVO> list2 = ProductCategorySvc.getAll();
			model.addAttribute("productCategoryList", list2);
			return "/front_end/product/shop_index" ;
		}
		
		@GetMapping("/listOneProduct")
		public String listOneProduct(ModelMap model,
				@RequestParam("productId") String productId) {
			ProductVO productVO = productSvc.findById( Integer.valueOf(productId) );
			model.addAttribute("productVO", productVO);
			return "/front_end/product/shop_single" ;
		}
		
		
		
		
	/*******************************************************************/
		
		
//		@GetMapping("/listAllTest")
//		public String listAllTest(ModelMap model) {
//			List<ProductVO> list = productSvc.getAll();
//			model.addAttribute("productList", list);
//			return "/front_end/product/shop_indexORI";
//		}
//
//	    @GetMapping("/listOneTest/{productId}")
//	    public String listOneTest(@PathVariable Integer productId, ModelMap model) {
//	    	System.out.println("aaa");
//	        ProductVO productVO = productSvc.findById(productId);
//	        model.addAttribute("productVO", productVO);
//	        return "/front_end/product/shop_singleORI";
//	    }
		
		
}
