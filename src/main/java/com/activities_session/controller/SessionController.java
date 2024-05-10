package com.activities_session.controller;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.activities_item.model.ItemVO;
import com.session_time_period.model.Time_PeriodVO;
import com.session_time_period.model.Time_PeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import com.activities_item.model.ItemService;
import com.activities_session.model.SessionVO;
import com.activities_session.model.SessionService;

@Controller
@RequestMapping("/session")
public class SessionController {
	
	@Autowired
	SessionService sessionService;
	
	@Autowired
	ItemService itemService;

	@Autowired
	Time_PeriodService time_PeriodService;


	
	/*
	 * 如要新增時
	 * This method will serve as addSession.html handler.
	 */
	@GetMapping("addSession")
	public String addSession(ModelMap model) {
		
		SessionVO sessionVO = new SessionVO();
		model.addAttribute("sessionVO", sessionVO);
		System.out.println("轉交請求");
		return "back_end/session/addSession";
	}

	/*
	 * This method will be called on addSession.html form submission, handling POST request It also validates the user input
	 * 新增
	 * BindingResult:配合@Valid一起使用，用于接收bean中的校验信息
	 */
	@PostMapping("insert")
	public String insert( @Valid SessionVO sessionVO, BindingResult result, ModelMap model
//						  @RequestParam("enrollStarted") String enrollStarted,
//						  @RequestParam("enrollEnd") String enrollEnd
						 ) {

/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//		 先去除BindingResult中enrollStarted、enrollEnd的FieldError紀錄
//		result = removeFieldError(sessionVO, result, "enrollStarted");
//		result = removeFieldError(sessionVO, result, "enrollEnd");
//
//		System.out.println("=======================");
//		System.out.println(enrollStarted);
//		System.out.println("=======================");



		/*************************** 2.開始新增資料 *****************************************/
		// 將前端傳遞的日期時間字符串轉換為 Timestamp 類型
//		Timestamp enrollStartedTimestamp = null;
//		Timestamp enrollEndTimestamp = null;
//		try {
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
//
//			java.util.Date parsedDate = dateFormat.parse(enrollStarted);
//			java.util.Date parsedDate2 = dateFormat.parse(enrollEnd);
////			System.out.println(enrollStarted);
//
//			enrollStartedTimestamp = new Timestamp(parsedDate.getTime());
//			enrollEndTimestamp = new Timestamp(parsedDate2.getTime());
////			System.out.println("--------------------------------");
////			System.out.println(enrollStartedTimestamp);
////			System.out.println(enrollEndTimestamp);
////			System.out.println("--------------------------------");
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			// 處理轉換異常
//		}
//		if(result.hasErrors()) {
//			System.out.println("資料不全");
//			return "back_end/session/update_session_input";
//		}
		// 將轉換後的 Timestamp set回 sessionVO 对象
//		sessionVO.setEnrollStarted(enrollStartedTimestamp);
//		sessionVO.setEnrollEnd(enrollEndTimestamp);


		if(result.hasErrors()) {
			System.out.println("資料有誤");
			return "back_end/session/addSession";
		}

		sessionService.addSession(sessionVO);
		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
		List<SessionVO> list = sessionService.getAll();
		model.addAttribute("sessionListData", list);
		model.addAttribute("success", "- (新增成功)");
		return "redirect:/session/listAllSession"; // 新增成功後重導至IndexController_inSpringBoot.java的第58行@GetMapping("/emp/listAllEmp")

	}
	

	
	/*
	 * This method will be called on listAllSession.html form submission, handling POST request
	 * 刪除
	 */
	@PostMapping("delete")
	public String delete(@RequestParam("activitySessionId") String activitySessionId, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始刪除資料 *****************************************/
		sessionService.deleteSession(Integer.valueOf(activitySessionId));
		/*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
		List<SessionVO> list = sessionService.getAll();
		model.addAttribute("sessionListData", list);
		model.addAttribute("success", "- (刪除成功)");
		return "back_end/session/listAllSession"; // 刪除完成後轉交listAllSession.html
	}
	
	/*
	 * 如要修改時
	 * This method will be called on listAllSession.html form submission, handling POST request
	 */
	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("activitySessionId") String activitySessionId, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/
		SessionVO sessionVO = sessionService.getOneSession(Integer.valueOf(activitySessionId));
		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("sessionVO", sessionVO);
		System.out.println("修改開始");
		return "back_end/session/update_session_input"; // 查詢完成後轉交update_session_input.html
		
	}
	
	@PostMapping("update")
	public String update(@Valid SessionVO sessionVO, BindingResult result, ModelMap model,
						 @RequestParam("enrollStarted") String enrollStarted,
						 @RequestParam("enrollEnd") String enrollEnd,
						 @RequestParam("activityDate")String activityDate
//						 @RequestParam("timePeriod1")String  timePeriod1,
//						 @RequestParam("timePeriod2")String  timePeriod2,
//						 @RequestParam("timePeriod3")String  timePeriod3,
//						 @RequestParam("timePeriod4")String  timePeriod4
	) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 先去除BindingResult中enrollStarted、enrollEnd的FieldError紀錄
		result = removeFieldError(sessionVO, result, "enrollStarted");
		result = removeFieldError(sessionVO, result, "enrollEnd");
		result = removeFieldError(sessionVO, result, "activityDate");
//		result = removeFieldError(sessionVO, result, "timePeriod1");
//		result = removeFieldError(sessionVO, result, "timePeriod2");
//		result = removeFieldError(sessionVO, result, "timePeriod3");
//		result = removeFieldError(sessionVO, result, "timePeriod4");

//		System.out.println("=======================");
//		System.out.println(enrollStarted);
//		System.out.println("=======================");

