package com.activities_photo.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.activities_attendees.model.AttendeesService;
import com.activities_attendees.model.AttendeesVO;
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
import com.session_time_period.model.TimePeriodDTO;
import com.session_time_period.model.Time_PeriodService;
import com.session_time_period.model.Time_PeriodVO;



@Controller
@RequestMapping("/activity")
public class PhotoController {

	@Autowired
	PhotoService photoSvc;

	@Autowired
	ItemService  itemSvc;
	
	@Autowired
    AttendeesService attendeesService;
	
	@Autowired
    ActivityOrderService activityOrderService;
	
	@Autowired
	SessionService sessionService;
	
	
	@Autowired
	MemberService memSvc;
	
	@Autowired
    Time_PeriodService time_periodService;
	
	@Autowired
    PromotionService promotionService;
	
	
	

	/*
	 * This method will serve as addEmp.html handler.
	 */
	@GetMapping("addPhoto")
	public String addEmp(ModelMap model) {
		PhotoVO photoVO = new PhotoVO();
		model.addAttribute("photoVO", photoVO);
		return "back_end/activity/addPhoto";
	}

	
	// 2先從最初的網頁按下按鈕後可以找出對應 PhotoId的值
		@PostMapping("listDetailPhoto")
		public String listDetailPhoto(ModelMap model,
				                       @RequestParam("activityId")Integer activityId,
				                       @RequestParam("activityPhotoId")String activityPhotoId
				                     
				                        ) {
			model.addAttribute("sessionVO", new SessionVO());
	        List<SessionVO> list = sessionService.getAll();
	        model.addAttribute("sessionListData", list);
//			/*************************** 2.開始查詢資料 *****************************************/
	        ItemVO itemVO = itemSvc.getOneItem(activityId);  
	        model.addAttribute("itemListData");
	        model.addAttribute("itemVO",itemVO);
	        
	        PhotoVO photoVO = photoSvc.findById(Integer.valueOf(activityPhotoId));
			model.addAttribute("photoVO",photoVO);
	        
	        
			return "/front_end/activity/listDetailPhoto";
			
			
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	

		
		// 3之後找出對應 PhotoId的值
		@GetMapping("listDetailAddAttendees")
		public String listDetailAddAttendeesSuc(ModelMap model,
				                               @RequestParam("activityId")Integer activityId,
				                               @RequestParam("activityPhotoId")String activityPhotoId,
						                       @RequestParam("sessionTimePeriodId")Integer timePeriodId
                                
				) {
			model.addAttribute("sessionVO", new SessionVO());
	        List<SessionVO> list = sessionService.getAll();
	        model.addAttribute("sessionListData", list);
			/*************************** 2.開始查詢資料 *****************************************/
	        
	        Time_PeriodVO time_periodVO = time_periodService.getOneTimePeriod(timePeriodId); 
	        model.addAttribute("time_periodVO", time_periodVO);
	        ItemVO itemVO = itemSvc.getOneItem(activityId);  
	        model.addAttribute("itemListData");
	        model.addAttribute("itemVO",itemVO);
			AttendeesVO attendeesVO = new AttendeesVO();
			List<ActivityOrderVO> list2 = activityOrderService.getAll();
	        model.addAttribute("activityOrderListData", list2);
	        model.addAttribute("attendeesVO", attendeesVO);
	        
	        PhotoVO photoVO = photoSvc.findById(Integer.valueOf(activityPhotoId));
			model.addAttribute("photoVO",photoVO);
	        
	        
			return "/front_end/activity/listDetailAddAttendees";
				
		}
		
		
	
	// 4確定結果沒問題按送出
	@GetMapping("confirmAttendees")
	public String confirmAttendees(ModelMap model, HttpSession session) {
	    /********************** 3. 從 session 中獲取數據 **************************/
	    AttendeesVO attendeesVO = (AttendeesVO) session.getAttribute("attendeesVO");
	    ItemVO itemVO = (ItemVO) session.getAttribute("itemVO"); 
	    Time_PeriodVO time_periodVO = (Time_PeriodVO)session.getAttribute("time_periodVO");
	    Integer time_period = time_periodVO.getSessionTimePeriodId();
	    Time_PeriodVO time_periodVOnew = time_periodService.getOneTimePeriod(time_period);
	    /********************** 4.將數據儲存到 model 中，以便在下一頁使用 ************************/
	    List<ActivityOrderVO> list2 = activityOrderService.getAll();
        model.addAttribute("activityOrderListData", list2);
	    model.addAttribute("attendeesVO", attendeesVO);
	    model.addAttribute("itemVO", itemVO);
	    model.addAttribute("time_periodVO", time_periodVOnew);
	    
	    return "/front_end/activity/confirmAttendees"; // 返回到下一頁
	}

		
	

	/*
	 * This method will be called on addEmp.html form submission, handling POST request It also validates the user input
	 */
	@PostMapping("insert")
	public String insert(@Valid PhotoVO photoVO, BindingResult result, ModelMap model,
			@RequestParam("activityPhoto") MultipartFile[] parts) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(photoVO, result, "activityPhoto");

		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的圖片時
			model.addAttribute("errorMessage", "員工照片: 請上傳照片");
		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] buf = multipartFile.getBytes();
				photoVO.setActivityPhoto(buf);
			}
		}
		if (result.hasErrors() || parts[0].isEmpty()) {
			//如果錯誤訊息跳轉頁面沒有返回想要的值給什麼加什麼
//			List<Position>list = positionService.findAllPositions();
//			model.addAllAttribute("positionListData",list);
			return "back_end/activity/addPhoto";
		}
		/*************************** 2.開始新增資料 *****************************************/
		// EmpService empSvc = new EmpService();
		photoSvc.addPhoto(photoVO);
		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
		List<PhotoVO> list = photoSvc.getAll();
		model.addAttribute("photoListData", list);
		model.addAttribute("success", "- (新增成功)");
		return "redirect:/activity/listAllPhoto"; // 新增成功後重導至IndexController_inSpringBoot.java的第58行@GetMapping("/emp/listAllEmp")
	}
	
	
	
	
	
	  //3 送出與錯誤處理也要放photoVO 與 attendessVO
	@PostMapping("insertDetailAddAttendees")
	public String insertDetailAddAttendees(@Valid AttendeesVO attendeesVO, BindingResult result, ModelMap model,
			                               @Valid ActivityOrderVO activityOrderVO,
			                               @Valid Time_PeriodVO  time_periodVO,
	                                       HttpSession session, 
	                                       @RequestParam("activityId")Integer activityId,
	                                       @RequestParam("sessionTimePeriodId") Integer timePeriodId){
		
	    /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
	    if(result.hasErrors()){
	    	ItemVO itemVO = itemSvc.getOneItem(activityId);  
	    	Time_PeriodVO time_periodVO2 = time_periodService.getOneTimePeriod(timePeriodId);
	        model.addAttribute("itemListData");
	        model.addAttribute("itemVO",itemVO);
	        model.addAttribute("time_periodVO", time_periodVO2);
	        List<ActivityOrderVO> list = activityOrderService.getAll();
	        model.addAttribute("activityOrderListData", list);
	        System.out.println("資料有誤"); 
	        return "front_end/activity/listDetailAddAttendees";
	    }
	    
	    System.out.println(activityOrderVO.getEnrollNumber());
	    System.out.println(activityOrderVO.getOrderTime());
	    
	    /********************* 2.存儲需要在下一頁使用的數據到 session 中 *********************/
	    ItemVO itemVO = itemSvc.getOneItem(activityId); // 獲得itemVO对象
	    session.setAttribute("itemVO", itemVO); // 存itemVO到session
	    session.setAttribute("attendeesVO", attendeesVO);
	    session.setAttribute("activityId", activityId);
	    session.setAttribute("activityOrderVO", activityOrderVO);
	    session.setAttribute("time_periodVO", time_periodVO);
	    return "redirect:/activity/confirmAttendees"; // 重定向到下一頁
	}
		
	
	//5.成功後導向到活動首頁
	@PostMapping("success")
	public String success(@Valid ActivityOrderVO activityOrderVO ,ModelMap model, HttpSession session,
			              @RequestParam("sessionId")Integer sessionId
			              ) {
	    AttendeesVO attendeesVO = (AttendeesVO) session.getAttribute("attendeesVO");
	    Integer activityId = (Integer) session.getAttribute("activityId");
      
	    
	    /********************* 新增資料 *********************/
	    String email = (String)session.getAttribute("account");
	    MemberVO xxx = memSvc.findByEmail(email);
	    
//	    SessionVO sessionVO = new SessionVO(); 
//	    sessionVO.setActivitySessionId(11);    
	    SessionVO sessionVO = sessionService.getOneSession(sessionId) ;
	     
        Time_PeriodVO time_periodVO = (Time_PeriodVO)session.getAttribute("time_periodVO");
//	    Time_PeriodVO time_periodVO = new Time_PeriodVO();
//	    time_periodVO.setSessionTimePeriodId(3);
        
        //取得活動日期
        java.util.Date  sessiondate = sessionVO.getActivityDate() ;
        System.out.println("sessiondate"+sessiondate);
        List<PromotionVO> list3 = promotionService.getAll();
        
        Double promotionDiscount = 1.0;
        Integer promotionCoupon = 0;
        Integer promotionId = null;
	    for(PromotionVO promotion : list3) {
	    	java.util.Date  promotionStart = promotion.getPromotionStarted();
	    	System.out.println("promotionStart"+promotionStart);
	    	java.util.Date  promotionEnd = promotion.getPromotionEnd();
	    	System.out.println("promotionEnd"+promotionEnd);
	    	
	    	if((sessiondate.after(promotionStart) || sessiondate.equals(promotionStart)) && (sessiondate.before(promotionEnd) || sessiondate.equals(promotionEnd))) {
	    		System.out.println("aaaaaa");
	    		promotionDiscount = promotion.getPromotionDiscount();
	    		promotionCoupon = promotion.getPromotionCoupon();
	    		promotionId = promotion.getPromotionId();
	    	}else{
	    		promotionDiscount = 1.0;
	    		promotionCoupon =0;
	    	}
	    		
	    }
	    System.out.println("promotionDiscount"+promotionDiscount);
	    System.out.println("promotionCoupon"+promotionCoupon);
	    System.out.println("promotionId"+promotionId);
        
        
	    activityOrderVO.setMemberVO(xxx);
	    activityOrderVO.setSessionVO(sessionVO);
	    attendeesService.addAttendees(attendeesVO);
	    activityOrderVO.setTime_PeriodVO(time_periodVO);
	    activityOrderVO.setOrderState(0);
	    activityOrderVO.setRefundState(0);
	    /*************************** 計算實付金額 *****************************************/
	    // 查詢數據設置到模型中
	    ItemVO itemVO = itemSvc.getOneItem(activityId);   

	    // 透過join拿取activityItem的價格資料
	    Integer activityPrice = itemVO.getActivityPrice();
	    // activityOrderVO的人數以及訂單金額
	    Integer EnrollNumber = activityOrderVO.getEnrollNumber();
//	    Integer promotionPrice = activityOrderVO.getPromotionPrice();
//	    // 如果 promotionPrice 為空，則設為 0
//	    if(promotionPrice == null) {
//	    	promotionPrice = 0;
//	    }
	    // 活動項目價格*活動訂單人數-活動促銷價格
	    Double OrderAmount = (promotionDiscount*(activityPrice * EnrollNumber))- promotionCoupon;
	    System.out.println("OrderAmount"+OrderAmount);
	    
	    activityOrderVO.setOrderAmount(activityPrice * EnrollNumber);
	    activityOrderVO.setPayAmount(OrderAmount.intValue());
	    if(promotionId != null) {
		    activityOrderVO.setPromotionVO(promotionService.getOnePromotion(promotionId));
	    }
	    Integer promotionPrice = (activityPrice * EnrollNumber) - (OrderAmount.intValue());
	    activityOrderVO.setPromotionPrice(promotionPrice);
	    activityOrderService.addOrder(activityOrderVO);
          
	    
	    /********************* 新增完成,準備轉交  *********************/
	    model.addAttribute("itemVO", itemVO);
	    List<AttendeesVO> list = attendeesService.getAll();
	    model.addAttribute("attendeesListData", list);
	    List<ActivityOrderVO> list2 = activityOrderService.getAll();
	    model.addAttribute("activityOrderListData", list2);
	    model.addAttribute("success", "- (新增成功)");
	    
	    // 綠界串流
	    String ecpayCheckout = activityOrderService.ecpayCheckout(activityOrderVO,"活動");
        model.addAttribute("ecpayCheckout", ecpayCheckout);
	    return "front_end/activity/success"; // 重定向到成功頁面
	}
	
	
