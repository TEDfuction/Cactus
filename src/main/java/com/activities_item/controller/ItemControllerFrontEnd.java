package com.activities_item.controller;

import com.activities_category.model.CategoryService;
import com.activities_category.model.CategoryVO;
import com.activities_item.model.ItemService;
import com.activities_item.model.ItemVO;
import com.activities_order.model.ActivityOrderService;
import com.activities_order.model.ActivityOrderVO;
import com.activities_session.model.SessionService;
import com.activities_session.model.SessionVO;
import com.session_time_period.model.TimePeriodDTO;
import com.session_time_period.model.Time_PeriodVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Digits;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/activity")
public class ItemControllerFrontEnd {

    @Autowired
    ItemService itemService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    SessionService sessionService;

    @Autowired
    ActivityOrderService activityOrderService;


    @GetMapping("getOne_For_FrontDisplay")
    public String getOne_For_FrontDisplay(
            /***************************1.接收請求參數 - 輸入格式的錯誤處理*************************/
            @Digits(integer = 2, fraction = 0, message = "活動編號：請填寫數字－－勿超過{integer}位數")
            @RequestParam("activityId") String activityId, ModelMap model) {
        /***************************2.開始查詢資料*********************************************/
        ItemVO itemVO = itemService.getOneItem(Integer.valueOf(activityId));
        List<ItemVO> list = itemService.getAll();
        model.addAttribute("itemListData", list);
        model.addAttribute("sessionVOs", itemVO.getSessionVOs());

        model.addAttribute("categoryVO", new CategoryVO());
        List<CategoryVO> list2 = categoryService.getAll();
        model.addAttribute("categoryListData2", list2);

        model.addAttribute("sessionVO", new SessionVO());
        List<SessionVO> list3 = sessionService.getAll();
        model.addAttribute("sessionListData", list3);

//        System.out.println("Activity Info: " + itemVO.getActivityInfo());

        /***************************3.查詢完成,準備轉交(Send the Success view)*****************/
        model.addAttribute("itemVO", itemVO);
        model.addAttribute("getOne_For_FrontDisplay", "true");

        return "front_end/activity/listOneItem";
    }



//    @ModelAttribute("itemListData") // 下拉選單、SHOW跑出DB已有的值 for select_page.html 第97 109行用 // for listAllEmp.html 第85行用
//    protected List<ItemVO> referenceListData2(Model model){
//
//        List<ItemVO> list = itemService.getAll();
//        return list;
//    }
//
//    @ModelAttribute("categoryListData2") // 下拉選單、SHOW跑出DB已有的值 for select_page.html 第97 109行用 // for listAllEmp.html 第85行用
//    protected List<CategoryVO> referenceListData_Cate(Model model){
//        model.addAttribute("categoryVO", new CategoryVO());
//        List<CategoryVO> list = categoryService.getAll();
//        return list;
//    }

    //透過活動日期找場次時段
    @GetMapping("/timePeriodsByActivityDate")
    @ResponseBody
    public List<TimePeriodDTO> getTimePeriodsByActivityDate(HttpServletRequest req, HttpServletResponse res) {

        //activityDate先轉為sql.Date
        Date actDate = java.sql.Date.valueOf(req.getParameter("activityDate"));
//		System.out.println(actDate);
        //得到所有的時段
        List<Time_PeriodVO> list =  sessionService.getTimePeriodsByActivityDate(actDate);
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
    public Integer getTotalEnrollNumber(@RequestParam("sessionTimePeriodId") Integer sessionTimePeriodId){
        List<ActivityOrderVO> list = activityOrderService.getTotalEnrollNumber(sessionTimePeriodId);
        Integer totalEnrollNumber = 0;

        for(ActivityOrderVO orderVO : list){
            System.out.println("getEnrollNumber"+orderVO.getEnrollNumber());
            totalEnrollNumber += orderVO.getEnrollNumber();
        }
        return totalEnrollNumber;
    }

    //取得場次最大參加人數
    public Integer getActivityMaxPart(@RequestParam("activitySessionId") Integer activitySessionId){
        SessionVO sessionVO = sessionService.getOneSession(activitySessionId);
        return  sessionVO.getActivityMaxPart();
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
        if(totalEnrollNumber >= activityMaxPart){
            return true;
        }else{
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

}
