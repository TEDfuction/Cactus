package com;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.activities_order.model.ActivityOrderService;

import com.cart.model.CartService;
import com.shoporder.model.ShopOrderRepository;

@SpringBootApplication
public class Testing implements CommandLineRunner {
    
	@Autowired
	ShopOrderRepository repository ;
	
	@Autowired
	CartService cartSvc;
	
	@Autowired
	ActivityOrderService activityOrderSvc;
	
	
	
	public static void main(String[] args) {
        SpringApplication.run(Testing.class);
    }

    @Override
    public void run(String...args) throws Exception {
    	
    	
    	
//    	//建立超級管理員權限
//    	for(int i = 1 ; i <= 5 ; i++) {
//    		AdminVO adminVO = new AdminVO();
//        	adminVO.setAdminId(1);
//    	
//    		AdminAuthorizationVO adminAuthorizationVO = new AdminAuthorizationVO();
//        	adminAuthorizationVO.setAdminAuthorizationId(i);
//        	
//        	AdminAuthVO adminAuthVO = new AdminAuthVO();
//        	adminAuthVO.setAdminVO(adminVO);
//        	adminAuthVO.setAdminAuthorizationVO(adminAuthorizationVO);
//        	
//        	adminAuthSvc.addAdminAuth(adminAuthVO);
//
//    	}
    	

    	
    	
//    	List<AdminVO> list = adminSvc.getAll();
//    	
//    	for(AdminVO adminVO : list) {
//    		System.out.println(adminVO.getAdminId());
//    		System.out.println(adminVO.getAdminAccount());
//    		System.out.println(adminVO.getAdminPassword());
//    		System.out.println(adminVO.getAdminName());
//    		System.out.println(adminVO.getAdminStatus());
//    	}
    	
//    	ActivityOrderVO activityOrderVO = activityOrderSvc.getOneOrder(1);
//    	System.out.println(activityOrderVO.getSessionVO()+"1111");
//    	System.out.println(activityOrderVO.getSessionVO().getItemVO()+"2222");
//    	System.out.println(activityOrderVO.getSessionVO().getItemVO().getActivityName()+"3333");
//    	
    	
//    	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
//		
//    	Date date = new Date();
//		String orderDateString = formatter.format(date); 
//    	SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//
//		System.out.println(formatter2.format(date));

//    	List<Cart> cartList = cartSvc.findAllItem(1);
//		
//		String allProductName = "";
//		int count = 0;
//		
//		for(Cart cart : cartList) {
//			count++;
//			if(count < cartList.size()) {
//				allProductName += cart.getProductName() + "#";
//			}else {
//				allProductName += cart.getProductName();
//			}
//		}
//		System.out.println(allProductName);

		
    	//● 新增
//		ShopOrderVO shopOrderVO = repository.findById(1).get();
//		Timestamp orderDate = shopOrderVO.getProductOrderDate();
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
//		String string = formatter.format(orderDate);
//		System.out.println(string);
		
		
		

//		MemberVO memberVO = new MemberVO();
//		ShopDiscountProjectVO shopDiscountProjectVO = new ShopDiscountProjectVO();
//		
//		shopOrderVO.setProductAmount(5000);
//		shopOrderVO.setPaymentMethod(1);
//		shopOrderVO.setShippingMethod(1);
//		shopOrderVO.setOrderStatus(1);
//		
//		memberVO.setMemberId(1);
//		shopDiscountProjectVO.setPromotionId(1);
//		
//		shopOrderVO.setMember(memberVO);
//		shopOrderVO.setShopDiscountProjectVO(shopDiscountProjectVO);
//		
//		
//		repository.save(shopOrderVO);
//		System.out.println("success");

		
		
		
		
		//● 刪除   //●●● --> EmployeeRepository.java 內自訂的刪除方法
//		repository.deleteById(2);
//		System.out.println("delete success");
		
		//● 刪除   //XXX --> Repository內建的刪除方法目前無法使用，因為有@ManyToOne
		//System.out.println("--------------------------------");
		//repository.deleteById(7001);      
		//System.out.println("--------------------------------");

		
		
		
		
    	//● 查詢
//    	List<ShopOrderVO> list = repository.findByMemberId(1);
//
//    	for (ShopOrderVO shopOrder : list) {
//			System.out.print(shopOrder.getShopOrderId() + ",");
//			System.out.print(shopOrder.getProductAmount() + ",");
//			System.out.print(shopOrder.getPaymentMethod() + ",");
//			System.out.print(shopOrder.getShippingMethod() + ",");
//			System.out.print(shopOrder.getOrderStatus() + ",");
//			System.out.println();
//			System.out.print(shopOrder.getMember().getMemberName() + ",");
//			System.out.print(shopOrder.getMember().getEmail() + ",");
//			System.out.print(shopOrder.getShopDiscountProjectVO().getPromotionTitle() + ",");
//			System.out.print(shopOrder.getShopDiscountProjectVO().getPromotionContent() + ",");
//			System.out.println("=============================");
//			System.out.println("=============================");
//		}
      
    	
		
		
		
//    	List<ShopOrderVO> list = repository.findAll();
//		for (ShopOrderVO shopOrder : list) {
//			System.out.print(shopOrder.getShopOrderId() + ",");
//			System.out.print(shopOrder.getProductAmount() + ",");
//			System.out.print(shopOrder.getPaymentMethod() + ",");
//			System.out.print(shopOrder.getShippingMethod() + ",");
//			System.out.print(shopOrder.getOrderStatus() + ",");
//			System.out.println();
//			System.out.print(shopOrder.getMember().getMemberName() + ",");
//			System.out.print(shopOrder.getMember().getEmail() + ",");
//			System.out.print(shopOrder.getShopDiscountProjectVO().getPromotionTitle() + ",");
//			System.out.print(shopOrder.getShopDiscountProjectVO().getPromotionContent() + ",");
//			System.out.println("=============================");
//			System.out.println("=============================");
//		}



    }
}