//ajax
	//透過活動日期找場次時段
    @GetMapping("/timePeriodsByActivityDate")
    @ResponseBody
    public List<TimePeriodDTO> getTimePeriodsByActivityDate(HttpServletRequest req, HttpServletResponse res) {

        //activityDate先轉為sql.Date
        Date actDate = java.sql.Date.valueOf(req.getParameter("activityDate"));
//		System.out.println(actDate);
        //得到所有的時段
        List<Time_PeriodVO> list =  sessionService.getTimePeriodsByActivityDate(actDate);
//		System.out.println(list.size());

        Iterator<Time_PeriodVO> iterator = list.iterator();
        while (iterator.hasNext()) {
            Time_PeriodVO tpVO = iterator.next();
            Integer sessionTimePeriodId = tpVO.getSessionTimePeriodId();

            //透過Time_PeriodVO取得所有場次Id
            SessionVO sessionVO = tpVO.getSessionVO();
            Integer activitySessionId = sessionVO.getActivitySessionId();

//			System.out.println("tpid" + sessionTimePeriodId);
//			System.out.println("sessID" + activitySessionId);

            //透過comparePeople方法做比較，TRUE的話移除list裡面的元素
            if (comparePeople(sessionTimePeriodId, activitySessionId)) {
                iterator.remove();  // 使用 iterator remove()方法移除元素
            }
        }

        //回傳JSON格式到前端
        List<TimePeriodDTO> dtos = new ArrayList<>();
        for (Time_PeriodVO vo : list) {
            TimePeriodDTO dto = new TimePeriodDTO();
            dto.setSessionTimePeriodId(vo.getSessionTimePeriodId());
            dto.setTimePeriod(vo.getTimePeriod());
            dtos.add(dto);
        }
        return dtos;
    }
    //取得訂單總人數
    public Integer getTotalEnrollNumber(@RequestParam("sessionTimePeriodId") Integer sessionTimePeriodId){
        List<ActivityOrderVO> list = activityOrderService.getTotalEnrollNumber(sessionTimePeriodId);
        Integer totalEnrollNumber = 0;

        for(ActivityOrderVO orderVO : list){
            System.out.println("getEnrollNumber"+orderVO.getEnrollNumber());
            totalEnrollNumber += orderVO.getEnrollNumber();
        }
        return totalEnrollNumber;
    }

    //取得場次最大參加人數
    public Integer getActivityMaxPart(@RequestParam("activitySessionId") Integer activitySessionId){
        SessionVO sessionVO = sessionService.getOneSession(activitySessionId);
        return  sessionVO.getActivityMaxPart();
    }

    //比較訂單總人數、場次最大參加人數
    public Boolean comparePeople(@RequestParam("sessionTimePeriodId") Integer sessionTimePeriodId,
                                 @RequestParam("activitySessionId") Integer activitySessionId) {
        //透過時段Id取得訂單總人數
        Integer totalEnrollNumber = getTotalEnrollNumber(sessionTimePeriodId);
        //System.out.println(totalEnrollNumber);

        //透過場次Id取得場次最大參加人數
        Integer activityMaxPart = getActivityMaxPart(activitySessionId);
        //System.out.println(activityMaxPart);

        //如果訂單總人數>=場次最大參加人數，得到True，就不要顯示時段
        if(totalEnrollNumber >= activityMaxPart){
            return true;
        }else{
            return false;
        }
    }

    //取得所有已建立的活動日期
    @GetMapping("/availableDates")
    @ResponseBody
    public List<String> getAvailableDates() {
        List<Date> availableDates = sessionService.getAvailableDates();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return availableDates.stream()
                .map(sdf::format)
                .collect(Collectors.toList());
    }


    
    
	// 2先從最初的網頁按下按鈕後可以找出對應 PhotoId的值