		if(result.hasErrors()) {
			System.out.println("資料不全");
			return "back_end/session/update_session_input";
		}


		/*************************** 2.開始修改資料 *****************************************/
		// 將前端傳遞的日期時間字符串轉換為 Timestamp 類型
		Timestamp enrollStartedTimestamp = null;
		Timestamp enrollEndTimestamp = null;
		java.sql.Date activityDateToDate = null;

		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
			Date parsedDate3 = dateFormat2.parse(activityDate);

//			new java.sql.Date(parsedDate3.getTime());

			java.util.Date parsedDate = dateFormat.parse(enrollStarted);
			java.util.Date parsedDate2 = dateFormat.parse(enrollEnd);
//			System.out.println(enrollStarted);

			enrollStartedTimestamp = new Timestamp(parsedDate.getTime());
			enrollEndTimestamp = new Timestamp(parsedDate2.getTime());
			activityDateToDate = new java.sql.Date(parsedDate3.getTime());
//			System.out.println("--------------------------------");
//			System.out.println(enrollStartedTimestamp);
//			System.out.println(enrollEndTimestamp);
//			System.out.println("--------------------------------");

		} catch (Exception e) {
			e.printStackTrace();
			// 處理轉換異常
		}
		// 將轉換後的 Timestamp set回 sessionVO 对象
		sessionVO.setEnrollStarted(enrollStartedTimestamp);
		sessionVO.setEnrollEnd(enrollEndTimestamp);
		sessionVO.setActivityDate(activityDateToDate);



		//轉換為Time類型
//		Time timePeriod1Time = null;
//		Time timePeriod2Time = null;
//		Time timePeriod3Time = null;
//		Time timePeriod4Time = null;
//
//		SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm");
//		java.util.Date date1 = dateFormat2.parse(timePeriod1);
//		timePeriod1Time = new Time(date1.getTime());
//		if(timePeriod2 == null || timePeriod2.isEmpty()){
//
//		}else {
//			java.util.Date date2 = dateFormat.parse(timePeriod2);
//			timePeriod2Time = new Time(date2.getTime());
//		}
//
//		if(timePeriod3 == null || timePeriod3.isEmpty()){
//
//		}else {
//			java.util.Date date3 = dateFormat.parse(timePeriod3);
//			timePeriod3Time = new Time(date3.getTime());
//		}
//
//		if(timePeriod4 == null || timePeriod4.isEmpty()){
//
//		}else {
//			java.util.Date date4 = dateFormat.parse(timePeriod4);
//			timePeriod4Time = new Time(date4.getTime());
//		}
//
//
//		Time_PeriodVO time_periodVO = new Time_PeriodVO();
//		time_periodVO.setTimePeriod1(timePeriod1Time);
//		time_periodVO.setTimePeriod2(timePeriod2Time);
//		time_periodVO.setTimePeriod3(timePeriod3Time);
//		time_periodVO.setTimePeriod4(timePeriod4Time);
//		sessionVO.setTime_PeriodVO(time_periodVO);
		sessionService.updateSession(sessionVO);
		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		sessionVO = sessionService.getOneSession(Integer.valueOf(sessionVO.getActivitySessionId()));
		model.addAttribute("sessionVO", sessionVO);
		return "back_end/session/listOneSession";
		
	}
	
	/*
     * This method will be called on select_session.html form submission, handling POST request
     *複合查詢
     */
//	@PostMapping("listSession_ByCompositeQuery")
//	public String listAllSession(HttpServletRequest req, Model model) {
//		Map<String, String[]> map = req.getParameterMap();//複合請求
//		List<SessionVO> list = sessionService.getAll(map); //搜尋的範圍列出
//		model.addAttribute("sessionListData", list);
//		return "back_end/session/listAllSession";
//	}

	@GetMapping("sessionBetweenDates")
	public String getSessionBetweenDates(
			@RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
			@RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd") Date end,
			Model model) {

		model.addAttribute("start", start);
		model.addAttribute("end", end);

		List<SessionVO> dateBetween = sessionService.getActivityDateBetween(start, end);
		model.addAttribute("sessionListData", dateBetween);
		return "back_end/session/listAllSession";

	}



	/*
	 * Method used to populate the List Data in view. 如 :
	 * <form:select path="deptno" id="deptno" items="${deptListData}" itemValue="deptno" itemLabel="dname" />
	 */
	@ModelAttribute("itemListData2")
	protected List<ItemVO> referenceListData(){
		List<ItemVO> list = itemService.getAll();
		return list;
	}

	@ModelAttribute("timePeriodListData2")
	protected List<Time_PeriodVO> referenceListData2(){
		List<Time_PeriodVO> list = time_PeriodService.getAll();
		return list;
	}

	// 去除BindingResult中某個欄位的FieldError紀錄
	public BindingResult removeFieldError(SessionVO sessionVO, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname))
				.collect(Collectors.toList());
		result = new BeanPropertyBindingResult(sessionVO, "sessionVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}

//	public BindingResult removeFieldError2(SessionVO sessionVO, BindingResult result2, String removedFieldname) {
//		List<FieldError> errorsListToKeep2 = result2.getFieldErrors().stream()
//				.filter(fieldname -> !fieldname.getField().equals(removedFieldname))
//				.collect(Collectors.toList());
//		result2 = new BeanPropertyBindingResult(sessionVO, "sessionVO");
//		for (FieldError fieldError2 : errorsListToKeep2) {
//			result2.addError(fieldError2);
//		}
//		return result2;
//	}




	
}
