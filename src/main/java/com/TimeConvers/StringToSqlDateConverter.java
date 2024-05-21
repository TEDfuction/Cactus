package com.TimeConvers;

import org.springframework.core.convert.converter.Converter;


import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/*
* 將 String 類型的日期格式轉換為 java.sql.Date 類型的轉換器
* 格式為"yyyy-MM-dd"
 */

public class StringToSqlDateConverter implements Converter<String, Date> {

    // 設置日期格式：yyyy-MM-dd
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    public Date convert(String source) {
        try {
            // 將 String 轉換為 java.util.Date
            java.util.Date utilDate = dateFormat.parse(source);
            // 將 java.util.Date 轉換為 java.sql.Date
            return new Date(utilDate.getTime());
        } catch (ParseException e) {
            // 如果轉換失敗，可以選擇打印錯誤或返回 null
            e.printStackTrace();
            System.out.println("轉換失敗");
            return null;
        }
    }

}