//	@GetMapping("listDetailPhoto")
//	public String listDetailPhoto(ModelMap model,
//			                       @Valid ActivityOrderVO activityOrderVO,
//			                       @RequestParam("activityPhotoId")String activityPhotoId
//			                       
//			                        ) {
//		/*************************** 2.開始查詢資料 *****************************************/
//        //ItemVO itemVO = itemSvc.getOneItem(Integer.valueOf(activityId));
//       // List<ItemVO> list = itemSvc.getAll();
//        //model.addAttribute("itemListData", list);
//        //model.addAttribute("sessionVOs", itemVO.getSessionVOs());
//
//
//        model.addAttribute("sessionVO", new SessionVO());
//        List<SessionVO> list2 = sessionService.getAll();
//        model.addAttribute("sessionListData", list2);
//		PhotoVO photoVO = photoSvc.findById(Integer.valueOf(activityPhotoId));
//		model.addAttribute("photoVO",photoVO);
//		return "/front_end/activity/listDetailPhoto";
//		
//		
//	}   

	// 3之後找出對應 PhotoId的值
//	@GetMapping("listDetailAddAttendees")
//	public String listDetailAddAttendeesSuc(ModelMap model,@RequestParam("activityPhotoId")String activityPhotoId
//			                                ) {
//		
//		PhotoVO photoVO = photoSvc.findById(Integer.valueOf(activityPhotoId));
//		model.addAttribute("photoVO",photoVO);
//		AttendeesVO attendeesVO = new AttendeesVO();
//		List<ActivityOrderVO> list = activityOrderService.getAll();
//        model.addAttribute("activityOrderListData", list);
//        model.addAttribute("attendeesVO", attendeesVO);
//        
//        
//		return "/front_end/activity/listDetailAddAttendees";
//			
//	}
    
    
  //3 送出與錯誤處理也要放photoVO 與 attendessVO
