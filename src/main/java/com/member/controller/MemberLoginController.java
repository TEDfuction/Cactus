package com.member.controller;

import java.io.UnsupportedEncodingException;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.member.model.MemberService;
import com.member.model.MemberVO;

@Controller
@Validated
@RequestMapping("member")
public class MemberLoginController {

	@Autowired
	MemberService memSvc;

	@Autowired
	private JavaMailSender mailSender;

	private String mailToken;
	
	
	

	@GetMapping("/memberLogin")
	public String memberLogin(Model model) {
		MemberVO memberVO = new MemberVO();
		model.addAttribute("memberVO", memberVO);

		return "/front_end/member/memberLogin";
	}

	//練習方法級別驗證
	@PostMapping("/memberLogin")
	public String memberLogin(HttpServletRequest req, HttpServletResponse res,

			@NotEmpty(message = "電子信箱:請勿空白") @RequestParam("email") String email,

			@NotEmpty(message = "會員密碼:請勿空白") @RequestParam("password") String password, 
			
			ModelMap model) {

		MemberVO memberVO = memSvc.findByEmail(email);
		
		//查無此使用者帳號(信箱)
		if (memberVO == null) {
			model.addAttribute("status", "wrong");

			return "/front_end/member/memberLogin";
		}
		
		//帳號密碼輸入錯誤
		if (!memberVO.getEmail().equals(email) || !memberVO.getPassword().equals(password)) {
			model.addAttribute("status", "failed");

			return "/front_end/member/memberLogin";
		}

		//驗證通過,將資訊存至session給過濾器做驗證
		HttpSession session = req.getSession();
		model.addAttribute("memberVO", memberVO);
		session.setAttribute("account", email);
		
		//檢查有無來源地址,若沒有就到會員專區頁面
		try {
			String location = (String) session.getAttribute("location");
			if (location != null) {
				return "redirect:" + location;
//				res.sendRedirect(location);
//				session.removeAttribute("location"); // 看看有無來源網頁 (-->如有來源網頁:則重導至來源網頁)
//				return;
			}
		} catch (Exception ignored) {
			ignored.printStackTrace();
		}

		return "/front_end/member/memberOnlyWeb";
	}

	
	
	
	


	
	
	
	
	
	
	
	@GetMapping("/forgetPassword")
	public String forgetPassword(Model model) {
		return "front_end/member/forgetPassword";
	}

	@PostMapping("/forgetMail")
	public String forgetMail(
			HttpServletRequest req,
			@NotEmpty(message = "電子信箱:請勿空白") 
			@RequestParam("email") String email, 
			Model model)
			throws MessagingException, UnsupportedEncodingException {

		// 錯誤處理
		MemberVO memberVO = memSvc.findByEmail(email);
		if (memberVO == null) {
			model.addAttribute("status", "failed");
			return "front_end/member/forgetPassword";
		}

		// 驗證碼產生--小吳Redis範例

		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= 6; i++) {
			int condition = (int) (Math.random() * 3) + 1;
			switch (condition) {
			case 1:
				char c1 = (char) ((int) (Math.random() * 26) + 65);
				sb.append(c1);
				break;
			case 2:
				char c2 = (char) ((int) (Math.random() * 26) + 97);
				sb.append(c2);
				break;
			case 3:
				sb.append((int) (Math.random() * 10));
			}
		}

		mailToken = sb.toString();

		
		// 信件寄送
		
		MimeMessage msg = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg);

		helper.setTo(email);
		helper.setFrom("david70306@yahoo.com.tw", "CactusHotel");
		helper.setSubject("忘記密碼驗證信");

		String text = "<p>親愛的" + memberVO.getMemberName() + "貴賓您好!</p>" + "<p>此為您的驗證碼「<span style='color:red'>"
				+ mailToken + "</span>」</p>" + "<p>請將此組密碼輸入後重新修改您的密碼!</p>";

		helper.setText(text, true);

		mailSender.send(msg);
		
		
		HttpSession session = req.getSession();
		session.setAttribute("memberVO", memberVO);
		model.addAttribute("status", "success");
		return "front_end/member/forgetTokenConfirm";
	}

	@PostMapping("/forgetTokenConfirm")
	public String forgetTokenConfirm(@RequestParam("token") String inputToken, ModelMap model) {

		//錯誤處理
		if (!inputToken.equals(mailToken)) {
			model.addAttribute("status", "failed");
			return "front_end/member/forgetTokenConfirm";
		} else {
			model.addAttribute("status", "success");
			return "front_end/member/changePassword";
		}

	}
	
	@PostMapping("/changePassword")
	public String changePassword (
			HttpServletRequest req,
			@RequestParam("password") String password,
			@RequestParam("rePassword") String rePassword,
			ModelMap model
			) {
		
		if(!password.equals(rePassword)) {
			model.addAttribute("status", "failed");
			return "front_end/member/changePassword";
		}
		
			//密碼更改完成
			HttpSession session = req.getSession();
			MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
			memSvc.updatePassword(password, memberVO.getMemberId());
			
			model.addAttribute("memberVO", memberVO);
			model.addAttribute("status", "changeFinish");
			return "front_end/member/memberLogin";
		
	}
	

	
	
	
	
	
	
	
	
	@GetMapping("/memberLogout")
	public String memberLogout(HttpSession session, ModelMap model) {
		if (session != null) {
			//移除session上之屬性,使對應網頁重新進入過濾器控制範圍
			session.removeAttribute("account");
		}

		MemberVO memberVO = new MemberVO();
		model.addAttribute("memberVO", memberVO);

		return "front_end/member/memberLogin";
	}
	
	
	
	
	//方法級別驗證使用--捕捉ConstraintViolationException做處理
	@ExceptionHandler(value = { ConstraintViolationException.class })
	public ModelAndView handleError(
			HttpServletRequest req,
			ConstraintViolationException e,
			Model model) {
		
		//將錯誤訊息收集
	    Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
	    
	    StringBuilder strBuilder = new StringBuilder();
	    
	    for (ConstraintViolation<?> errorSet : violations ) {
	    	//把錯誤訊息串接 
	    	strBuilder.append(errorSet.getMessage() + "<br>");
	    }
	    
		String message = strBuilder.toString();
	    return new ModelAndView("front_end/member/memberLogin", "errorMessage", "請修正以下錯誤:<br>"+message);
	}
}
