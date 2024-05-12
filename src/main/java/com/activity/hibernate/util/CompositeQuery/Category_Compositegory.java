package com.activity.hibernate.util.CompositeQuery;

import java.util.*;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.activities_category.model.CategoryVO;

public class Category_Compositegory {
	
	public static Predicate getCategory_For_AnyDB(CriteriaBuilder builder, Root<CategoryVO> root, String columnName, String value){
        Predicate predicate = null;
        
        if("activityCategoryId".equals(columnName)){
            predicate = builder.equal(root.get(columnName), Integer.valueOf(value));
        } else if ("activityCategoryName".equals(columnName)) {
            predicate = builder.like(root.get(columnName), "%" + value + "%");
        } else if ("activityCategoryInfo".equals(columnName)) {
        	predicate = builder.like(root.get(columnName), "%" + value + "%");
        }
        return predicate;
    }

    @SuppressWarnings("unchecked")
    public static List<CategoryVO> getAllCategory(Map<String ,String[]> map , Session session){

        Transaction transaction = session.beginTransaction();
        List<CategoryVO> list = null;
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<CategoryVO> criteriaQuery = builder.createQuery(CategoryVO.class);
            Root<CategoryVO> root = criteriaQuery.from(CategoryVO.class);
            List<Predicate> predicateList = new ArrayList<Predicate>();

            Set<String> keys = map.keySet();
            int count = 0;
            for (String key : keys){
                String value = map.get(key)[0];
                if(value != null && value.trim().length() != 0 && !"action".equals(key)){ //??  !"action".equals(key) 不等於欄位名稱
                    count++;
                    predicateList.add(getCategory_For_AnyDB(builder, root, key, value.trim()));
                    System.out.println("有送出查詢資料的欄位數count = " + count);
                }
            }
            System.out.println("predicateList.size()="+predicateList.size()); //複合查詢的欄位數(有輸入值的部分)
            criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
            criteriaQuery.orderBy(builder.asc(root.get("activityCategoryId")));
            Query query =session.createQuery(criteriaQuery);
            list = query.getResultList(); //返回List<CategoryVO> list = null;

            transaction.commit();
        }catch (RuntimeException ex){
            if(transaction != null){
                transaction.rollback();
                System.out.println(ex.getMessage()); //throw ex;

            }
        }finally {
            session.close();
        }
        return list;
    }

}
