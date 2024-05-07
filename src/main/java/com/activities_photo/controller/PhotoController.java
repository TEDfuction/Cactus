package com.activities_photo.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.web.multipart.MultipartFile;

import com.activities_item.model.ItemService;
import com.activities_item.model.ItemVO;
import com.activities_photo.model.PhotoService;
import com.activities_photo.model.PhotoVO;



@Controller
@RequestMapping("/activity")
public class PhotoController {

	@Autowired
	PhotoService photoSvc;

	@Autowired
	ItemService  itemSvc;

	/*
	 * This method will serve as addEmp.html handler.
	 */
	@GetMapping("addPhoto")
	public String addEmp(ModelMap model) {
		PhotoVO photoVO = new PhotoVO();
		model.addAttribute("photoVO", photoVO);
		return "back_end/activity/addPhoto";
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