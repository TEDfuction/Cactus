package com.activities_category.controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.activities_category.model.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.activities_category.model.CategoryVO;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;
	private String activityCategoryId;
	
	/*
	 * 如要新增時
	 * This method will serve as addCategory.html handler.
	 */
	@GetMapping("addCategory")
	public String addCategory(ModelMap model) {
		CategoryVO categoryVO = new CategoryVO();
		model.addAttribute("categoryVO", categoryVO);
		System.out.println("轉交請求");
		return "back_end/category/addCategory";
	}
	
	/*
	 * This method will be called on addCategory.html form submission, handling POST request It also validates the user input
	 * 新增
	 * BindingResult:配合@Valid一起使用，用于接收bean中的校验信息
	 */
    @PostMapping("insert")
    public String insert(@Valid CategoryVO categoryVO , BindingResult result, ModelMap model) throws IOException{
    	/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		
    	if(result.hasErrors()) {
    		System.out.println("資料有誤");
    		return "back_end/category/addCategory";
    	}
    	/*************************** 2.開始新增資料 *****************************************/
		categoryService.addCategory(categoryVO);
		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
		List<CategoryVO> list = categoryService.getAll();
		model.addAttribute("categoryListData", list);
		model.addAttribute("success", "新增成功～");
		// 在成功消息返回后使用JavaScript的alert方法
//		model.addAttribute("alertScript", "alert('新增成功');");
		return "back_end/category/listAllCategory"; // 新增成功後重導至IndexController_inSpringBoot.java的第58行@GetMapping("/emp/listAllEmp")
    }
	
	
	/*
	 * This method will be called on listAllCategory.html form submission, handling POST request
	 * 刪除
	 */
	@PostMapping("delete")
	public String delete(@RequestParam("activityCategoryId") String activityCategoryId
															, RedirectAttributes redirectAttributes) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始刪除資料 *****************************************/
		categoryService.deleteCategory(Integer.valueOf(activityCategoryId));
		/*************************** 3.刪除完成,準備轉交(Send the Success view) **************/

//		List<CategoryVO> list = categoryService.getAll();
//		model.addAttribute("categoryListData", list);
		redirectAttributes.addFlashAttribute("success", "刪除成功");
//		model.addAttribute("successdelete", "刪除成功");
		return "redirect:/category/listAllCategory"; // 刪除完成後轉交listAllCategory.html
	}
	
	/*
	 * This method will be called on listAllCategory.html form submission, handling POST request
	 */
	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("activityCategoryId") String activityCategoryId, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/
		
		CategoryVO categoryVO = categoryService.getOneCategory(Integer.valueOf(activityCategoryId));

		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("categoryVO", categoryVO);
		System.out.println("修改開始");
		return "back_end/category/update_category_input"; // 查詢完成後轉交update_category_input.html
	}
	
	
	@PostMapping("update")
	public String update(@Valid CategoryVO categoryVO, BindingResult result, ModelMap model) throws IOException{
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/

		if(result.hasErrors()) {
			System.out.println("資料不全");
			return "back_end/category/update_category_input";
		}

		/*************************** 2.開始修改資料 *****************************************/
		categoryService.updateCategory(categoryVO);
		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "修改成功～");
		List<CategoryVO> list = categoryService.getAll();
		model.addAttribute("categoryListData", list);
		return "back_end/category/listAllCategory"; // 修改成功後轉交listOneCategory.html

	}
	
	
	
	/*
     * This method will be called on select_category.html form submission, handling POST request
     *複合查詢
     */
    @PostMapping("listCategory_ByCompositeQuery")
    public String listAllCategory(HttpServletRequest req, Model model) {
        Map<String, String[]> map = req.getParameterMap();
        List<CategoryVO> list = categoryService.getAll(map);
        model.addAttribute("categoryListData", list); // for listAllCategory.html 第85行用
        return "back_end/category/listAllCategory";
    }
    

}
