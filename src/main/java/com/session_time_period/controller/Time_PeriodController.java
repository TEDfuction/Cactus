package com.session_time_period.controller;

import com.session_time_period.model.Time_PeriodService;
import com.session_time_period.model.Time_PeriodVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/timePeriod")
public class Time_PeriodController {

    @Autowired
    Time_PeriodService time_periodService;

    /*
     * 如要新增時
     * This method will serve as addTimePeriod.html handler.
     */
    @GetMapping("addTimePeriod")
    public String addTimePeriod(ModelMap model) {
        Time_PeriodVO time_PeriodVO = new Time_PeriodVO();
        model.addAttribute("time_PeriodVO", time_PeriodVO);
        System.out.println("轉交請求");
        return "back_end/timePeriod/addTimePeriod";

    }
    /*
     * This method will be called on addTimePeriod.html form submission, handling POST request It also validates the user input
     * 新增
     * BindingResult:配合@Valid一起使用，用于接收bean中的校验信息
     */
    @PostMapping("insert")
    public String insert(@Valid Time_PeriodVO time_PeriodVO, BindingResult result, ModelMap model
                         ,@RequestParam("timePeriod")String  timePeriod) throws Exception {

/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
        result = removeFieldError(time_PeriodVO, result, "timePeriod");

        Time timePeriodTime = null;

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        java.util.Date date = dateFormat.parse(timePeriod);
        timePeriodTime = new Time(date.getTime());

//        if(timePeriod1 == null || timePeriod1.isEmpty()|| time_PeriodVO.getTimePeriod1().equals(new Time(0))){
//            result.rejectValue("timePeriod1", "error.time_period1", "請輸入有效的時間");
//        }else {
//            java.util.Date date1 = dateFormat.parse(timePeriod1);
//            timePeriod1Time = new Time(date1.getTime());
//        }

//        if(timePeriod2 == null || timePeriod2.isEmpty()){
//
//        }else {
//            java.util.Date date2 = dateFormat.parse(timePeriod2);
//            timePeriod2Time = new Time(date2.getTime());
//        }


        time_PeriodVO.setTimePeriod(timePeriodTime);

//        System.out.println("=======================");
//		System.out.println(timePeriod1);
//		System.out.println("=======================");

//        if (time_PeriodVO.getTimePeriod1() == null || time_PeriodVO.getTimePeriod1().equals(new Time(0))) {
//            // 時間欄位為 null 或者是 Time(0)，進行相應處理，例如：
//            result.rejectValue("timePeriod1", "error.time_period1", "請輸入有效的時間");
//        }
        if(result.hasErrors()) {
            System.out.println("資料有誤");
            return "back_end/timePeriod/addTimePeriod";
        }


        /*************************** 2.開始新增資料 *****************************************/
        time_periodService.addTimePeriod(time_PeriodVO);
        /*************************** 3.新增完成,準備轉交(Send the Success view) **************/
        List<Time_PeriodVO> list = time_periodService.getAll();
//        model.addAttribute("time_PeriodVO", time_PeriodVO);
        model.addAttribute("timePeriodListData", list);
        model.addAttribute("success", "- (新增成功)");
        return "redirect:/timePeriod/listAllTimePeriod"; // 新增成功後重導至IndexController_inSpringBoot.java的第58行@GetMapping("/emp/listAllEmp")

    }

    // 將字串時間轉換為 Java 中的 Time 物件的方法
//    private Time convertStringToTime(String timeString) throws Exception {
//        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//        java.util.Date date = sdf.parse(timeString);
//        return new Time(date.getTime());
//    }

    public BindingResult removeFieldError(Time_PeriodVO time_PeriodVO, BindingResult result, String removedFieldname) {
        List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
                .filter(fieldname -> !fieldname.getField().equals(removedFieldname))
                .collect(Collectors.toList());
        result = new BeanPropertyBindingResult(time_PeriodVO, "time_PeriodVO");
        for (FieldError fieldError : errorsListToKeep) {
            result.addError(fieldError);
        }
        return result;
    }

    @ModelAttribute("timePeriodListData") // 下拉選單、SHOW跑出DB已有的值 for select_page.html 第97 109行用 // for listAllEmp.html 第85行用
    protected List<Time_PeriodVO> referenceListData_Time(Model model){
        model.addAttribute("time_PeriodVO", new Time_PeriodVO());
        List<Time_PeriodVO> list = time_periodService.getAll();
        return list;
    }
}
