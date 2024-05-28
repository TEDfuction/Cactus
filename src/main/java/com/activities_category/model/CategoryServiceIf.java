package com.activities_category.model;

import java.util.List;
import java.util.Map;

public interface CategoryServiceIf {
	
	CategoryVO addCategory(CategoryVO categoryVO);
	
	CategoryVO updaCategory(CategoryVO categoryVO);
	
	CategoryVO deleteCategory(Integer activityCategoryId);
	
//	CategoryVO getByCategoryId(Integer activityCategoryId);
	
	CategoryVO getOneCategory(Integer activityCategoryId);
	
//	List<CategoryVO> getAllCategoryVO(int currentPage);
	
	List<CategoryVO> getAll();
	
	List<CategoryVO> getAll(Map<String, String[]> map);
	
	int getPageTotal();
	
//	List<CategoryVO> getCategoryVOsByCompositeQuery(Map<String, String[]> map);


}

