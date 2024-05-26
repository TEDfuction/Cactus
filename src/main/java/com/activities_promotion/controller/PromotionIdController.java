package com.activities_promotion.controller;

import com.activities_promotion.model.PromotionService;
import com.activities_promotion.model.PromotionVO;
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
@RequestMapping("/promotion")
public class PromotionIdController {

    @Autowired
    PromotionService promotionService;

    /*
     * This method will be called on select_promotion.html form submission, handling POST
     * request It also validates the user input
     */
    @PostMapping("getOne_For_Display")
    public String getOne_For_Display(
        /***************************1.接收請求參數 - 輸入格式的錯誤處理*************************/
        @Digits(integer = 5, fraction = 0, message = "活動促銷編號：請填寫數字")
        @RequestParam("promotionId") String promotionId,
        ModelMap model){
    /***************************2.開始查詢資料*********************************************/
        PromotionVO promotionVO = promotionService.getOnePromotion(Integer.valueOf(promotionId));
        List<PromotionVO> list = promotionService.getAll();
        model.addAttribute("promotionListData", list);

        if(promotionVO == null){
            model.addAttribute("errorMessage", "查無資料");
            return "back_end/promotion/select_promotion";

        }
        /***************************3.查詢完成,準備轉交(Send the Success view)*****************/
        model.addAttribute("promotionVO", promotionVO);
        model.addAttribute("getOne_For_Display", "true");

        return "back_end/promotion/select_promotion";
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ModelAndView handleError(HttpServletRequest req, ConstraintViolationException e, Model model){
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        StringBuilder strBuilder = new StringBuilder();
        for(ConstraintViolation<?> violation : violations) {
            strBuilder.append(violation.getMessage() + "<br>");
        }

        //==== 以下是當前面第55行返回  ====

        model.addAttribute("promotionVO", new PromotionVO());
        List<PromotionVO> list = promotionService.getAll();
        String message = strBuilder.toString();
        return new ModelAndView("back_end/promotion/select_promotion", "errorMessage", "請修正以下錯誤:<br>"+message);
    }


}
