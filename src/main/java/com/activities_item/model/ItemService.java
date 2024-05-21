package com.activities_item.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.activities.hibernate.util.Item_Compositegory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("itemService")
public class ItemService {
	
	@Autowired
	ItemRepository itemRepository;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void addItem(ItemVO itemVO) {
		itemRepository.save(itemVO);
	}
	
	public void updateItem(ItemVO itemVO) {
		itemRepository.save(itemVO);
	}
	public void deleteItem(Integer activityId) {
		if(itemRepository.existsById(activityId))
			itemRepository.deleteByActivityId(activityId);
	}
	public ItemVO getOneItem(Integer activityId) {
		Optional<ItemVO> optional = itemRepository.findById(activityId);
		return optional.orElse(null);// public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}
	public List<ItemVO> getAll(){

        return itemRepository.findAll();
    }
	public List<ItemVO> getAll(Map<String, String[]> map){
		return Item_Compositegory.getAllItemVOs(map, sessionFactory.openSession());
	}
	

}
