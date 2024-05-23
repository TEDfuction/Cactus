package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.boot.web.servlet.ServletComponentScan;

//後台過濾器

@WebFilter(filterName = "adminFilter" , 
		   urlPatterns = { 
//				   "/attendees/addAttendees",
//				   "/attendees/insert",
//				   "/attendees/delete",
//				   "/attendees/getOne_For_Update",
//				   "/attendees/update",
//				   "/attendees/getAttendeesName",
//				   "/attendees/getAttendeesEmail",
//				   "/attendees/getOne_For_Display",
//
//				   "/category/getOne_For_Display",
//				   "/category/addCategory",
//				   "/category/insert",
//				   "/category/delete",
//				   "/category/getOne_For_Update",
//				   "/category/update",
//				   "/category/listCategory_ByCompositeQuery",
//				   "/category/listAllCategory",
//
//				   "/item/addItem",
//				   "/item/insert",
//				   "/item/delete",
//				   "/item/getOne_For_Update",
//				   "/item/update",
//				   "/item/listItem_ByCompositeQuery",
//				   "/item/getOne_For_Display",
//				   "/item/listAllItem",
//
//				   "/activityOrder/addOrder",
//				   "/activityOrder/insert",
//				   "/activityOrder/delete",
//				   "/activityOrder/getOne_For_Update",
//				   "/activityOrder/update",
//				   "/activityOrder/listAllActivityOrder",
//				   "/activityOrder/getOrderTimeBetween",
//				   "/activityOrder/getOne_For_Display",
//				   "/activityOrder/getOne_For_Display2",
//				   "/activityOrder/listAllOrder",
//				   "/promotion/listAllPromotion",
//
//				   "/activity/addPhoto",
//				   "/activity/insert",
//				   //"/activity/insertOrder",
//				   "/activity/getOne_For_Update",
//				   "/activity/update",
//				   "/activity/delete",
//				   "/activity/getOne_For_Display",
//
//				   "/promotion/addPromotion",
//				   "/promotion/insert",
//				   "/promotion/delete",
//				   "/promotion/getOne_For_Update",
//				   "/promotion/update",
//				   "/promotion/getPromotionTitle",
//				   "/promotion/getStartedAndEnd",
//				   "/promotion/getOne_For_Display",
//
//				   "/session/getOne_For_Display",
//				   "/session/getOne_For_Display2",
//				   "/session/addSession",
//				   "/session/insert",
//				   "/session/delete",
//				   "/session/getOne_For_Update",
//				   "/session/update",
//				   "/session/sessionBetweenDates",
//				   "/session/listAllSession",
//
				   "/admin/backendAdminIndex"
//				   "/admin/adminUpdate",
//
//				   "/member/memberSearch",
//				   "/member/findByName",
//
//				   "/notification/showAllMessage",
//				   "/notification/sendMessage",
//
//				   "/roomorder/listAllRoomOrder",
//
//				   "/roomtype/listAllRoomType",
//				   "/roomtype/updateRTS",
//				   "/roomtype/addRoomType",
//				   "/roomtype/getOneRoomType",
//				   "/roomtype/updateRoomTypeForm",
//
//				   "/timePeriod/getOne_For_Display2",
//				   "/timePeriod/addTimePeriod",
//				   "/timePeriod/insert",
//
//				   "/shopOrder/shopOrderSearch",
//				   "/shopOrder/showAllShopOrder",
//				   "/shopOrder/showOneShopOrder",
//				   "/shopOrder/orderDetailFromShowAll",
//				   "/shopOrder/orderDetailFromShowOne",
//				   
//				   "/product/historyProduct",
//				   "/product/select_page",
//				   "/product/listAllProduct1",
//				   "/product/addProduct",
//				   "/product/insert",
//				   "/product/getOne_For_Update",
//				   "/product/update",
//				   "/product/listProducts_ByCompositeQuery",
//				   "/product/getOne_For_Display"
		   		   		})
public class AdminFilter implements Filter {

	private FilterConfig config;

	public void init(FilterConfig config) {
		this.config = config;
	}

	public void destroy() {
		config = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws ServletException, IOException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		// 取得 session
		HttpSession session = req.getSession();
		
		// 判斷是否登入過
		Object account = session.getAttribute("adminVO");
//		System.out.println(req.getRequestURI());
		
		//設定重導路徑
		if (account == null) {
			session.setAttribute("admin_location", req.getRequestURI());
//			System.out.println(req.getRequestURI());
			res.sendRedirect(req.getContextPath() + "/admin/adminLogin");
			return;
		} else {
			chain.doFilter(request, response);
		}
	}
}