package com.activities_promotion.model;

import com.activities_promotion.model.PromotionVO;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.Set;

public class Promotion_Compositegory {

    public static Predicate getPromotion_For_AnyDB(CriteriaBuilder builder, Root<PromotionVO> root, String columnName, String value){
        Predicate predicate = null;

        if("promotionId".equals(columnName)){ // 用於Integer
            predicate = builder.equal(root.get(columnName), Integer.valueOf(value));
        } else if ("promotionTitle".equals(columnName) ||
                "promotionContent".equals(columnName)) {// 用於varchar
            predicate = builder.like(root.get(columnName), "%" + value + "%");
        }else if("promotionState".equals(columnName)) {
            predicate = builder.equal(root.get(columnName), Boolean.valueOf(value));
        }
        return predicate;
    }

        public static List<PromotionVO> getAllPromotionVOs(Map<String, String[]> map , Session session) {
            Transaction transaction = session.beginTransaction();
            List<PromotionVO> list = null;
            try {
                // 【●創建 CriteriaBuilder】
                CriteriaBuilder builder = session.getCriteriaBuilder();
                // 【●創建 CriteriaQuery】
                CriteriaQuery<PromotionVO> criteriaQuery = builder.createQuery(PromotionVO.class);
                // 【●創建 Root】
                Root<PromotionVO> root = criteriaQuery.from(PromotionVO.class);

                List<Predicate> predicateList = new ArrayList<Predicate>();

                Set<String> keys = map.keySet();
                int count = 0;
                for (String key : keys) {
                    String value = map.get(key)[0];
                    if (value != null && value.trim().length() != 0 && !"action".equals(key)) {
                        count++;
                        predicateList.add(getPromotion_For_AnyDB(builder, root, key, value.trim()));
                        System.out.println("有送出查詢資料的欄位數count = " + count);
                    }
                }
                System.out.println("predicateList.size()="+predicateList.size());
                criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
                criteriaQuery.orderBy(builder.asc(root.get("promotionId")));
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
