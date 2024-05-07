package com.product.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.product.model.ProductService;
import com.product.model.ProductVO;
import com.productcategory.model.ProductCategoryVO;


@WebServlet("/ProductServlet")
//@MultipartConfig(fileSizeThreshold = 0 * 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
@MultipartConfig
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ProductServlet() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=Big5");
		
		String action = request.getParameter("action");
		
		if("getOne_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			request.setAttribute("errorMsgs", errorMsgs);
			
			//**************1.接受參數 輸入格式的錯誤處理*******************
			String str = request.getParameter("productId");
			
			if(str == null || (str.trim().length() == 0)) {
				errorMsgs.add("請輸入商品編號");
			}
		
			Integer productId = null;
			
			try {
				productId = Integer.valueOf(str);
			}catch(Exception e) {
				errorMsgs.add("商品編號不正確");
			}
			//***寄送錯誤訊息*****
			if(!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = request.getRequestDispatcher("/shop/product_select_page.jsp");//路徑是否正確
				failureView.forward(request, response);
				return;
			}
			//*******************查詢資料**********************
			ProductService productSvc = new ProductService();
			ProductVO productVO = productSvc.getOneProduct(productId);
			if(productVO == null) {
				errorMsgs.add("查無資料");
			}
			//*********寄送錯誤訊息************
			if(!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = request.getRequestDispatcher("/shop/product_select_page.jsp");//路徑是否正確
				failureView.forward(request, response);
				return;
			}
			//*********轉交資料***********
			request.setAttribute("productVO", productVO);
			String url = "/shop/listOneProduct.jsp";
			RequestDispatcher successView = request.getRequestDispatcher(url);
			successView.forward(request, response);
		}
		
		
		if("getOne_For_Update".equals(action)) {//來自listAllProduct.jsp的請求
			List<String> errorMsgs = new LinkedList<String>();
			
			request.setAttribute("errorMsgs", errorMsgs);
			
			//******************接受請求參數******************
			Integer productId = Integer.valueOf(request.getParameter("productId"));
//			System.out.println("productId = " + productId);
			
			//******************開始查詢資料******************
			ProductService productSvc = new ProductService();
			ProductVO productVO = productSvc.getOneProduct(productId);
			
			//*****************查詢完成，準備繳交***************
			request.setAttribute("productVO", productVO);
			String url = "/shop/update_product_input.jsp";
			RequestDispatcher successView = request.getRequestDispatcher(url);
			successView.forward(request, response);
		}
		
		
		//新增資料
		if("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<>();
			request.setAttribute("errorMsgs", errorMsgs);
			
			//*******************接受參數 -- 輸入格式錯誤處理**************
			String productName = request.getParameter("productName");
			System.out.println(productName);
			String productReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";//要注意
			if(productName == null || productName.trim().length() == 0) {
				errorMsgs.add("商品名稱: 請勿空白");
			}else if(!productName.trim().matches(productReg)) {
				errorMsgs.add("商品名稱: 只能是中、英文字母、數字和_");
			}
			
			Integer productCategoryId = null;
			
			try {
				productCategoryId = Integer.valueOf(request.getParameter("productCategoryId").trim());
			}catch (NumberFormatException e) {
				errorMsgs.add("商品類型編號請填數字");
			}
			
			String productDescribtion = request.getParameter("productDescribtion");
//			String productDescribtionReg = ""
			if(productDescribtion == null || productDescribtion.trim().length() == 0) {
				errorMsgs.add("商品內容，請勿空白");
			}
			
			Integer productPrice = null;
			
			try {
				productPrice = Integer.valueOf(request.getParameter("productPrice").trim());
			}catch(NumberFormatException e) {
				errorMsgs.add("商品價格請填數字");
			}
			//要注意 不會
			Boolean productStatus = null;
			if(request.getParameter("productStatus").trim().equals("true")) {
				productStatus = Boolean.valueOf(true);
			}else if(request.getParameter("productStatus").trim().equals("false")) {
				productStatus = Boolean.valueOf(false);
			}
			
			
			//圖片新增
			byte[] upFiles = null;
				
			Part part = request.getPart("upFiles");
			
				InputStream in = part.getInputStream();
				if(in.available()!=0) {
					upFiles = new byte[in.available()];
					in.read(upFiles);
					in.close();
				}else {
					errorMsgs.add("商品照片: 請上傳照片");
				}
							
			
			ProductCategoryVO productCategoryVO = new ProductCategoryVO();
			productCategoryVO.setProductCategotyId(productCategoryId);
			
			//新增商品資料
			ProductVO productVO = new ProductVO();
			
			productVO.setProductName(productName);
			productVO.setProductCategoryVOs(productCategoryVO);
			productVO.setProductDescribtion(productDescribtion);
			productVO.setProductPrice(productPrice);
			productVO.setProductStatus(productStatus);
			productVO.setProductPhoto(upFiles);
			
			
			//新增從屬的商品圖片物件，從service抓的
//			ProductPhotoVo productPhotoVO = new ProductPhotoVo();
//			productPhotoVO.setProductPhoto(upFiles);
			
			//set關聯的資料
//			productPhotoVO.setProductVO(productVO);
			
			//productVO放入productPhotoVos物件,讓productVO可以調productPhotoVos資訊
//			Set<ProductPhotoVo> productPhotoVos = new LinkedHashSet<>();
//			productPhotoVos.add(productPhotoVO);
			//此商品新增時，跟著要一起新增的商品圖片
//			productVO.setProductPhotoVos(productPhotoVos);
			
			
			if(!errorMsgs.isEmpty()) {
				request.setAttribute("productVO", productVO);
				RequestDispatcher failureView = request.getRequestDispatcher("/shop/addProduct.jsp");
				failureView.forward(request, response);
				return;
			}
			
			//**************新增資料**************
			try {
				ProductService productSvc = new ProductService();
				productVO = productSvc.addProduct(productCategoryId, productDescribtion, productPrice, productName, productStatus, upFiles);
			}catch(Exception e){
				System.out.println("這裡有錯");
			}
			
			
			//*************新增完成轉交資料***********
			String url = "/shop/listAllProduct.jsp";
			RequestDispatcher successView = request.getRequestDispatcher(url); // 新增成功後轉交
			successView.forward(request, response);	
		}
		
		//修改
		if("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<>();
			request.setAttribute("errorMsgs", errorMsgs);
			
			//*******************接受參數 -- 輸入格式錯誤處理**************
			Integer productId = Integer.valueOf(request.getParameter("productId").trim());
			
			
			String productName = request.getParameter("productName");
			System.out.println(productName);
			
			String productReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_ )]{1,}$";//要注意
			if(productName == null || productName.trim().length() == 0) {
				errorMsgs.add("商品名稱: 請勿空白");
			}else if(!productName.trim().matches(productReg)) {
				errorMsgs.add("商品名稱: 只能是中、英文字母、數字和_");
			}
			
			Integer productCategoryId = null;
			
			try {
				productCategoryId = Integer.valueOf(request.getParameter("productCategoryId").trim());
			}catch (NumberFormatException e) {
				errorMsgs.add("商品類型編號請填數字");
			}
			
			String productDescribtion = request.getParameter("productDescribtion");
