package com.cart.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cart.model.Cart;
import com.cart.model.CartService;
import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.product.model.ProductService;
import com.product.model.ProductServiceImpl;
import com.product.model.ProductVO;
import com.shoporder.model.ShopOrderService;
import com.shoporder.model.ShopOrderVO;
import com.shoporderdetail.model.ShopOrderDetailService;
import com.shoporderdetail.model.ShopOrderDetailVO;

@Controller
//@Validated
@RequestMapping("/shopCart")
public class CartController {

	@Autowired
	CartService CartSvc;

	@Autowired
	ShopOrderService shopOrderSvc;

	@Autowired
	ShopOrderDetailService shopOrderDetailSvc;

	@Autowired
	ProductServiceImpl productSvc;

	@Autowired
	MemberService memSvc;

	private EntityManager entityManager;

	@ResponseBody
	@PostMapping("/addOneToCart")
	public String addOneToCart(@RequestBody Cart cart, HttpSession session) {
		
		//從Session中取得會員資料
		String email = (String) session.getAttribute("account");
		Integer memberId = memSvc.findByEmail(email).getMemberId();
		
		//找出對應商品
		ProductVO productVO = productSvc.findById(cart.getProductId());
		cart.setPrice(productVO.getProductPrice());
		cart.setProductName(productVO.getProductName());

		// 將一筆購物項目(訂單明細DTO)和會員ID放進購物車
		return CartSvc.addToCart(cart, memberId);
	}

	// 導入購物車頁面
	@GetMapping("/shopCart")
	public String shopCart() {
		return "front_end/product/shop_cart";
	}

	// 前端傳來要找該會員的購物車
	@ResponseBody
	@GetMapping("/shopCartByMember")
	public List<Cart> findShopCart(HttpSession session) {
		
		//從Session中取得會員資料
		String email = (String) session.getAttribute("account");
		Integer memberId = memSvc.findByEmail(email).getMemberId();
		
		return CartSvc.findAllItem(memberId);
	}

	@ResponseBody
	@PostMapping("/upDateCart")
	public String upDateCart(HttpSession session, @RequestBody Cart cart) {
		
		//從Session中取得會員資料
		String email = (String) session.getAttribute("account");
		Integer memberId = memSvc.findByEmail(email).getMemberId();
				
		if (memberId != null) {
			System.out.println("開始");
			//找出對應商品
			ProductVO productVO = productSvc.findById(cart.getProductId());
			cart.setPrice(productVO.getProductPrice());
			cart.setProductName(productVO.getProductName());

			CartSvc.updateOneItem(memberId, cart);
			System.out.println("結束");
		} else {
			return "沒找到Id";
		}
		return "修改成功";
	}

	@ResponseBody
	@PostMapping("/removeOneCartItem")
	public String removeOneCartItem(@RequestBody Map<String, Object> requestData, HttpSession session) {
		Integer productId = (Integer) requestData.get("productId");

		//從Session中取得會員資料
		String email = (String) session.getAttribute("account");
		Integer memberId = memSvc.findByEmail(email).getMemberId();
				
		if (memberId != null) {

			System.out.println("開始");
			CartSvc.removeOneItem(productId, memberId);
			System.out.println("結束");

		} else {
			return "沒找到Id";
		}

		return "移除成功";
	}

	@GetMapping("/CartToCheckout")
	public String cartToCheckout() {
		return "front_end/product/shop_checkout";
	}

	@Transactional
	@PostMapping("/checkoutOrder")
	public ResponseEntity<String> checkoutOrder(@RequestBody ShopOrderVO shopOrdeVO, HttpSession session) {
		
		//從Session中取得會員資料
		String email = (String) session.getAttribute("account");
		Integer memberId = memSvc.findByEmail(email).getMemberId();
		
		if (memberId != null) {
			MemberVO member = memSvc.findByPK(memberId);
			List<Cart> cart = CartSvc.findAllItem(memberId);

			//????
			shopOrdeVO.setMember(member);
			shopOrdeVO.setOrderStatus(1);

			
			//新增訂單明細資料
			Set<ShopOrderDetailVO> orderDetailSet = shopOrdeVO.getShopOrderDetailVO();
			
			if (cart != null) {
				for (Cart item : cart) {
					ShopOrderDetailVO shopOrderDetailVO = new ShopOrderDetailVO();

					ProductVO productVO = entityManager.merge(productSvc.findById(item.getProductId()));
					shopOrderDetailVO.setShopOrder(shopOrdeVO);
					shopOrderDetailVO.setProduct(productVO);
					shopOrderDetailVO.setOrderQuantity(item.getQuantity());
					shopOrderDetailVO.setProductAmount(item.getPrice());

					orderDetailSet.add(shopOrderDetailVO);
				}
			} else {
				return new ResponseEntity<>("訂單處理失敗", HttpStatus.INTERNAL_SERVER_ERROR);
			}

			shopOrderSvc.addOrder(shopOrdeVO);
			CartSvc.cleanAllCart(memberId);
			return new ResponseEntity<>("訂單處理成功", HttpStatus.OK);
		}
		return new ResponseEntity<>("訂單處理失敗", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ResponseBody
	@GetMapping("/cleanShopCart")
	public String cleanShopCart(HttpSession session) {
		
		//從Session中取得會員資料
		String email = (String) session.getAttribute("account");
		Integer memberId = memSvc.findByEmail(email).getMemberId();	
		
		if (memberId != null) {
			CartSvc.cleanAllCart(memberId);
		}
		return "/product/shopAllProduct";
	}
}
