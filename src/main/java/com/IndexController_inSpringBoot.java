package com;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.activities_item.model.ItemService;
import com.activities_item.model.ItemVO;
import com.activities_photo.model.PhotoService;
import com.activities_photo.model.PhotoVO;



//@PropertySource("classpath:application.properties") // 於https://start.spring.io建立Spring Boot專案時, application.properties文件預設已經放在我們的src/main/resources 目錄中，它會被自動檢測到
@Controller
public class IndexController_inSpringBoot {
	
	// @Autowired (●自動裝配)(Spring ORM 課程)
	// 目前自動裝配了EmpService --> 供第66使用
	@Autowired
	PhotoService phoSvc;
	
	@Autowired
	ItemService itemSvc;
	
    // inject(注入資料) via application.properties
    @Value("${welcome.message}")
    private String message;
	
    private List<String> myList = Arrays.asList("Spring Boot Quickstart 官網 : https://start.spring.io", "IDE 開發工具", "直接使用(匯入)官方的 Maven Spring-Boot-demo Project + pom.xml", "直接使用官方現成的 @SpringBootApplication + SpringBootServletInitializer 組態檔", "依賴注入(DI) HikariDataSource (官方建議的連線池)", "Thymeleaf", "Java WebApp (<font color=red>快速完成 Spring Boot Web MVC</font>)");
    @GetMapping("/activityPhoto")
    public String index(Model model) {
    	model.addAttribute("message", message);
        model.addAttribute("myList", myList);
        return "/front_end/activity/index"; //view
    }
    @GetMapping("/activityOrder")
    public String order(Model model) {
    	model.addAttribute("message", message);
        model.addAttribute("myList", myList);
        return "/front_end/activity/order"; //view
    }
    
    
    
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