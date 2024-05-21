package com.activities_session.controller;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.TimeConvers.StringToSqlTimeConverter;
import com.TimeConvers.StringToSqlTimestampConverter;
import com.activities_item.model.ItemVO;
import com.activities_order.model.ActivityOrderService;
import com.activities_order.model.ActivityOrderVO;
import com.session_time_period.model.TimePeriodDTO;
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
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import com.activities_item.model.ItemService;
import com.activities_session.model.SessionVO;
import com.activities_session.model.SessionService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/session")
public class SessionController {

    @Autowired
    SessionService sessionService;

    @Autowired
    ItemService itemService;

    @Autowired
    Time_PeriodService time_PeriodService;

    @Autowired
    ActivityOrderService activityOrderService;

    @Autowired
    private StringToSqlTimeConverter stringToSqlTimeConverter;


    /*
     * 如要新增時
     * This method will serve as addSession.html handler.
     */
    @GetMapping("addSession")
    public String addSession(ModelMap model) {

        SessionVO sessionVO = new SessionVO();
        model.addAttribute("sessionVO", sessionVO);
        Time_PeriodVO time_PeriodVO = new Time_PeriodVO();
        model.addAttribute("time_PeriodVO", time_PeriodVO);
        System.out.println("轉交請求");
        return "back_end/session/addSession";
    }

    /*
     * This method will be called on addSession.html form submission, handling POST request It also validates the user input
     * 新增
     * BindingResult:配合@Valid一起使用，用于接收bean中的校验信息
     */
    @PostMapping("insert")
    public String insert(@Valid SessionVO sessionVO, BindingResult result, ModelMap model,
                         RedirectAttributes redirectAttributes,
                         @RequestParam("timePeriod1") String timePeriod1,
                         @RequestParam("timePeriod2") String timePeriod2,
                         @RequestParam("timePeriod3") String timePeriod3,
                         @RequestParam("timePeriod4") String timePeriod4

    ) throws ParseException {

/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
        if (result.hasErrors()) {
            //驗證方式： 若屬性存在一個以上的錯誤驗證註解，為避免在驗證皆未通過，使用迴圈輸出完整的錯誤訊息
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (int i = 0, length = fieldErrors.size(); i < length; i++) {
                //依索引值放入個別錯誤
                FieldError field = fieldErrors.get(i);
                model.addAttribute(i + "-" + field.getField(), field.getDefaultMessage()); //出錯的名稱&訊息放入。
                model.addAttribute("sessionVO", sessionVO);
            }
            System.out.println("資料有誤");
            return "back_end/session/addSession";
        }

        /*************************** 2.開始新增資料 *****************************************/

        //先做場次新增
        SessionVO sessionNew = sessionService.addSession(sessionVO);
        System.out.println("場次新增成功");

        //成功後再新增場次時段，有四個時段要放進去
        List<String> tpsVO = new ArrayList<>();
        tpsVO.add(timePeriod1);
        tpsVO.add(timePeriod2);
        tpsVO.add(timePeriod3);
        tpsVO.add(timePeriod4);

        for (String timePeriods : tpsVO) {
            if (timePeriods == null || timePeriods.isEmpty()) {

            } else {
                Time time = stringToSqlTimeConverter.convert(timePeriods);
                Time_PeriodVO tpVO = new Time_PeriodVO();
                tpVO.setTimePeriod(time);
                tpVO.setSessionVO(sessionNew);//FK 場次新增完的結果
                time_PeriodService.addTimePeriod(tpVO);
            }
        }

        /*************************** 3.新增完成,準備轉交(Send the Success view) **************/
        List<SessionVO> list = sessionService.getAll();

        model.addAttribute("sessionListData", list);
        redirectAttributes.addFlashAttribute("success", "新增成功～");
        return "redirect:/session/listAllSession";

    }

