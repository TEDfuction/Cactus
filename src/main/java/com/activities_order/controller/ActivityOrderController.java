package com.activities_order.controller;
import com.activities_order.model.ActivityOrderService;
import com.activities_order.model.ActivityOrderVO;
import com.activities_promotion.model.PromotionService;
import com.activities_promotion.model.PromotionVO;
import com.activities_session.model.SessionService;
import com.activities_session.model.SessionVO;
import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.session_time_period.model.Time_PeriodService;
import com.session_time_period.model.Time_PeriodVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/activityOrder")
public class ActivityOrderController {

    @Autowired
    ActivityOrderService activityOrderService;

    @Autowired
    MemberService memberService;

    @Autowired
    SessionService sessionService;

    @Autowired
    PromotionService promotionService;

    @Autowired
    Time_PeriodService time_periodService;



    /*
     * 如要新增時
     * This method will serve as addOrder.html handler.
     */
    @GetMapping("addOrder")
    public String addOrder(ModelMap model){

        ActivityOrderVO activityOrderVO = new ActivityOrderVO();
        model.addAttribute("activityOrderVO", activityOrderVO);
        System.out.println("請求轉交");
        return "back_end/activityOrder/addOrder";

    }
    /*
     * This method will be called on addOrder.html form submission, handling POST request It also validates the user input
     * 新增
     * BindingResult:配合@Valid一起使用
     */

    @PostMapping("insert")
    public String insert(@Valid ActivityOrderVO activityOrderVO, BindingResult result, ModelMap model){
        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
        if(result.hasErrors()){
            System.out.println("資料有誤");
            return "back_end/activityOrder/addOrder";
        }
        /*************************** 2.開始新增資料 *****************************************/
        activityOrderService.addOrder(activityOrderVO);
        /*************************** 3.新增完成,準備轉交(Send the Success view) **************/
        List<ActivityOrderVO> list = activityOrderService.getAll();
        model.addAttribute("attendeesListData", list);
        model.addAttribute("success", "- (新增成功)");
        return "redirect:/activityOrder/listAllOrder"; // 新增成功後重導
    }
    /*
     * This method will be called on listAllOrder.html form submission, handling POST request
     * 刪除
     */
    @PostMapping("delete")
    public String delete(@RequestParam("activityOrderId") String activityOrderId
                            , RedirectAttributes redirectAttributes){
        /*************************** 1.開始刪除資料 *****************************************/
        activityOrderService.deleteOrder(Integer.valueOf(activityOrderId));
        /*************************** 2.刪除完成,準備轉交(Send the Success view) **************/
//        List<ActivityOrderVO> list = activityOrderService.getAll();
//        model.addAttribute("activityOrderListData", list);
        redirectAttributes.addFlashAttribute("success", "刪除成功");
        return "redirect:/activityOrder/listAllOrder"; // 刪除完成後轉交listAllOrder.html
    }

    /*
     * 如要修改時
     * This method will be called on listAllOrder.html form submission, handling POST request
     */
    @PostMapping("getOne_For_Update")
    public String getOne_For_Update(@RequestParam("activityOrderId") String activityOrderId, ModelMap model){
        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
        /*************************** 2.開始查詢資料 *****************************************/
        ActivityOrderVO activityOrderVO = activityOrderService.getOneOrder(Integer.valueOf(activityOrderId));

        // 取得 itemVO 的 activityPrice
        Integer activityPrice = null;
        if (activityOrderVO.getSessionVO() != null && activityOrderVO.getSessionVO().getItemVO() != null) {
            activityPrice = activityOrderVO.getSessionVO().getItemVO().getActivityPrice();
            System.out.println("activityPrice=" + activityPrice);

            // 計算 orderAmount
            if (activityOrderVO.getEnrollNumber() != null) {
                activityOrderVO.setOrderAmount(activityPrice * activityOrderVO.getEnrollNumber());
            }
        }

        // 計算預設的訂單總金額
        Integer enrollNumber = activityOrderVO.getEnrollNumber(); // 假設已經有報名人數
        Integer defaultOrderAmount = activityPrice * enrollNumber;

        // 根據 activitySessionId 取得所有 sessionTimePeriodId
//        List<Time_PeriodVO> timePeriods = time_periodService.getActivitySessionId(activityOrderVO.getSessionVO().getActivitySessionId());


        /*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
        model.addAttribute("activityOrderVO", activityOrderVO);
        model.addAttribute("activityPrice", activityPrice);
        model.addAttribute("defaultOrderAmount", defaultOrderAmount);
//        model.addAttribute("timePeriods", timePeriods); // 將 timePeriods 傳遞到前端
        System.out.println("修改開始");
        return "back_end/activityOrder/update_order_input"; // 查詢完成後轉交update_order_input.html

    }
    @PostMapping("update")
    public String update(@Valid @ModelAttribute ActivityOrderVO activityOrderVO,BindingResult result,
                         @RequestParam("memberVO.memberId") Integer memberId,
                         @RequestParam("sessionVO.activitySessionId") Integer activitySessionId,
                         @RequestParam("time_PeriodVO.sessionTimePeriodId") Integer sessionTimePeriodId,
                         @RequestParam("promotionVO.promotionId") Integer promotionId,
                         ModelMap model){
        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/

        if(result.hasErrors()) {
            System.out.println("資料不全");
            return "back_end/activityOrder/update_order_input";
        }
        /*************************** 2.開始修改資料 *****************************************/
        MemberVO memberVO = new MemberVO();
        memberVO.setMemberId(memberId);
        activityOrderVO.setMemberVO(memberVO);

        SessionVO sessionVO = new SessionVO();
        sessionVO.setActivitySessionId(activitySessionId);
        activityOrderVO.setSessionVO(sessionVO);

        Time_PeriodVO time_PeriodVO = new Time_PeriodVO();
        time_PeriodVO.setSessionTimePeriodId(sessionTimePeriodId);
        activityOrderVO.setTime_PeriodVO(time_PeriodVO);


        if (activityOrderVO.getPromotionVO() == null) {
            activityOrderVO.setPromotionVO(new PromotionVO());
        }
        activityOrderVO.getPromotionVO().setPromotionId(promotionId);


        activityOrderService.updateOrder(activityOrderVO);
        /*************************** 3.修改完成,準備轉交(Send the Success view) **************/
        model.addAttribute("success", "修改成功～");

        return "back_end/activityOrder/listAllOrder"; // 修改成功後轉交listOneOrder.html

    }

