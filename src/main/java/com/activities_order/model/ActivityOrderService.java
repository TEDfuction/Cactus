package com.activities_order.model;

import com.activities.hibernate.util.ActivityOrder_Compositegory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ActivityOrderService {

    @Autowired
    ActivityOrderRepository activityOrderRepository;

    @Autowired
    private SessionFactory sessionFactory;

    public void addOrder(ActivityOrderVO activityOrderVO){
        activityOrderRepository.save(activityOrderVO);
    }

    public void updateOrder(ActivityOrderVO activityOrderVO){
        activityOrderRepository.save(activityOrderVO);
    }

    public void deleteOrder(Integer activityOrderId){
        if(activityOrderRepository.existsById(activityOrderId))
            activityOrderRepository.deleteById(activityOrderId);
    }

    public ActivityOrderVO getOneOrder(Integer activityOrderId){
        Optional<ActivityOrderVO> optional = activityOrderRepository.findById(activityOrderId);
        return optional.orElse(null);// public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
    }

    public List<ActivityOrderVO> getAll(){
        return activityOrderRepository.findAll();
    }

    public List<ActivityOrderVO> getOrderTimeBetween(Date start, Date end){
        return activityOrderRepository.findByOrderTimeBetween(start, end);
    }

    public List<ActivityOrderVO> getAll(Map<String, String[]> map){
        return ActivityOrder_Compositegory.getAllOrders(map,sessionFactory.openSession());
    }



}
