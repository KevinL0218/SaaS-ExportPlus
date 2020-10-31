import cn.itcast.web.converter.StringToDateConverter;

import java.util.Date;

public class MyTest {
    public static void main(String[] args) {
        Date date = new Date();
        long now = date.getTime();
        long before = now - 259200000L;
        StringToDateConverter converter = new StringToDateConverter();
        Date convertDate = converter.convert("2020-10-31");
        long convertDateTime = convertDate.getTime()+57600000L;
        System.out.println("convertDateTime = " + convertDateTime);
        System.out.println("now = " + now);
        System.out.println("before = " + before);
        System.out.println("date = " + date);
    }
}
