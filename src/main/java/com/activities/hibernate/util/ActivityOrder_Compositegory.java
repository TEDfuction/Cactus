package com.activities.hibernate.util;

import com.activities_order.model.ActivityOrderVO;
import com.activities_promotion.model.PromotionVO;
import com.activities_session.model.SessionVO;
import com.member.model.MemberVO;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;


public class ActivityOrder_Compositegory {

    public static Predicate getItem_For_AnyDB(CriteriaBuilder builder, Root<ActivityOrderVO> root, String columnName, String value) {
        Predicate predicate = null;

        if ("activityOrderId".equals(columnName) || "enrollNumber".equals(columnName)
                || "orderState".equals(columnName) || "refundState".equals(columnName)) { // 用於Integer
            predicate = builder.equal(root.get(columnName), Integer.valueOf(value));
        } else if ("orderMemo".equals(columnName)) {// 用於varchar
            predicate = builder.like(root.get(columnName), "%" + value + "%");
        } else if ("memberId".equals(columnName)) {
            MemberVO memberVO = new MemberVO();
            memberVO.setMemberId(Integer.valueOf(value));
            predicate = builder.equal(root.get("memberVO"), memberVO);
        } else if ("activitySessionId".equals(columnName)) {
            SessionVO sessionVO = new SessionVO();
            sessionVO.setActivitySessionId(Integer.valueOf(value));
            predicate = builder.equal(root.get("sessionVO"), sessionVO);
        } else if ("promotionId".equals(columnName)) {
            PromotionVO promotionVO = new PromotionVO();
            promotionVO.setPromotionId(Integer.valueOf(value));
            predicate = builder.equal(root.get("promotionVO"), promotionVO);
        }
        return predicate;
    }

    @SuppressWarnings("unchecked")
    public static List<ActivityOrderVO> getAllOrders(Map<String, String[]> map , Session session){
        Transaction transaction = session.beginTransaction();
        List<ActivityOrderVO> list = null;
        try {
            // 【●創建 CriteriaBuilder】
            CriteriaBuilder builder = session.getCriteriaBuilder();
            // 【●創建 CriteriaQuery】
            CriteriaQuery<ActivityOrderVO> criteriaQuery = builder.createQuery(ActivityOrderVO.class);
            // 【●創建 Root】
            Root<ActivityOrderVO> root = criteriaQuery.from(ActivityOrderVO.class);

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
            criteriaQuery.orderBy(builder.asc(root.get("activityOrderId")));
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

