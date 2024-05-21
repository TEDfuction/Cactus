package com.activities_category.model;

import java.util.*;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.activity.hibernate.util.CompositeQuery.Category_Compositegory;

@Service("categoryService")
public class CategoryService {
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void addCategory(CategoryVO categoryVO){
        categoryRepository.save(categoryVO);

    }

    public void updateCategory(CategoryVO categoryVO){

        categoryRepository.save(categoryVO);
    }

    public void deleteCategory(Integer activityCategoryId){
        if(categoryRepository.existsById(activityCategoryId))
            categoryRepository.deleteById(activityCategoryId);

    }

    public CategoryVO getOneCategory(Integer activityCategoryId){
        Optional<CategoryVO> optional = categoryRepository.findById(activityCategoryId);
        return optional.orElse(null); // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
    }

    public List<CategoryVO> getAll(){

        return categoryRepository.findAll();
    }

    public List<CategoryVO> getAll(Map<String ,String[]> map ){
        return Category_Compositegory.getAllCategory(map, sessionFactory.openSession());
    }
}
