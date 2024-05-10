package com.activity.hibernate.util.CompositeQuery;

import java.sql.Timestamp;
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

import com.activities_item.model.ItemVO;
import com.activities_session.model.SessionVO;

public class Session_Compositegory {
	
	public static Predicate getSession_For_AnyDB(CriteriaBuilder builder, Root<SessionVO> root, String columnName, String value){
        Predicate predicate = null;
        
        if("activitySessionId".equals(columnName) || 
        		"activityMaxPart".equals(columnName)|| 
        		"activityMinPart".equals(columnName)||
        		"enrollTotal".equals(columnName)||
        		"activitySessionState".equals(columnName)){ // 用於Integer
            predicate = builder.equal(root.get(columnName), Integer.valueOf(value));
        } else if ("activityLocation".equals(columnName) || 
        		"activityNote".equals(columnName) ) {// 用於varchar
            predicate = builder.like(root.get(columnName), "%" + value + "%");
        }else if("activityStarted".equals(columnName)||
        		"activityEnd".equals(columnName)||
        		"enrollStarted".equals(columnName)||
        		"enrollEnd".equals(columnName)) {
        	predicate = builder.equal(root.get(columnName), Timestamp.valueOf(value)); //用於datetime
        } else if ("activityId".equals(columnName)) {
        	ItemVO itemVO = new ItemVO();
        	itemVO.setActivityId(Integer.valueOf(value));
        	predicate = builder.equal(root.get("itemVO"), itemVO);
        }
        return predicate;
    }
	
	public static List<SessionVO> getAllSessionVOs(Map<String, String[]> map , Session session) {
		Transaction transaction = session.beginTransaction();
		List<SessionVO> list = null;
		try {
			// 【●創建 CriteriaBuilder】
			CriteriaBuilder builder = session.getCriteriaBuilder();
			// 【●創建 CriteriaQuery】
			CriteriaQuery<SessionVO> criteriaQuery = builder.createQuery(SessionVO.class);
			// 【●創建 Root】
			Root<SessionVO> root = criteriaQuery.from(SessionVO.class);

			List<Predicate> predicateList = new ArrayList<Predicate>();
			
			Set<String> keys = map.keySet();
			int count = 0;
			for (String key : keys) {
				String value = map.get(key)[0];
				if (value != null && value.trim().length() != 0 && !"action".equals(key)) {
					count++;
					predicateList.add(getSession_For_AnyDB(builder, root, key, value.trim()));
					System.out.println("有送出查詢資料的欄位數count = " + count);
				}
			}
			System.out.println("predicateList.size()="+predicateList.size());
			criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
			criteriaQuery.orderBy(builder.asc(root.get("activitySessionId")));
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
