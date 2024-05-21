package com;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.activities_attendees.model.AttendeesService;
import com.activities_attendees.model.AttendeesVO;
import com.activities_category.model.CategoryService;
import com.activities_item.model.ItemService;
import com.activities_item.model.ItemVO;
import com.activities_order.model.ActivityOrderService;
import com.activities_order.model.ActivityOrderVO;
import com.activities_photo.model.PhotoService;
import com.activities_photo.model.PhotoVO;
import com.activities_promotion.model.PromotionService;
import com.activities_promotion.model.PromotionVO;
import com.activities_session.model.SessionService;
import com.activities_session.model.SessionVO;
import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.session_time_period.model.Time_PeriodVO;



//@PropertySource("classpath:application.properties") // 於https://start.spring.io建立Spring Boot專案時, application.properties文件預設已經放在我們的src/main/resources 目錄中，它會被自動檢測到
@Controller
public class IndexController_inSpringBoot {
	
	// @Autowired (●自動裝配)(Spring ORM 課程)
	// 目前自動裝配了EmpService --> 供第66使用
	@Autowired
	PhotoService phoSvc;
	
	@Autowired
	ItemService itemSvc;
	
	@Autowired
	ActivityOrderService activityOrderService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	SessionService sessionService;
	
	@Autowired
	PromotionService promotionService;
	
	@Autowired
	MemberService memberService;

	@Autowired
	AttendeesService attendeesService;
	
    // inject(注入資料) via application.properties
    @Value("${welcome.message}")
    private String message;
	
    private List<String> myList = Arrays.asList("Spring Boot Quickstart 官網 : https://start.spring.io", "IDE 開發工具", "直接使用(匯入)官方的 Maven Spring-Boot-demo Project + pom.xml", "直接使用官方現成的 @SpringBootApplication + SpringBootServletInitializer 組態檔", "依賴注入(DI) HikariDataSource (官方建議的連線池)", "Thymeleaf", "Java WebApp (<font color=red>快速完成 Spring Boot Web MVC</font>)");
    //1
    @GetMapping("/activity/activityPhoto")
    public String index(Model model) {
    	model.addAttribute("message", message);
        model.addAttribute("myList", myList);
        return "/front_end/activity/index"; //view
    }
    
    @GetMapping("/activity/activityOrder")
    public String order(Model model) {
    	model.addAttribute("message", message);
        model.addAttribute("myList", myList);
        return "/front_end/activity/order"; //view
    }
    
    
    @GetMapping("/activity/addAttendees")
    public String insertaddAttendees(Model model) {
    	 AttendeesVO attendeesVO = new AttendeesVO();
         model.addAttribute("attendeesVO", attendeesVO);
         
        return "/front_end/activity/addAttendees"; //view
    }
    
    
    
    
  
    
    
    @GetMapping("/activity/test")
    public String test(Model model) {
    	 AttendeesVO attendeesVO = new AttendeesVO();
         model.addAttribute("attendeesVO", attendeesVO);
         
        return "/front_end/activity/test"; //view
    }
    
    @ModelAttribute("activityOrderListData")
	protected List<ActivityOrderVO> referenceListDataAc(Model model) {
		model.addAttribute("activityOrderVO", new ActivityOrderVO());
		List<ActivityOrderVO> list = activityOrderService.getAll();
		return list;
	}
    
    @ModelAttribute("sessionListData") // 下拉選單、SHOW跑出DB已有的值 for select_page.html 第97 109行用 // for listAllEmp.html 第85行用
	protected List<SessionVO> referenceListData_Sess(Model model){
		model.addAttribute("sessionVO", new SessionVO());
		List<SessionVO> list = sessionService.getAll();

		return list;
	}
    
    @ModelAttribute("promotionListData") // 下拉選單、SHOW跑出DB已有的值 for select_page.html 第97 109行用 // for listAllEmp.html 第85行用
	protected List<PromotionVO> referenceListPro(Model model){
		model.addAttribute("promotionVO", new PromotionVO());
		List<PromotionVO> list = promotionService.getAll();
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
		model.addAttribute("sessionVO", new SessionVO());
		List<SessionVO> list = sessionService.getAll();
		return list;
	}

    
    @ModelAttribute("attendeesListData")
	protected List<AttendeesVO> referenceListDataAt(Model model) {
		model.addAttribute("attendeesVO", new AttendeesVO());
		List<AttendeesVO> list = attendeesService.getAll();
		return list;
	}
    
    
    
    
    
    
    
//	@ModelAttribute("timePeriodListData") // 下拉選單、SHOW跑出DB已有的值 for select_page.html 第97 109行用 // for listAllEmp.html 第85行用
//	protected List<Time_PeriodVO> referenceListData_Time(Model model){
//		model.addAttribute("time_PeriodVO", new Time_PeriodVO());
//		List<Time_PeriodVO> list = time_periodService.getAll();
//		return list;
//	}
    
    
    
    
    
    
    
    
    
    
    
//    @GetMapping("addfrom")
//    public String addfrom(ModelMap model){
//
//        AttendeesVO attendeesVO = new AttendeesVO();
//        model.addAttribute("attendeesVO", attendeesVO);
//        System.out.println("請求轉交");
//        return "front_end/activity/activityOrderConfirm";
//    }

    
   
    
    
    
    
    // http://......../hello?name=peter1
    @GetMapping("/hello")
    public String indexWithParam(
            @RequestParam(name = "name", required = false, defaultValue = "") String name, Model model) {
        model.addAttribute("message", name);
        return "index"; //view
    }
    
  
    //=========== 以下第63~75行是提供給 /src/main/resources/templates/back-end/emp/select_page.html 與 listAllEmp.html 要使用的資料 ===================   
    @GetMapping("/activity/select_photo")
	public String select_photo(Model model) {
		return "back_end/activity/select_photo";
	}
    
    @GetMapping("/activity/listAllPhoto")
	public String listAllPhoto(Model model) {
		return "back_end/activity/listAllPhoto";
	}
    
    @ModelAttribute("photoListData")  // for select_page.html 第97 109行用 // for listAllEmp.html 第85行用
	protected List<PhotoVO> referenceListData(Model model) {
		
    	List<PhotoVO> list = phoSvc.getAll();
		return list;
	}
    
	@ModelAttribute("photoListData") // for select_page.html 第135行用
	protected List<PhotoVO> referenceListData_Photo(Model model) {
		model.addAttribute("photoVO", new PhotoVO()); // for select_page.html 第133行用
		List<PhotoVO> list = phoSvc.getAll();
		return list;
	}

}