//	@PostMapping("insertDetailAddAttendees")
//	public String insertDetailAddAttendees(@Valid AttendeesVO attendeesVO, BindingResult result, ModelMap model,
//			                               @Valid ActivityOrderVO activityOrderVO,
//	                                       HttpSession session, @RequestParam("activityPhotoId")String activityPhotoId){
//		
//	    /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//	    if(result.hasErrors()){
//	        PhotoVO photoVO = photoSvc.findById(Integer.valueOf(activityPhotoId));
//	        model.addAttribute("photoVO",photoVO);
//	        List<ActivityOrderVO> list = activityOrderService.getAll();
//	        model.addAttribute("activityOrderListData", list);
//	        System.out.println("資料有誤"); 
//	        return "front_end/activity/listDetailAddAttendees";
//	    }
//	    
//	    System.out.println(activityOrderVO.getEnrollNumber());
//	    System.out.println(activityOrderVO.getOrderTime());
//	    
//	    /********************* 2.存儲需要在下一頁使用的數據到 session 中 *********************/
//	    session.setAttribute("attendeesVO", attendeesVO);
//	    session.setAttribute("activityPhotoId", activityPhotoId);
//	    session.setAttribute("activityOrderVO", activityOrderVO);
//	    return "redirect:/activity/confirmAttendees"; // 重定向到下一頁
//	}
    
	
	// 4確定結果沒問題按送出
