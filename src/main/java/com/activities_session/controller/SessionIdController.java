package com.activities_session.controller;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Digits;

import com.session_time_period.model.Time_PeriodVO;
import com.session_time_period.model.Time_PeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.activities_item.model.ItemVO;
import com.activities_item.model.ItemService;
import com.activities_session.model.SessionVO;
import com.activities_session.model.SessionService;

@Controller
@Validated
@RequestMapping("/session")
public class SessionIdController {
	
	@Autowired
	SessionService sessionService;
	
	@Autowired
	ItemService itemService;

	@Autowired
	Time_PeriodService time_PeriodService;
	
	/*
	 * This method will be called on select_session.html form submission, handling POST
	 * request It also validates the user input
	 */
	@PostMapping("getOne_For_Display")
	public String getOne_For_Display(
			/***************************1.接收請求參數 - 輸入格式的錯誤處理*************************/
			@Digits(integer = 5, fraction = 0, message = "活動場次編號：請填寫數字")
			@RequestParam("activitySessionId") String activitySessionId, 
			ModelMap model) {
		/***************************2.開始查詢資料*********************************************/
		SessionVO sessionVO = sessionService.getOneSession(Integer.valueOf(activitySessionId));
		
		List<SessionVO> list = sessionService.getAll();
		model.addAttribute("sessionListData", list);
		model.addAttribute("itemVO", new ItemVO());
		List<ItemVO> list2 = itemService.getAll();
		model.addAttribute("itemListData2", list2);
		model.addAttribute("time_PeriodVO", new Time_PeriodVO());
		List<Time_PeriodVO> list3 = time_PeriodService.getAll();
		model.addAttribute("timePeriodListData", list3);


		Set<Integer> timePeriodIdsForSession = sessionService.getTimePeriodId(Integer.valueOf(activitySessionId));
		model.addAttribute("timePeriodIdsForSession", timePeriodIdsForSession);
		Set<Time> timePeriodsForSession = sessionService.getTimePeriods(Integer.valueOf(activitySessionId));
		List<Time> sortedTimePeriods = new ArrayList<>(timePeriodsForSession); //時段重新排序
		Collections.sort(sortedTimePeriods);

		model.addAttribute("sortedTimePeriods", sortedTimePeriods);

		if(sessionVO == null) {
			model.addAttribute("errorMessage", "查無資料");
			return "back_end/session/select_session";
		}
		
		/***************************3.查詢完成,準備轉交(Send the Success view)*****************/
		model.addAttribute("sessionVO", sessionVO);
		model.addAttribute("getOne_For_Display", "true");
		
		return "back_end/session/listOneSession"; // 查詢完成後轉交select_session.html由其第158行insert listOneEmp.html內的th:fragment="listOneEmp-div
	}

	@PostMapping("getOne_For_Display2")
	public String getOne_For_Display2(
			/***************************1.接收請求參數 - 輸入格式的錯誤處理*************************/
			@RequestParam("activitySessionId") String activitySessionId,
			ModelMap model) {
		/***************************2.開始查詢資料*********************************************/
		SessionVO sessionVO = sessionService.getOneSession(Integer.valueOf(activitySessionId));

		List<SessionVO> list = sessionService.getAll();
		model.addAttribute("sessionListData", list);
		model.addAttribute("itemVO", new ItemVO());
		List<ItemVO> list2 = itemService.getAll();
		model.addAttribute("itemListData2", list2);
		model.addAttribute("time_PeriodVO", new Time_PeriodVO());
		List<Time_PeriodVO> list3 = time_PeriodService.getAll();
		model.addAttribute("timePeriodListData", list3);

		Set<Integer> timePeriodIdsForSession = sessionService.getTimePeriodId(Integer.valueOf(activitySessionId));
		model.addAttribute("timePeriodIdsForSession", timePeriodIdsForSession);
		Set<Time> timePeriodsForSession = sessionService.getTimePeriods(Integer.valueOf(activitySessionId));
		model.addAttribute("timePeriodsForSession", timePeriodsForSession);

		if(sessionVO == null) {
			model.addAttribute("errorMessage", "查無資料");
			return "back_end/session/select_session";
		}



		/***************************3.查詢完成,準備轉交(Send the Success view)*****************/
		model.addAttribute("sessionVO", sessionVO);
		model.addAttribute("getOne_For_Display", "true");

		return "back_end/session/listAllSession"; // 查詢完成後轉交select_session.html由其第158行insert listOneEmp.html內的th:fragment="listOneEmp-div
	}
	
	
	@ExceptionHandler(value = {ConstraintViolationException.class})
	public ModelAndView handleError(HttpServletRequest req, ConstraintViolationException e , Model model) {
		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		StringBuilder strBuilder = new StringBuilder();
		for(ConstraintViolation<?> violation : violations) {
			strBuilder.append(violation.getMessage() + "<br>");
			
		}

		model.addAttribute("sessionVO", new SessionVO());
		List<SessionVO> list = sessionService.getAll();
		model.addAttribute("sessionListData", list);     // for select_page.html 第97 109行用
		model.addAttribute("itemVO", new ItemVO());
		List<ItemVO> list2 = itemService.getAll();
		model.addAttribute("itemListData2", list2);
		model.addAttribute("time_PeriodVO", new Time_PeriodVO());
		List<Time_PeriodVO> list3 = time_PeriodService.getAll();
		model.addAttribute("timePeriodListData", list3);

		String message = strBuilder.toString();
	    return new ModelAndView("back_end/session/select_session", "errorMessage", "請修正以下錯誤:<br>"+message);
	}

}
