package cn.itcast.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自定义类型转换器，实现String-->java.util.Date
 *  1. 实现Converter<数据源（都是String）, 要转换的目标类型>  接口
 *  2. 在springmvc.xml 配置转换器工厂、引用转换器工厂
 */
@Component
public class StringToDateConverter implements Converter<String,Date> {
    @Override
    public Date convert(String source) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(source);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