//	@GetMapping("confirmAttendees")
//	public String confirmAttendees(ModelMap model, HttpSession session) {
//	    /********************** 3. 從 session 中獲取數據 **************************/
//	    AttendeesVO attendeesVO = (AttendeesVO) session.getAttribute("attendeesVO");
//	    String activityPhotoId = (String) session.getAttribute("activityPhotoId");
//
//	    /********************** 4.將數據儲存到 model 中，以便在下一頁使用 ************************/
//	    PhotoVO photoVO = photoSvc.findById(Integer.valueOf(activityPhotoId));
//	    List<ActivityOrderVO> list2 = activityOrderService.getAll();
//        model.addAttribute("activityOrderListData", list2);
//		model.addAttribute("photoVO",photoVO);
//	    model.addAttribute("attendeesVO", attendeesVO);
//	    
//	    
//	    return "/front_end/activity/confirmAttendees"; // 返回到下一頁
//	}

    
    
  //5.成功後導向到活動首頁
//  	@PostMapping("success")
//  	public String success(@Valid ActivityOrderVO activityOrderVO ,ModelMap model, HttpSession session ){
//  	    AttendeesVO attendeesVO = (AttendeesVO) session.getAttribute("attendeesVO");
//  	    String activityPhotoId = (String) session.getAttribute("activityPhotoId");
//        
//  	    
//  	    /********************* 新增資料 *********************/
//  	    String email = (String)session.getAttribute("account");
//  	    MemberVO xxx = memSvc.findByEmail(email);
//  	    SessionVO sessionVO = new SessionVO(); 
//  	    sessionVO.setActivitySessionId(11);
//  	    activityOrderVO.setMemberVO(xxx);
//  	    activityOrderVO.setSessionVO(sessionVO);
//  	    attendeesService.addAttendees(attendeesVO);
//  	    
//  	    activityOrderVO.setOrderState(0);
//  	    activityOrderVO.setRefundState(0);
//  	    /*************************** 計算實付金額 *****************************************/
//  	    // 查詢數據設置到模型中
//  	    PhotoVO photoVO = photoSvc.findById(Integer.valueOf(activityPhotoId));
//  	    // 透過join拿取activityItem的價格資料
//  	    Integer activityPrice = photoVO.getActivityPh().getActivityPrice();
//  	    // activityOrderVO的人數以及訂單金額
//  	    Integer EnrollNumber = activityOrderVO.getEnrollNumber();
//  	    Integer promotionPrice = activityOrderVO.getPromotionPrice();
//  	    // 如果 promotionPrice 為空，則設為 0
//  	    if(promotionPrice == null) {
//  	    	promotionPrice = 0;
//  	    }
//  	    // 活動項目價格*活動訂單人數-活動促銷價格
//  	    int OrderAmount = (activityPrice * EnrollNumber)- promotionPrice;
//  	    activityOrderVO.setOrderAmount(OrderAmount);
//  	    activityOrderVO.setPayAmount(OrderAmount);
//  	    activityOrderService.addOrder(activityOrderVO);
//
//  	    
//  	    /********************* 新增完成,準備轉交  *********************/
//  	    model.addAttribute("photoVO", photoVO);
//  	    List<AttendeesVO> list = attendeesService.getAll();
//  	    model.addAttribute("attendeesListData", list);
//  	    List<ActivityOrderVO> list2 = activityOrderService.getAll();
//  	    model.addAttribute("activityOrderListData", list2);
//  	    model.addAttribute("success", "- (新增成功)");
//  	    
//  	    
//  	    return "redirect:/activity/activityPhoto"; // 重定向到成功頁面
//  	}
	
	
	
	
//  @PostMapping("insertOrder")
//  public String insertOrder(@Valid ActivityOrderVO activityOrderVO, BindingResult result, ModelMap model,
//		                    @Digits(integer = 2, fraction = 0, message = "活動編號：請填寫數字－－勿超過{integer}位數")
//                             @RequestParam("activityId") String activityId ){
//      /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//      if(result.hasErrors()){
//          System.out.println("資料有誤");
//          return "front_end/activity/addOrder";
//      }
//      
//      
//      /*************************** 2.開始查詢資料 *****************************************/
//      ItemVO itemVO = itemSvc.getOneItem(Integer.valueOf(activityId));
//      List<ItemVO> list = itemSvc.getAll();
//      model.addAttribute("itemListData", list);
//      model.addAttribute("sessionVOs", itemVO.getSessionVOs());
//
//
//      model.addAttribute("sessionVO", new SessionVO());
//      List<SessionVO> list2 = sessionService.getAll();
//      model.addAttribute("sessionListData", list2);
//      /*************************** 3.新增完成,準備轉交(Send the Success view) **************/
//      List<ActivityOrderVO> list3 = activityOrderService.getAll();
//      model.addAttribute("attendeesListData", list3);
//      model.addAttribute("success", "- (新增成功)");
//      return "redirect:/activityOrder/listAllOrder"; // 新增成功後重導
//  }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//    @PostMapping("insertOrder")
//    public String insertOrder(@Valid ActivityOrderVO activityOrderVO, BindingResult result, ModelMap model){
//        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//        if(result.hasErrors()){
//            System.out.println("資料有誤");
//            return "front_end/activity/addOrder";
//        }
//        /*************************** 2.開始新增資料 *****************************************/
//        activityOrderService.addOrder(activityOrderVO);
//        /*************************** 3.新增完成,準備轉交(Send the Success view) **************/
//        List<ActivityOrderVO> list = activityOrderService.getAll();
//        model.addAttribute("attendeesListData", list);
//        model.addAttribute("success", "- (新增成功)");
//        return "redirect:/activity/listAllOrder"; // 新增成功後重導
//    }
//	
	
