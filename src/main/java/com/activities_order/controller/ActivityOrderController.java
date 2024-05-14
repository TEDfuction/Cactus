package com.activities_order.controller;

import com.activities_order.model.ActivityOrderService;
import com.activities_order.model.ActivityOrderVO;
import com.activities_promotion.model.PromotionService;
import com.activities_promotion.model.PromotionVO;
import com.activities_session.model.SessionService;
import com.activities_session.model.SessionVO;
import com.member.model.MemberService;
import com.member.model.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    public String delete(@RequestParam("activityOrderId") String activityOrderId, ModelMap model){
        /*************************** 1.開始刪除資料 *****************************************/
        activityOrderService.deleteOrder(Integer.valueOf(activityOrderId));
        /*************************** 2.刪除完成,準備轉交(Send the Success view) **************/
        List<ActivityOrderVO> list = activityOrderService.getAll();
        model.addAttribute("activityOrderListData", list);
        model.addAttribute("success", "- (刪除成功)");
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
        /*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
        model.addAttribute("activityOrderVO", activityOrderVO);
        System.out.println("修改開始");
        return "back_end/activityOrder/update_order_input"; // 查詢完成後轉交update_order_input.html

    }
    @PostMapping("update")
    public String update(@Valid ActivityOrderVO activityOrderVO,BindingResult result, ModelMap model){
        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/

        if(result.hasErrors()) {
            System.out.println("資料不全");
            return "back_end/activityOrder/update_order_input";
        }
        /*************************** 2.開始修改資料 *****************************************/
        activityOrderService.updateOrder(activityOrderVO);
        /*************************** 3.修改完成,準備轉交(Send the Success view) **************/
        model.addAttribute("success", "修改成功～");
        List<ActivityOrderVO> list = activityOrderService.getAll();
        model.addAttribute("activityOrderListData", list);
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





}

