package com.activities_promotion.controller;

import com.activities_promotion.model.PromotionService;
import com.activities_promotion.model.PromotionVO;
import com.activities_session.model.SessionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/promotion")
public class PromotionController {

    @Autowired
    PromotionService promotionService;

    /*
     * 如要新增時
     * This method will serve as addPromotion.html handler.
     */
    @GetMapping("addPromotion")
    public String addPromotion(ModelMap model){

        PromotionVO promotionVO = new PromotionVO();
        model.addAttribute("promotionVO", promotionVO);
        System.out.println("請求轉交");
        return "back_end/promotion/addPromotion";
    }

    /*
     * This method will be called on addPromotion.html form submission, handling POST request It also validates the user input
     * 新增
     * BindingResult:配合@Valid一起使用，用于接收bean中的校验信息
     */
    @PostMapping("insert")
    public String insert(@Valid PromotionVO promotionVO, BindingResult result, ModelMap model
                         ){
        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//        result = removeFieldError(promotionVO, result, "promotionStarted");
//        result = removeFieldError(promotionVO, result, "promotionEnd");
        if(result.hasErrors()){
             System.out.println("資料有誤");
             return "back_end/promotion/addPromotion";
         }
//        java.sql.Date promotionStartedToDate = null;
//        java.sql.Date promotionEndToDate = null;
//
//        try {
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            Date parsedDate1 = dateFormat.parse(promotionStarted);
//            Date parsedDate2 = dateFormat.parse(promotionEnd);
//
//            promotionStartedToDate = new java.sql.Date(parsedDate1.getTime());
//            promotionEndToDate = new java.sql.Date(parsedDate2.getTime());
////
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            // 處理轉換異常
//        }
//        // set回 promotionVO 對象
//        promotionVO.setPromotionStarted(promotionStartedToDate);
//        promotionVO.setPromotionEnd(promotionEndToDate);

        /*************************** 2.開始新增資料 *****************************************/
        promotionService.addPromotion(promotionVO);
        /*************************** 3.新增完成,準備轉交(Send the Success view) **************/
        List<PromotionVO> list = promotionService.getAll();
        model.addAttribute("promotionListData", list);
        model.addAttribute("success", "新增成功～");
        return "redirect:/promotion/listAllPromotion"; // 新增成功後重導

    }
    /*
     * This method will be called on listAllPromotion.html form submission, handling POST request
     * 刪除
     */
    @PostMapping("delete")
    public String delete(@RequestParam("promotionId")String promotionId, ModelMap model){
        /*************************** 1.開始刪除資料 *****************************************/
        promotionService.deletePromotion(Integer.valueOf(promotionId));
        /*************************** 2.刪除完成,準備轉交(Send the Success view) **************/
        List<PromotionVO> list = promotionService.getAll();
        model.addAttribute("promotionListData", list);
        model.addAttribute("success", "刪除成功");
        return "redirect:/promotion/listAllPromotion"; // 刪除完成後轉交listAllPromotion.html

    }

    /*
     * 如要修改時
     * This method will be called on listAllPromotion.html form submission, handling POST request
     */
    @PostMapping("getOne_For_Update")
    public String getOne_For_Update(@RequestParam("promotionId")String promotionId, ModelMap model) {
        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
        /*************************** 2.開始查詢資料 *****************************************/
        PromotionVO promotionVO = promotionService.getOnePromotion(Integer.valueOf(promotionId));
        /*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
        model.addAttribute("promotionVO", promotionVO);
        System.out.println("修改開始");
        return "back_end/promotion/update_promotion_input"; // 查詢完成後轉交update_promotion_input.html
    }
    @PostMapping("update")
    public String update(@Valid PromotionVO promotionVO, BindingResult result, ModelMap model) throws IOException {
        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/

        if(result.hasErrors()) {
            System.out.println("資料不全");
            return "back_end/promotion/update_promotion_input";
        }
        /*************************** 2.開始修改資料 *****************************************/
        promotionService.updatePromotion(promotionVO);
        /*************************** 3.修改完成,準備轉交(Send the Success view) **************/
        model.addAttribute("success", "修改成功～");
        List<PromotionVO> list = promotionService.getAll();
        model.addAttribute("promotionListData", list);
        return "back_end/promotion/listAllPromotion"; // 修改成功後轉交listOnePromotion.html
    }

    @GetMapping("getPromotionTitle")
    public String getPromotionTitle(@RequestParam("promotionTitle") String promotionTitle, ModelMap model){

        //開始查詢資料
        List<PromotionVO> list = promotionService.getPromotionTitle("%" + promotionTitle + "%");
        model.addAttribute("promotionListData", list);
        return "back_end/promotion/listAllPromotion";

    }

    @GetMapping("getStartedAndEnd")
    public String getStartedAndEnd(@RequestParam("promotionStarted")@DateTimeFormat(pattern = "yyyy-MM-dd") Date promotionStarted,
                                   @RequestParam("promotionEnd") @DateTimeFormat(pattern = "yyyy-MM-dd")Date promotionEnd, Model model) throws Exception{

        model.addAttribute("promotionStarted", promotionStarted);
        model.addAttribute("promotionEnd",promotionEnd);

        List<PromotionVO> startedAndEnd = promotionService.getStartedAndEnd(promotionStarted,promotionEnd);
        model.addAttribute("promotionListData", startedAndEnd);
        return "back_end/promotion/listAllPromotion";
    }

    // 去除BindingResult中某個欄位的FieldError紀錄
    public BindingResult removeFieldError(PromotionVO promotionVO, BindingResult result, String removedFieldname) {
        List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
                .filter(fieldname -> !fieldname.getField().equals(removedFieldname))
                .collect(Collectors.toList());
        result = new BeanPropertyBindingResult(promotionVO, "promotionVO");
        for (FieldError fieldError : errorsListToKeep) {
            result.addError(fieldError);
        }
        return result;
    }



}
