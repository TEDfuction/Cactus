package com.activities_attendees.controller;

import com.activities_attendees.model.AttendeesVO;
import com.activities_attendees.model.AttendeesService;
import com.activities_order.model.ActivityOrderService;
import com.activities_order.model.ActivityOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/attendees")
public class AttendeesController {
    @Autowired
    AttendeesService attendeesService;

    @Autowired
    ActivityOrderService activityOrderService;

    /*
     * 如要新增時
     * This method will serve as addAttendees.html handler.
     */
    @GetMapping("addAttendees")
    public String addAttendees(ModelMap model){

        AttendeesVO attendeesVO = new AttendeesVO();
        model.addAttribute("attendeesVO", attendeesVO);
        System.out.println("請求轉交");
        return "back_end/attendees/addAttendees";
    }
    
    
    /*
     * This method will be called on addAttendees.html form submission, handling POST request It also validates the user input
     * 新增
     */
    @PostMapping("insert")
    public String insert(@Valid AttendeesVO attendeesVO, BindingResult result, ModelMap model){
        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
        if(result.hasErrors()){
            //驗證方式： 若屬性存在一個以上的錯誤驗證註解，為避免在驗證皆未通過，使用迴圈輸出完整的錯誤訊息
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (int i = 0, length = fieldErrors.size(); i < length; i++) {
                //依索引值放入個別錯誤
                FieldError field = fieldErrors.get(i);
                model.addAttribute(i + "-" + field.getField(), field.getDefaultMessage()); //出錯的名稱&訊息放入。
                model.addAttribute("attendeesVO", attendeesVO);
            }
            System.out.println("資料有誤");
            return "back_end/attendees/addAttendees";
        }
        /*************************** 2.開始新增資料 *****************************************/
        //先更新enrollNumber的值，再做add新增
        // 先獲取對應的 ActivityOrderId
//        Integer activityOrderId = attendeesVO.getActivityOrderVO().getActivityOrderId();
        // 如果相關的 ActivityOrderId 存在，則對其 enrollNumber 屬性進行增加
//        if (activityOrderId != null) {
//            ActivityOrderVO activityOrderVO = activityOrderService.getOneOrder(activityOrderId);
//            Integer currentEnrollNumber = activityOrderVO.getEnrollNumber();
//            if (currentEnrollNumber != null) {
//                System.out.println("當前報名人數: " + currentEnrollNumber);
//                activityOrderVO.setEnrollNumber(currentEnrollNumber + 1);
//                // 保存更新後的 ActivityOrderVO
//                activityOrderService.updateOrder(activityOrderVO);
//                System.out.println("更新後的報名人數: " + activityOrderVO.getEnrollNumber());
//                attendeesVO.setActivityOrderVO(activityOrderVO);
//            }
//        }

        attendeesService.addAttendees(attendeesVO);
        /*************************** 3.新增完成,準備轉交(Send the Success view) **************/
        List<AttendeesVO> list = attendeesService.getAll();
        model.addAttribute("attendeesListData", list);
        model.addAttribute("success", "新增成功");
        return "back_end/attendees/listAllAttendees";
    }
    
//    @PostMapping("insert")
//    public String insert1(@Valid AttendeesVO attendeesVO, BindingResult result, ModelMap model){
//        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//        if(result.hasErrors()){
//            System.out.println("資料有誤");
//            return "front_end/activity/activityOrderConfirm";
//        }
//        /*************************** 2.開始新增資料 *****************************************/
//        attendeesService.addAttendees(attendeesVO);
//        /*************************** 3.新增完成,準備轉交(Send the Success view) **************/
//        List<AttendeesVO> list = attendeesService.getAll();
//        model.addAttribute("attendeesListData", list);
//
//        model.addAttribute("success", "- (新增成功)");
//        return "redirect:/activity/activityOrderConfirm"; // 新增成功後重導
//    }


    /*
     * This method will be called on listAllAttendees.html form submission, handling POST request
     * 刪除
     */

