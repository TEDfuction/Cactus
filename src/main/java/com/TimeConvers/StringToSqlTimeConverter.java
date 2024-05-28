package com.TimeConvers;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class StringToSqlTimeConverter implements Converter<String, Time> {

    // 定義日期時間格式：HH:mm
    private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("HH:mm");


    @Override
    public Time convert(String source) {
        try {
            // 將字串解析為 java.util.Date
            java.util.Date utilDate = dateTimeFormat.parse(source);
            // 將 java.util.Date 轉換為 java.sql.Timestamp
            return new Time(utilDate.getTime());
        } catch (ParseException e) {
            // 如果解析失敗，可以打印錯誤消息或進行其他的處理
            e.printStackTrace();
            System.out.println("轉換失敗");
            return null;
        }
    }

}