//			String productDescribtionReg = ""
			if(productDescribtion == null || productDescribtion.trim().length() == 0) {
				errorMsgs.add("商品內容，請勿空白");
			}
			
			Integer productPrice = null;
			
			try {
				productPrice = Integer.valueOf(request.getParameter("productPrice").trim());
			}catch(NumberFormatException e) {
				errorMsgs.add("商品價格請填數字");
			}
			
			Boolean productStatus = null;
			if(request.getParameter("productStatus").trim().equals("true")) {
				productStatus = Boolean.valueOf(true);
			}else if(request.getParameter("productStatus").trim().equals("false")) {
				productStatus = Boolean.valueOf(false);
			}
			
//			try{
//				productStatus = Boolean.valueOf(request.getAttribute("productStatus"));
//			}
			
			//圖片修改
//			byte[] upFiles = null;
			
//			String saveDirectory = "/images_uploaded";
//			String realPath = getServletContext().getRealPath(saveDirectory);
//			System.out.println("realPath = " + realPath);
			
//			File fsaveDirectory = new File(realPath);
//			if (!fsaveDirectory.exists())
//				 fsaveDirectory.mkdirs(); // 於 ContextPath 之下,自動建立目地目錄
			
//			Part part = request.getPart("upFiles");
//			System.out.println("part.getName() = " + part.getName());
			
