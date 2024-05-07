package com.product.model;



import java.util.List;


import com.productcategory.model.ProductCategoryVO;


public class ProductService {
	private ProductDAO_interface dao;
	
	public ProductService() {
		dao = new ProductDAO();
	}
	
	public ProductVO addProduct(Integer productCategoryId, String productDescribtion, Integer productPrice, String productName, Boolean productStatus, byte[] upFiles) {
		
		//新增商品類別物件
		ProductCategoryVO productCategoryVO = new ProductCategoryVO();
		productCategoryVO.setProductCategotyId(productCategoryId);

		//新增商品資料
		ProductVO productVO = new ProductVO();		
		productVO.setProductCategoryVOs(productCategoryVO);
		productVO.setProductDescribtion(productDescribtion);
		productVO.setProductPrice(productPrice);
		productVO.setProductName(productName);
		productVO.setProductStatus(productStatus);
		productVO.setProductPhoto(upFiles);
		
//		//新增從屬的商品圖片物件
//		ProductPhotoVo productPhotoVO = new ProductPhotoVo();
//		productPhotoVO.setProductPhoto(upFiles);
		
		//set關聯的資料
//		productPhotoVO.setProductVO(productVO);
		
		//productVO放入productPhotoVos物件,讓productVO可以調productPhotoVos資訊
//		Set<ProductPhotoVo> productPhotoVos = new LinkedHashSet<>();
//		productPhotoVos.add(productPhotoVO);
//		productVO.setProductPhotoVos(productPhotoVos);
	

		dao.insert(productVO);
		
		return productVO;
	}
	
	public ProductVO updateproduct(Integer productId, Integer productCategoryId, String productDescribtion, Integer productPrice, String productName, Boolean productStatus, byte[] upFiles) {
			

		//更新商品資料
		ProductVO productVO = dao.findByPrimaryKey(productId);
		
		productVO.setProductId(productId);

		productVO.setProductDescribtion(productDescribtion);
		productVO.setProductPrice(productPrice);
		productVO.setProductName(productName);
		productVO.setProductStatus(productStatus);
		productVO.setProductPhoto(upFiles);

		
		//chatgpt
		 // 創建新的 ProductCategoryVO 對象，並設置 productCategoryId
	    ProductCategoryVO productCategoryVO = new ProductCategoryVO();
	    productCategoryVO.setProductCategotyId(productCategoryId);
	    
	    // 將新的 ProductCategoryVO 對象設置給 ProductVO
	    productVO.setProductCategoryVOs(productCategoryVO);
		
				
//		//更新從屬的商品圖片物件
//		ProductPhotoVo productPhotoVO = new ProductPhotoVo();
//		productPhotoVO.setProductPhoto(upFiles);
//		productPhotoVO.setProductPhotoId(88);
		
		//set關聯的資料
//		productPhotoVO.setProductVO(productVO);
		
		//productVO放入productPhotoVos物件,讓productVO可以調productPhotoVos資訊
//		Set<ProductPhotoVo> productPhotoVos = new LinkedHashSet<>();
//		productPhotoVos.add(productPhotoVO);
//		productVO.setProductPhotoVos(productPhotoVos);

		dao.update(productVO);
		
		return productVO;
	}
	
//	public ProductVO updateProduct()
	
	public ProductVO getOneProduct(Integer productId) {
		return dao.findByPrimaryKey(productId);
	}
	
	public List<ProductVO> getAll(){
		return dao.getAll();
	}
}
