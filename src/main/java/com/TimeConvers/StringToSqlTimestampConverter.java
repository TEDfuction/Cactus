package com.TimeConvers;

import org.springframework.core.convert.converter.Converter;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class StringToSqlTimestampConverter implements Converter<String, Timestamp> {

    // 定義日期時間格式：yyyy-MM-dd'T'HH:mm
    private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");


    @Override
    public Timestamp convert(String source) {
        try {
            // 將字串解析為 java.util.Date
            java.util.Date utilDate = dateTimeFormat.parse(source);
            // 將 java.util.Date 轉換為 java.sql.Timestamp
            return new Timestamp(utilDate.getTime());
        } catch (ParseException e) {
            // 如果解析失敗，可以打印錯誤消息或進行其他的處理
            e.printStackTrace();
            System.out.println("轉換失敗");
            return null;
        }
    }
}