//  @PostMapping("insertOrder")
//  public String insertOrder(@Valid ActivityOrderVO activityOrderVO, BindingResult result, ModelMap model){
//      /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//      if(result.hasErrors()){
//          System.out.println("資料有誤");
//          return "front_end/activity/addOrder";
//      }
//      /*************************** 計算實付金額 *****************************************/
//      Integer EnrollNumber = activityOrderVO.getEnrollNumber();
//      Integer OrderAmount = activityOrderVO.getOrderAmount();
//      Integer promotionPrice = activityOrderVO.getPromotionPrice();
//      //如果 promotionPrice 為空，則設為 0
//      if(promotionPrice == null) {
//    	  promotionPrice = 0;
//      }
//      int PaymentAmount = (EnrollNumber * OrderAmount)- promotionPrice;
//      
//      activityOrderVO.setPayAmount(PaymentAmount);
//      /*************************** 2.開始新增資料 *****************************************/
//      activityOrderService.addOrder(activityOrderVO);
//      /*************************** 3.新增完成,準備轉交(Send the Success view) **************/
//      List<ActivityOrderVO> list = activityOrderService.getAll();
//      model.addAttribute("attendeesListData", list);
//      model.addAttribute("success", "- (新增成功)");
//      return "redirect:/activityOrder/listAllOrder"; // 新增成功後重導
//  }
	
  
//	@PostMapping("listDetailPhotoPrice")
//	public String listDetailPhotoPrice(@RequestParam("activityPrice")Integer activityPrice  ,HttpSession session) {
//		
//		//Integer total = 0;
//		session.setAttribute("activityPrice",activityPrice );
//		return "redirect:/activity/addOrder";
//	}
//	
//	//<span th:value="${session.total}">	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
//	@PostMapping("insertOrder")
//	public String insertOrder(@Valid ActivityOrderVO activityOrderVO,
//			                         ItemVO itemVO,
//			                         BindingResult result, ModelMap model,
//			                         
//			                         @RequestParam("activityId")String activityId){
//	    /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//	    if(result.hasErrors()){
//	        System.out.println("資料有誤");
//	        return "front_end/activity/addOrder";
//	    }
//	    
//	    /*************************** 2.開始新增資料 *****************************************/
//	    // 先得到別的其他VO的價格
//	    
//	    int price = itemVO.getActivityPrice();
//	       
//	    // 從 activityOrderVO 中獲得人數
//	    int numberOfPeople = activityOrderVO.getEnrollNumber();
//	    
//	    // 計算總金額
//	    int totalAmount = price * numberOfPeople;
//	    
//	    // 將總金額設置到 activityOrderVO 中
//	    activityOrderVO.setOrderAmount(totalAmount);
//	    
//	    // 新增訂單
//	    activityOrderService.addOrder(activityOrderVO);
//	    
//	    /*************************** 3.新增完成,準備轉交(Send the Success view) **************/
//	
//	    List<ActivityOrderVO> list = activityOrderService.getAll();
//	    model.addAttribute("attendeesListData", list);
//	    model.addAttribute("success", "- (新增成功)");
//	    return "redirect:/activity/listAllOrder"; // 新增成功後重導
//	}
	
	
	
	
	

	
	
	//3 送出與錯誤處理也要放photoVO 與 attendessVO
