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

//登入過濾器

@WebFilter(filterName = "loginFilter" , 
urlPatterns = { 
		   		   "/member/memberOnlyWeb" , 
		   		   "/member/checkNotification",
		   		   "/member/checkActivityOrderDetail",
		   		   "/member/checkRoomOrderDetail",
		   		   "/member/checkProductOrderDetail"
		   		   })
public class LoginFilter implements Filter {

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
		Object account = session.getAttribute("account");
//		System.out.println(req.getRequestURI());
		
		//設定重導路徑
		if (account == null) {
			session.setAttribute("location", req.getRequestURI());
			res.sendRedirect(req.getContextPath() + "/member/memberLogin");
			return;
		} else {
			chain.doFilter(request, response);
		}
	}
}