//			String filename = part.getSubmittedFileName();
//			System.out.println("filename = " + filename);
			
//			if (filename!= null && filename.length()!=0 && part.getContentType()!=null) {
				
//				File f = new File(fsaveDirectory, filename);
//				System.out.println("File: " + f);
//				out.println("File: " + f);

				// 利用File物件,寫入目地目錄,上傳成功
//				part.write(f.toString());//f.toString是轉成字串
//				System.out.println("有無寫入目的");
				
//				InputStream in = part.getInputStream();
//				if(in.available()!=0) {
//					upFiles = new byte[in.available()];
//					in.read(upFiles);
//					in.close();
//				}else {
//					errorMsgs.add("商品照片: 請上傳照片");
//				}
//			}

			byte[] upFiles = null;
			InputStream in = request.getPart("upFiles").getInputStream();
			if(in.available()!=0) {
				upFiles = new byte[in.available()];
				in.read(upFiles);
				in.close();
			}else {
				ProductService productSvc = new ProductService();
				upFiles = productSvc.getOneProduct(productId).getProductPhoto();
//				errorMsgs.add("商品照片: 請上傳照片");
			}
	
			
			
			
			ProductCategoryVO productCategoryVO = new ProductCategoryVO();
			productCategoryVO.setProductCategotyId(productCategoryId);
			
			//修改商品資料
			ProductVO productVO = new ProductVO();
			
			productVO.setProductId(productId);
			productVO.setProductName(productName);
			productVO.setProductCategoryVOs(productCategoryVO);
			productVO.setProductDescribtion(productDescribtion);
			productVO.setProductPrice(productPrice);
			productVO.setProductStatus(productStatus);
			
			
//			//修改從屬的商品圖片物件，從service抓的
//			ProductPhotoVo productPhotoVO = new ProductPhotoVo();
//			productPhotoVO.setProductPhoto(upFiles);
//			
//			//set關聯的資料
//			productPhotoVO.setProductVO(productVO);
//			
//			//productVO放入productPhotoVos物件,讓productVO可以調productPhotoVos資訊
//			Set<ProductPhotoVo> productPhotoVos = new LinkedHashSet<>();
//			productPhotoVos.add(productPhotoVO);
//			//此商品修改時，跟著要一起修改的商品圖片
//			productVO.setProductPhoto(upFiles);
			
			
			if(!errorMsgs.isEmpty()) {
				request.setAttribute("productVO", productVO);
				RequestDispatcher failureView = request.getRequestDispatcher("/shop/update_product_input.jsp");
				failureView.forward(request, response);
				System.out.println("----------------------------------");		
				return;
			}
			
			//**************更改資料**************
			try {
				request.setAttribute("productVO", productVO);
				ProductService productSvc = new ProductService();
				productVO = productSvc.updateproduct(productId, productCategoryId, productDescribtion, productPrice, productName, productStatus, upFiles);
				System.out.println("確定有更改資料");
			}catch(Exception e){
				System.out.println("這裡有錯");
			}
			
			System.out.println("22222222222222222222222222222222222");			
			//*************修改完成轉交資料***********
			String url = "/shop/listOneProduct.jsp";
			RequestDispatcher successView = request.getRequestDispatcher(url); // 新增成功後轉交
			successView.forward(request, response);	
		}
		
		
		
		
		
		