    @PostMapping("delete")
    public String delete(@RequestParam("activityAttendeesId")String activityAttendeesId
                            , RedirectAttributes redirectAttributes){
        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/

        /*************************** 2.開始刪除資料 *****************************************/
            attendeesService.deleteAttendees(Integer.valueOf(activityAttendeesId));


        /*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
//        List<AttendeesVO> list = attendeesService.getAll();
//        model.addAttribute("attendeesListData", list);
        redirectAttributes.addFlashAttribute("success", "刪除成功");
        return "redirect:/attendees/listAllAttendees"; // 刪除完成後轉交listAllAttendees.html

    }

    /*
     * 如要修改時
     * This method will be called on listAllAttendees.html form submission, handling POST request
     */
    @PostMapping("getOne_For_Update")
    public String getOne_For_Update(@RequestParam("activityAttendeesId")String activityAttendeesId,
                                    ModelMap model) {
        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
        /*************************** 2.開始查詢資料 *****************************************/
        AttendeesVO attendeesVO = attendeesService.getOneAttendees(Integer.valueOf(activityAttendeesId));
        /*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
        model.addAttribute("attendeesVO", attendeesVO);
        System.out.println("修改開始");
        return "back_end/attendees/update_attendees_input"; // 查詢完成後轉交update_attendees_input.html
    }

    @PostMapping("update")
    public String update(@Valid AttendeesVO attendeesVO, BindingResult result, ModelMap model) throws SQLException {
        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
        if(result.hasErrors()) {
            //驗證方式： 若屬性存在一個以上的錯誤驗證註解，為避免在驗證皆未通過，使用迴圈輸出完整的錯誤訊息
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (int i = 0, length = fieldErrors.size(); i < length; i++) {
                //依索引值放入個別錯誤
                FieldError field = fieldErrors.get(i);
                model.addAttribute(i + "-" + field.getField(), field.getDefaultMessage()); //出錯的名稱&訊息放入。
                model.addAttribute("attendeesVO", attendeesVO);
            }
            System.out.println("資料不全");
            return "back_end/attendees/update_attendees_input";
        }

        /*************************** 2.開始修改資料 *****************************************/
        /*************************** 2.檢查電子郵件是否重複 ******************************/
//        if (attendeesService.existsByEmailExcludingId(attendeesVO.getAttendeesEmail(), attendeesVO.getActivityAttendeesId())) {
//            model.addAttribute("emailError", "The email address is already registered.");
//            model.addAttribute("attendeesVO", attendeesVO);
//            return "back_end/attendees/update_attendees_input";
//        }

        attendeesService.updateAttendees(attendeesVO);
        /*************************** 3.修改完成,準備轉交(Send the Success view) **************/
        model.addAttribute("success", "修改成功～");
        List<AttendeesVO> list = attendeesService.getAll();
        model.addAttribute("attendeesListData", list);
        return "back_end/attendees/listAllAttendees"; // 修改成功後轉交listOneAttendees.html

    }

    @PostMapping("getAttendeesName")
    public String getAttendeesName(@RequestParam("attendeesName") String attendeesName, ModelMap model){
        //開始查詢資料
        List<AttendeesVO> list = attendeesService.getAttendeesName("%" + attendeesName + "%");
        model.addAttribute("attendeesListData", list);
        return "back_end/attendees/listAllAttendees";
    }

    @PostMapping("getAttendeesEmail")
    public String getAttendeesEmail(@RequestParam("attendeesEmail") String attendeesEmail, ModelMap model){
        //開始查詢資料
        List<AttendeesVO> list = attendeesService.getAttendeesEmail("%" + attendeesEmail + "%");
        model.addAttribute("attendeesListData", list);
        return "back_end/attendees/listAllAttendees";
    }

    @ModelAttribute("activityOrderListData")
    protected List<ActivityOrderVO> referenceListDataAc(Model model) {
        model.addAttribute("activityOrderVO", new ActivityOrderVO());
        List<ActivityOrderVO> list = activityOrderService.getAll();
        return list;
    }




}