    @PostMapping("listAllActivityOrder")
    public String listAllActivityOrder(HttpServletRequest req, Model model){
        Map<String, String[]> map = req.getParameterMap();
        List<ActivityOrderVO> list = activityOrderService.getAll(map);
        model.addAttribute("activityOrderListData", list);
        return "back_end/activityOrder/listAllOrder";
    }

    @GetMapping("getOrderTimeBetween")
    public String getOrderTimeBetween(
            @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
            @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd") Date end,
            Model model){

        model.addAttribute("start", start);
        model.addAttribute("end", end);

        List<ActivityOrderVO> orderTimeBetween = activityOrderService.getOrderTimeBetween(start, end);
        model.addAttribute("activityOrderListData", orderTimeBetween);
        return "back_end/activityOrder/listAllOrder";

    }

//    //取得訂單總人數
//    public Integer getTotalEnrollNumber(@RequestParam("sessionTimePeriodId") Integer sessionTimePeriodId){
//        Integer totalEnrollNumber = activityOrderService.getTotalEnrollNumber(sessionTimePeriodId);
//
//        return totalEnrollNumber;
//    }
//
//    //取得場次最大參加人數
//    public Integer getActivityMaxPart(@RequestParam("activitySessionId") Integer activitySessionId){
//        SessionVO sessionVO = sessionService.getOneSession(activitySessionId);
//        return  sessionVO.getActivityMaxPart();
//    }
//
//    public Boolean comparePeople(@RequestParam("sessionTimePeriodId") Integer sessionTimePeriodId,
//                                 @RequestParam("activitySessionId") Integer activitySessionId) {
//        Integer totalEnrollNumber = getTotalEnrollNumber(sessionTimePeriodId);
//        Integer activityMaxPart = getActivityMaxPart(activitySessionId);
//        //如果訂單總人數>大於場次最大參加人數，得到True，就不要顯示時段
//        return totalEnrollNumber > activityMaxPart;
//    }

    @ModelAttribute("activityOrderListData")
    protected List<ActivityOrderVO> referenceListDataAc(Model model) {
        List<ActivityOrderVO> list = activityOrderService.getAll();
        return list;
    }

    @ModelAttribute("memberListData")
    protected List<MemberVO> referenceListDataMe(Model model) {
        model.addAttribute("memberVO", new MemberVO());
        List<MemberVO> list = memberService.getAll();
        return list;
    }

    @ModelAttribute("sessionListData")
    protected List<SessionVO> referenceListDataSe(Model model) {
        List<SessionVO> list = sessionService.getAll();
        return list;
    }

    @ModelAttribute("promotionListData")
    protected List<PromotionVO> referenceListDataPr(Model model) {
        List<PromotionVO> list = promotionService.getAll();
        return list;
    }

    @ModelAttribute("timePeriodListData") // 下拉選單、SHOW跑出DB已有的值 for select_page.html 第97 109行用 // for listAllEmp.html 第85行用
    protected List<Time_PeriodVO> referenceListData_Time(Model model){
        model.addAttribute("time_PeriodVO", new Time_PeriodVO());
        List<Time_PeriodVO> list = time_periodService.getAll();
        return list;
    }





}

