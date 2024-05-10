package com.activity.hibernate.util.CompositeQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.activities_category.model.CategoryVO;
import com.activities_item.model.ItemVO;


public class Item_Compositegory {
	
	public static Predicate getItem_For_AnyDB(CriteriaBuilder builder, Root<ItemVO> root, String columnName, String value){
        Predicate predicate = null;
        
        if("activityId".equals(columnName) || "activityPrice".equals(columnName)){ // 用於Integer
            predicate = builder.equal(root.get(columnName), Integer.valueOf(value));
        } else if ("activityName".equals(columnName) || 
        		   "activityDescription".equals(columnName) || 
        		   "activityInfo".equals(columnName)) {// 用於varchar
            predicate = builder.like(root.get(columnName), "%" + value + "%");
        }else if("activityState".equals(columnName)) {
        	predicate = builder.equal(root.get(columnName), Boolean.valueOf(value));
        } else if ("activityCategoryId".equals(columnName)) {
        	CategoryVO categoryVO = new CategoryVO();
        	categoryVO.setActivityCategoryId(Integer.valueOf(value));
        	predicate = builder.equal(root.get("categoryVO"), categoryVO);
        }
        return predicate;
    }
	
	public static List<ItemVO> getAllItemVOs(Map<String, String[]> map , Session session) {
		Transaction transaction = session.beginTransaction();
		List<ItemVO> list = null;
		try {
			// 【●創建 CriteriaBuilder】
			CriteriaBuilder builder = session.getCriteriaBuilder();
			// 【●創建 CriteriaQuery】
			CriteriaQuery<ItemVO> criteriaQuery = builder.createQuery(ItemVO.class);
			// 【●創建 Root】
			Root<ItemVO> root = criteriaQuery.from(ItemVO.class);

			List<Predicate> predicateList = new ArrayList<Predicate>();
			
			Set<String> keys = map.keySet();
			int count = 0;
			for (String key : keys) {
				String value = map.get(key)[0];
				if (value != null && value.trim().length() != 0 && !"action".equals(key)) {
					count++;
					predicateList.add(getItem_For_AnyDB(builder, root, key, value.trim()));
					System.out.println("有送出查詢資料的欄位數count = " + count);
				}
			}
			System.out.println("predicateList.size()="+predicateList.size());
			criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
			criteriaQuery.orderBy(builder.asc(root.get("activityId")));
			// 【●最後完成創建 javax.persistence.Query●】
			Query query = session.createQuery(criteriaQuery); //javax.persistence.Query; //Hibernate 5 開始 取代原 org.hibernate.Query 介面
			list = query.getResultList();

			transaction.commit();
		} catch (RuntimeException ex) {
			if (transaction != null)
				transaction.rollback();
			throw ex; // System.out.println(ex.getMessage());
		} finally {
			session.close();
			// HibernateUtil.getSessionFactory().close();
		}

		return list;
	}

}