//		
//		if("getOne_For_Update".equals(action)) {//來自listAllProduct.jsp的請求
//			List<String> errorMsgs = new LinkedList<String>();
//			
//			request.setAttribute("errorMsgs", errorMsgs);
//			
//			//******************接受請求參數******************
//			Integer productId = Integer.valueOf(request.getParameter("productId"));
//			
//			//******************開始查詢資料******************
//			ProductService productSvc = new ProductService();
//			ProductVO productVO = productSvc.getOneProduct(productId);
//			
//			//*****************查詢完成，準備繳交***************
//			request.setAttribute("productVO", productVO);
//			String url = "/shop/update_product_input.jsp";
//			RequestDispatcher successView = request.getRequestDispatcher(url);
//			successView.forward(request, response);
//		}
//		
//		
//		if("update".equals(action)) {
//			
//			List<String> errorMsgs = new LinkedList<>();
//			request.setAttribute("errorMsgs", errorMsgs);
//			
//			//*******************接受參數 -- 輸入格式錯誤處理**************
//			Integer productId = Integer.valueOf(request.getParameter("productId").trim());//還未看懂
////			System.out.println(productId);
//			
//			String productName = request.getParameter("productName");
////			System.out.println(productName);
//			String productReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_\\s)]{1,}$";//要注意
//			if(productName == null || productName.trim().length() == 0) {
//				errorMsgs.add("商品名稱: 請勿空白");
//			}else if(!productName.trim().matches(productReg)) {
//				errorMsgs.add("商品名稱: 只能是中、英文字母、數字和_");
//			}
//			
//			Integer productCategoryId = null;
//			
//			try {
//				productCategoryId = Integer.valueOf(request.getParameter("productCategoryId").trim());
//			}catch (NumberFormatException e) {
//				errorMsgs.add("商品類型編號請填數字");
//			}
//			
//			String productDescribtion = request.getParameter("productDescribtion");
////			String productDescribtionReg = ""
//			if(productDescribtion == null || productDescribtion.trim().length() == 0) {
//				errorMsgs.add("商品內容，請勿空白");
//			}
//			
//			Integer productPrice = null;
//			
//			try {
//				productPrice = Integer.valueOf(request.getParameter("productPrice").trim());
//			}catch(NumberFormatException e) {
//				errorMsgs.add("商品價格請填數字");
//			}
//			//要注意 不會
//			Boolean productStatus = null;
////			System.out.println(request.getParameter("productStatus").trim());
//			if(request.getParameter("productStatus").trim().equals("true")) {
//				productStatus = Boolean.valueOf(true);
////				productStatus = true;
////				System.out.println("true");
//			}else if(request.getParameter("productStatus").trim().equals("false")) {
////				productStatus = false;
////				System.out.println("false");
//				productStatus = Boolean.valueOf(false);
//			}
//			
////			try{
////				productStatus = Boolean.valueOf(request.getAttribute("productStatus"));
////			}
//			
//			ProductVO productVO = new ProductVO();
//			
//			productVO.setProductId(productId);
//			productVO.setProductName(productName);
////			productVO.setProductCategoryId(productCategoryId);
//			productVO.setProductDescribtion(productDescribtion);
//			productVO.setProductPrice(productPrice);
//			productVO.setProductStatus(productStatus);
//			
//			if(!errorMsgs.isEmpty()) {
//				request.setAttribute("productVO", productVO);
//				RequestDispatcher failureView = request.getRequestDispatcher("/shop/update_product_input.jsp");
//				failureView.forward(request, response);
//				return;
//			}
//			//******************開始修改資料*******************
//			ProductService productSvc = new ProductService();
//			productVO = productSvc.updateproduct(productId, productCategoryId, productDescribtion, productPrice, productName, productStatus);
//			//******************修改完成，準備繳交***************
//			request.setAttribute("productVO", productVO);
//			String url = "/shop/listOneProduct.jsp";
//			RequestDispatcher successView = request.getRequestDispatcher(url);
//			successView.forward(request, response);
//		}
		
		
	}

}