//	@PostMapping("insertDetailAddAttendees")
//    public String insertDetailAddAttendees(@Valid AttendeesVO attendeesVO, BindingResult result, ModelMap model,
//    		                         @RequestParam("activityPhotoId")String activityPhotoId){
//		
//        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//        if(result.hasErrors()){
//        	PhotoVO photoVO = photoSvc.findById(Integer.valueOf(activityPhotoId));
//    		model.addAttribute("photoVO",photoVO);
//        	List<ActivityOrderVO> list = activityOrderService.getAll();
//            model.addAttribute("activityOrderListData", list);
//            System.out.println("資料有誤"); 
//            return "front_end/activity/listDetailAddAttendees";
//        }
//        /*************************** 2.開始新增資料 *****************************************/
//        attendeesService.addAttendees(attendeesVO);
//        /*************************** 3.新增完成,準備轉交(Send the Success view) **************/
//        PhotoVO photoVO = photoSvc.findById(Integer.valueOf(activityPhotoId));
//		  model.addAttribute("photoVO",photoVO);
//        List<AttendeesVO> list = attendeesService.getAll();
//        model.addAttribute("attendeesListData", list);
//        List<ActivityOrderVO> list2 = activityOrderService.getAll();
//        model.addAttribute("activityOrderListData", list2);
//        model.addAttribute("success", "- (新增成功)");
//        return "redirect:/activity/listDetailAddAttendees"; // 新增成功後重導
//    }
	
	
	
	
//	@PostMapping("insertAddAttendees")
//    public String insertaddAttendees(@Valid AttendeesVO attendeesVO, BindingResult result, ModelMap model){
//        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//        if(result.hasErrors()){
//        	List<ActivityOrderVO> list = activityOrderService.getAll();
//            model.addAttribute("activityOrderListData", list);
//            System.out.println("資料有誤"); 
//            return "front_end/activity/addAttendees";
//        }
//        /*************************** 2.開始新增資料 *****************************************/
//        attendeesService.addAttendees(attendeesVO);
//        /*************************** 3.新增完成,準備轉交(Send the Success view) **************/
//        List<AttendeesVO> list = attendeesService.getAll();
//        model.addAttribute("attendeesListData", list);
//
//        model.addAttribute("success", "- (新增成功)");
//        return "redirect:/activity/addAttendees"; // 新增成功後重導
//    }
	
	
	
	
	

	/*
	 * This method will be called on listAllEmp.html form submission, handling POST request
	 */
	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("activityPhotoId") String activityPhotoId, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/
		// EmpService empSvc = new EmpService();
		PhotoVO photoVO = photoSvc.getOnePhoto(Integer.valueOf(activityPhotoId));

		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("photoVO", photoVO);
		return "back_end/activity/update_photo_input"; // 查詢完成後轉交update_emp_input.html
	}

	/*
	 * This method will be called on update_emp_input.html form submission, handling POST request It also validates the user input
	 */
	@PostMapping("update")
	public String update(@Valid PhotoVO photoVO, BindingResult result, ModelMap model,
			@RequestParam("activityPhoto") MultipartFile[] parts) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(photoVO, result, "activityPhoto");

		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的新圖片時
			// EmpService empSvc = new EmpService();
			byte[] activityPhoto = photoSvc.getOnePhoto(photoVO.getActivityPhotoId()).getActivityPhoto();
			photoVO.setActivityPhoto(activityPhoto);
		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] activityPhoto = multipartFile.getBytes();
				photoVO.setActivityPhoto(activityPhoto);
			}
		}
		if (result.hasErrors()) {
			return "back_end/activity/update_photo_input";
		}
		/*************************** 2.開始修改資料 *****************************************/
		// EmpService empSvc = new EmpService();
		photoSvc.updatePhoto(photoVO);

		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		photoVO = photoSvc.getOnePhoto(Integer.valueOf(photoVO.getActivityPhotoId()));
		model.addAttribute("photoVO", photoVO);
		return "back_end/activity/listOnePhoto"; // 修改成功後轉交listOneEmp.html
	}

	/*
	 * This method will be called on listAllEmp.html form submission, handling POST request
	 */
	@PostMapping("delete")
	public String delete(@RequestParam("activityPhotoId") String activityPhotoId, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始刪除資料 *****************************************/
		// EmpService empSvc = new EmpService();
		photoSvc.deletePhoto(Integer.valueOf(activityPhotoId));
		/*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
		List<PhotoVO> list = photoSvc.getAll();
		model.addAttribute("photoListData", list);
		model.addAttribute("success", "- (刪除成功)");
		return "back_end/activity/listAllPhoto"; // 刪除完成後轉交listAllEmp.html
	}

	/*
	 * 第一種作法 Method used to populate the List Data in view. 如 : 
	 * <form:select path="deptno" id="deptno" items="${deptListData}" itemValue="deptno" itemLabel="dname" />
	 */
	@ModelAttribute("itemListData")
	protected List<ItemVO> referenceListData() {
		// DeptService deptSvc = new DeptService();
		List<ItemVO> list = itemSvc.getAll();
		return list;
	}

	/*
	 * 【 第二種作法 】 Method used to populate the Map Data in view. 如 : 
	 * <form:select path="deptno" id="deptno" items="${depMapData}" />
	 */
//	@ModelAttribute("deptMapData") //
//	protected Map<Integer, String> referenceMapData() {
//		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
//		map.put(10, "財務部");
//		map.put(20, "研發部");
//		map.put(30, "業務部");
//		map.put(40, "生管部");
//		return map;
//	}

	// 去除BindingResult中某個欄位的FieldError紀錄
//	public BindingResult removeFieldError(EmpVO empVO, BindingResult result, String removedFieldname) {
//		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
//				.filter(fieldname -> !fieldname.getField().equals(removedFieldname))
//				.collect(Collectors.toList());
//		result = new BeanPropertyBindingResult(empVO, "empVO");
//		for (FieldError fieldError : errorsListToKeep) {
//			result.addError(fieldError);
//		}
//		return result;
//	}
	
	/*
	 * This method will be called on select_page.html form submission, handling POST request
	 */
//	@PostMapping("listEmps_ByCompositeQuery")
//	public String listAllEmp(HttpServletRequest req, Model model) {
//		Map<String, String[]> map = req.getParameterMap();
//		List<EmpVO> list = empSvc.getAll(map); //關鍵行
//		model.addAttribute("empListData", list); // for listAllEmp.html 第85行用
//		return "back-end/emp/listAllEmp";
//	}
//	
	

	// 去除BindingResult中某個欄位的FieldError紀錄
			public BindingResult removeFieldError(PhotoVO photoVO, BindingResult result, String removedFieldname) {
				List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
						.filter(fieldname -> !fieldname.getField().equals(removedFieldname))
						.collect(Collectors.toList());
				result = new BeanPropertyBindingResult(photoVO, "photoVO");
				for (FieldError fieldError : errorsListToKeep) {
					result.addError(fieldError);
				}
				return result;
			}

}