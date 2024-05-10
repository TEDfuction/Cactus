package com.activities_item.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.activities_category.model.CategoryVO;
import com.activities_category.model.CategoryService;
import com.activities_item.model.ItemVO;
import com.activities_item.model.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	ItemService itemService;
	
	@Autowired
	CategoryService categoryService;
	
	
	/*
	 * 如要新增時
	 * This method will serve as addItem.html handler.
	 */
	@GetMapping("addItem")
	public String addItem(ModelMap model) {
		ItemVO itemVO = new ItemVO();
		model.addAttribute("itemVO", itemVO);
		System.out.println("轉交請求");
		return "back_end/item/addItem";
		
	}
	/*
	 * This method will be called on addItem.html form submission, handling POST request It also validates the user input
	 * 新增
	 * BindingResult:配合@Valid一起使用，用于接收bean中的校验信息
	 */
	@PostMapping("insert")
	public String insert(@Valid ItemVO itemVO, BindingResult result, ModelMap model) throws IOException{
		
/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		
    	if(result.hasErrors()) {
    		System.out.println("資料有誤");
    		return "back_end/item/addItem";
    	}
    	/*************************** 2.開始新增資料 *****************************************/
		itemService.addItem(itemVO);
		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
		List<ItemVO> list = itemService.getAll();
		model.addAttribute("itemListData", list);
		model.addAttribute("success", "- (新增成功)");
		return "redirect:/item/listAllItem"; // 新增成功後重導至IndexController_inSpringBoot.java的第58行@GetMapping("/emp/listAllEmp")
    
	}
	
	/*
	 * This method will be called on listAllItem.html form submission, handling POST request
	 * 刪除
	 */

	@PostMapping("delete")
	public String delete(@RequestParam("activityId") String activityId , ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		
		/*************************** 2.開始刪除資料 *****************************************/
		itemService.deleteItem(Integer.valueOf(activityId));
		/*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
		List<ItemVO> list = itemService.getAll();
		model.addAttribute("itemListData", list);
		model.addAttribute("success", "- (刪除成功)");
		return "back_end/item/listAllItem"; // 刪除完成後轉交listAllItem.html
	}
	
	/*
	 * Method used to populate the List Data in view. 如 : 
	 * <form:select path="deptno" id="deptno" items="${deptListData}" itemValue="deptno" itemLabel="dname" />
	 */
	@ModelAttribute("categoryListData2")
	protected List<CategoryVO> referenceListData(){
		List<CategoryVO> list = categoryService.getAll();
		return list;
	}
	
	
	/*
	 * 如要修改時
	 * This method will be called on listAllItem.html form submission, handling POST request
	 */
	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("activityId") String activityId,
									ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/

		ItemVO itemVO = itemService.getOneItem(Integer.valueOf(activityId));
		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("itemVO", itemVO);
		System.out.println("修改開始");
		return "back_end/item/update_item_input"; // 查詢完成後轉交update_category_input.html
	}
	
	
	@PostMapping("update")
	public String update(@Valid ItemVO itemVO, BindingResult result, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/

		if(result.hasErrors()) {
			System.out.println("資料不全");
			return "back_end/item/update_item_input";
		}
		/*************************** 2.開始修改資料 *****************************************/
		itemService.updateItem(itemVO);
		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		model.addAttribute("itemVO", itemVO);
		return "back_end/item/listOneItem"; // 修改成功後轉交listOneItem.html
		
	}
	
	/*
     * This method will be called on select_item.html form submission, handling POST request
     *複合查詢
     */
	
//	@PostMapping("listItem_ByCompositeQuery")
//	public String listAllItem(HttpServletRequest req, Model model) {
//		Map<String, String[]> map = req.getParameterMap();
//		List<ItemVO> list = itemService.getAll(map);
//		model.addAttribute("itemListData", list); // for listAllItem.html 第85行用
//		return "back_end/item/listAllItem";
//	}
	
}
