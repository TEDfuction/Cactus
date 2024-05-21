package com.session_time_period.controller;

import com.activities_session.model.SessionService;
import com.activities_session.model.SessionVO;
import com.session_time_period.model.Time_PeriodService;
import com.session_time_period.model.Time_PeriodVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@Validated
@RequestMapping("/timePeriod")
public class Time_PeriodIdController {

    @Autowired
    Time_PeriodService time_periodService;
    @Autowired
    SessionService sessionService;

    @PostMapping("getOne_For_Display2")
    public String getOne_For_Display2(@RequestParam("sessionTimePeriodId") String sessionTimePeriodId, ModelMap model){
        Time_PeriodVO time_PeriodVO = time_periodService.getOneTimePeriod(Integer.valueOf(sessionTimePeriodId));
        List<Time_PeriodVO> list = time_periodService.getAll();
        model.addAttribute("timePeriodListData", list);
        List<SessionVO> list2 = sessionService.getAll();
        model.addAttribute("sessionListData",list2);
        model.addAttribute("sessionVO", new SessionVO());

        model.addAttribute("time_periodVO", time_PeriodVO);
        model.addAttribute("getOne_For_Display2", "true");

        return "back_end/timePeriod/listAllTimePeriod";

    }

    @ModelAttribute("timePeriodListData") // 下拉選單、SHOW跑出DB已有的值 for select_page.html 第97 109行用 // for listAllEmp.html 第85行用
    protected List<Time_PeriodVO> referenceListData_Time(Model model){
        model.addAttribute("time_PeriodVO", new Time_PeriodVO());
        List<Time_PeriodVO> list = time_periodService.getAll();
        return list;
    }



}