    /*
     * This method will be called on listAllSession.html form submission, handling POST request
     * 刪除
     */
    @PostMapping("delete")
    public String delete(@RequestParam("activitySessionId") String activitySessionId,
                         RedirectAttributes redirectAttributes) {
        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
        /*************************** 2.開始刪除資料 *****************************************/
        sessionService.deleteSession(Integer.valueOf(activitySessionId));
        /*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
//        List<SessionVO> list = sessionService.getAll();
//        model.addAttribute("sessionListData", list);
        redirectAttributes.addFlashAttribute("success", "刪除成功");
        return "redirect:/session/listAllSession"; // 刪除完成後轉交listAllSession.html
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
        List<Time_PeriodVO> timePeriodVOList = time_PeriodService.getActivitySessionId(Integer.valueOf(activitySessionId));
        System.out.println(timePeriodVOList);

        // 将List<Time_PeriodVO>轉換為Set<Time_PeriodVO>
//        Set<Time_PeriodVO> timePeriodSet = new HashSet<>(timePeriodVOList);
//
//        sessionVO.setTime_periodVO(timePeriodSet);
        /*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
        model.addAttribute("sessionVO", sessionVO);
        model.addAttribute("timePeriods", timePeriodVOList);
        System.out.println("修改開始");
        return "back_end/session/update_session_input"; // 查詢完成後轉交update_session_input.html

    }

    @PostMapping("update")
    public String update(@Valid SessionVO sessionVO, BindingResult result, ModelMap model,
                         @RequestParam("timePeriod1") String timePeriod1,
                         @RequestParam("timePeriod2") String timePeriod2,
                         @RequestParam("timePeriod3") String timePeriod3,
                         @RequestParam("timePeriod4") String timePeriod4
    ) {
        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/

        if (result.hasErrors()) {
            // 驗證方式： 若屬性存在一個以上的錯誤驗證註解，為避免在驗證皆未通過，使用迴圈輸出完整的錯誤訊息
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (int i = 0, length = fieldErrors.size(); i < length; i++) {
                //依索引值放入個別錯誤
                FieldError field = fieldErrors.get(i);
                model.addAttribute(i + "-" + field.getField(), field.getDefaultMessage()); //出錯的名稱&訊息放入。
                model.addAttribute("sessionVO", sessionVO);
            }
            System.out.println("資料不全");
            for (ObjectError error : result.getAllErrors()) {
                System.out.println(error.getDefaultMessage());
            }
            return "back_end/session/update_session_input";
        }


        /*************************** 2.開始修改資料 *****************************************/

//        SessionVO sessionNew = sessionService.addSession(sessionVO);

        SessionVO sessionUpdate =  sessionService.updateSession(sessionVO);
        System.out.println("場次編輯成功");

        //成功後再新增場次時段，有四個時段要放進去
        List<String> tpsVO = new ArrayList<>();
        tpsVO.add(timePeriod1);
        tpsVO.add(timePeriod2);
        tpsVO.add(timePeriod3);
        tpsVO.add(timePeriod4);

        for (String timePeriods : tpsVO) {
            if (timePeriods == null || timePeriods.isEmpty()) {

            } else {
                Time time = stringToSqlTimeConverter.convert(timePeriods);
                Time_PeriodVO tpVO = new Time_PeriodVO();
                tpVO.setTimePeriod(time);
                tpVO.setSessionVO(sessionUpdate);//FK 場次新增完的結果
                time_PeriodService.updateTimePeriod(tpVO);
            }
        }
        /*************************** 3.修改完成,準備轉交(Send the Success view) **************/
        model.addAttribute("success", "修改成功～");
        List<SessionVO> list = sessionService.getAll();
        model.addAttribute("sessionListData", list);
        return "back_end/session/listAllSession";

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

//	@GetMapping("timePeriodsByActivityDate")
//	public String  getTimePeriodsByActivityDate(@RequestParam("activityDate")@DateTimeFormat(pattern = "yyyy-MM-dd")  Date activityDate, Model model){
//		model.addAttribute("activityDate", activityDate);
//		List<Time> timePeriods = sessionService.getTimePeriodByActivityDate(activityDate);
//		model.addAttribute("timePeriods", timePeriods);
//		model.addAttribute("timePeriodsByActivityDate", "true");
//		return "back_end/session/selectTime";
//	}

    //透過活動日期找場次時段
    @GetMapping("/timePeriodsByActivityDate")
    @ResponseBody
    public List<TimePeriodDTO> getTimePeriodsByActivityDate(HttpServletRequest req, HttpServletResponse res) {

        //activityDate先轉為sql.Date
        Date actDate = java.sql.Date.valueOf(req.getParameter("activityDate"));
//		System.out.println(actDate);
        //得到所有的時段
        List<Time_PeriodVO> list = sessionService.getTimePeriodsByActivityDate(actDate);
//		System.out.println(list.size());

        Iterator<Time_PeriodVO> iterator = list.iterator();
        while (iterator.hasNext()) {
            Time_PeriodVO tpVO = iterator.next();
            Integer sessionTimePeriodId = tpVO.getSessionTimePeriodId();

            //透過Time_PeriodVO取得所有場次Id
            SessionVO sessionVO = tpVO.getSessionVO();
            Integer activitySessionId = sessionVO.getActivitySessionId();

//			System.out.println("tpid" + sessionTimePeriodId);
//			System.out.println("sessID" + activitySessionId);

            //透過comparePeople方法做比較，TRUE的話移除list裡面的元素
            if (comparePeople(sessionTimePeriodId, activitySessionId)) {
                iterator.remove();  // 使用 iterator remove()方法移除元素
            }
        }

        //回傳JSON格式到前端
        List<TimePeriodDTO> dtos = new ArrayList<>();
        for (Time_PeriodVO vo : list) {
            TimePeriodDTO dto = new TimePeriodDTO();
            dto.setSessionTimePeriodId(vo.getSessionTimePeriodId());
            dto.setTimePeriod(vo.getTimePeriod());
            dtos.add(dto);
        }
        return dtos;
    }

    //取得訂單總人數
    public Integer getTotalEnrollNumber(@RequestParam("sessionTimePeriodId") Integer sessionTimePeriodId) {
        List<ActivityOrderVO> list = activityOrderService.getTotalEnrollNumber(sessionTimePeriodId);
        Integer totalEnrollNumber = 0;

        for (ActivityOrderVO orderVO : list) {
            System.out.println("getEnrollNumber" + orderVO.getEnrollNumber());
            totalEnrollNumber += orderVO.getEnrollNumber();
        }
        return totalEnrollNumber;
    }

    //取得場次最大參加人數
    public Integer getActivityMaxPart(@RequestParam("activitySessionId") Integer activitySessionId) {
        SessionVO sessionVO = sessionService.getOneSession(activitySessionId);
        return sessionVO.getActivityMaxPart();
    }

    //比較訂單總人數、場次最大參加人數
    public Boolean comparePeople(@RequestParam("sessionTimePeriodId") Integer sessionTimePeriodId,
                                 @RequestParam("activitySessionId") Integer activitySessionId) {
        //透過時段Id取得訂單總人數
        Integer totalEnrollNumber = getTotalEnrollNumber(sessionTimePeriodId);
        //System.out.println(totalEnrollNumber);

        //透過場次Id取得場次最大參加人數
        Integer activityMaxPart = getActivityMaxPart(activitySessionId);
        //System.out.println(activityMaxPart);

        //如果訂單總人數>=場次最大參加人數，得到True，就不要顯示時段
        if (totalEnrollNumber >= activityMaxPart) {
            return true;
        } else {
            return false;
        }
    }

    //取得所有已建立的活動日期
    @GetMapping("/availableDates")
    @ResponseBody
    public List<String> getAvailableDates() {
        List<Date> availableDates = sessionService.getAvailableDates();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return availableDates.stream()
                .map(sdf::format)
                .collect(Collectors.toList());
    }


    /*
     * Method used to populate the List Data in view. 如 :
     * <form:select path="deptno" id="deptno" items="${deptListData}" itemValue="deptno" itemLabel="dname" />
     */
    @ModelAttribute("itemListData2")
    protected List<ItemVO> referenceListData() {
        List<ItemVO> list = itemService.getAll();
        return list;
    }

    @ModelAttribute("timePeriodListData2")
    protected List<Time_PeriodVO> referenceListData2() {
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
