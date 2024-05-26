package com.config;

import com.TimeConvers.StringToSqlDateConverter;
import com.TimeConvers.StringToSqlTimeConverter;
import com.TimeConvers.StringToSqlTimestampConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // 將自定義 StringToSqlDateConverter 轉換器添加到格式化程序註冊表中
        registry.addConverter(new StringToSqlDateConverter());

        // 將自定義 StringToSqlTimeConverter 轉換器添加到格式化程序註冊表中
        registry.addConverter(new StringToSqlTimeConverter());

        // 將自定義 StringToSqlTimestampConverter 轉換器添加到格式化程序註冊表中
        registry.addConverter(new StringToSqlTimestampConverter());
    }

    //上傳的圖片可以通過瀏覽器訪問
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/uploads/");
    }
}
