package com.activities_order.controller;

import com.activities_order.model.ActivityOrderService;
import com.activities_order.model.ActivityOrderVO;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Digits;
import java.util.List;
import java.util.Set;

@Controller
@Validated
@RequestMapping("/activityOrder")
public class ActivityOrderIdController {

    @Autowired
    ActivityOrderService activityOrderService;

    /*
     * This method will be called on select_order.html form submission, handling POST
     * request It also validates the user input
     */
    @PostMapping("getOne_For_Display")
    public String getOne_For_Display(
            /***************************1.接收請求參數 - 輸入格式的錯誤處理*************************/
            @Digits(integer = 5, fraction = 0, message = "活動訂單編號：請輸入數字")
            @RequestParam("activityOrderId") String activityOrderId,
            ModelMap model) {
        /***************************2.開始查詢資料*********************************************/
        ActivityOrderVO activityOrderVO = activityOrderService.getOneOrder(Integer.valueOf(activityOrderId));
        List<ActivityOrderVO> list = activityOrderService.getAll();
        model.addAttribute("activityOrderListData", list);

        if (activityOrderVO == null) {
            model.addAttribute("errorMessage", "查無資料");
            return "back_end/activityOrder/select_order";
        }
        /***************************3.查詢完成,準備轉交(Send the Success view)*****************/
        model.addAttribute("activityOrderVO", activityOrderVO);
        model.addAttribute("getOne_For_Display", "true");

        return "back_end/activityOrder/select_order";
    }

    @PostMapping("getOne_For_Display2")
    public String getOne_For_Display2(
            /***************************1.接收請求參數 - 輸入格式的錯誤處理*************************/
            @RequestParam("activityOrderId") String activityOrderId,
            ModelMap model) {
        /***************************2.開始查詢資料*********************************************/
        ActivityOrderVO activityOrderVO = activityOrderService.getOneOrder(Integer.valueOf(activityOrderId));
        List<ActivityOrderVO> list = activityOrderService.getAll();
        model.addAttribute("activityOrderListData", list);

        /***************************3.查詢完成,準備轉交(Send the Success view)*****************/
        model.addAttribute("activityOrderVO", activityOrderVO);
        model.addAttribute("getOne_For_Display", "true");

        return "back_end/activityOrder/listAllOrder";
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ModelAndView handleError(HttpServletRequest req, ConstraintViolationException e, Model model){
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        StringBuilder strBuilder = new StringBuilder();
        for(ConstraintViolation<?> violation : violations) {
            strBuilder.append(violation.getMessage() + "<br>");
        }

        //==== 以下是當前面第54行返回  ====

        model.addAttribute("activityOrderVO", new ActivityOrderVO());
        List<ActivityOrderVO> list = activityOrderService.getAll();
        String message = strBuilder.toString();
        return new ModelAndView("back_end/activityOrder/select_order", "errorMessage", "請修正以下錯誤:<br>"+message);
    }

}
