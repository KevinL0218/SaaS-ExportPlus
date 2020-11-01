import cn.itcast.web.converter.StringToDateConverter;
import io.netty.handler.codec.DateFormatter;
import org.junit.Test;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyTest {

    @Test
    public void test1() {
        Date date = new Date();
        long now = date.getTime();
        long before = now - 259200000L;
        StringToDateConverter converter = new StringToDateConverter();
        Date convertDate = converter.convert("2020-10-31");
        long convertDateTime = convertDate.getTime() + 57600000L;
        System.out.println("convertDateTime = " + convertDateTime);
        System.out.println("now = " + now);
        System.out.println("before = " + before);
        System.out.println("date = " + date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        NumberFormat numberFormat = sdf.getNumberFormat();
        String format = numberFormat.format(1603951317018L);
        System.out.println("numberFormat = " + numberFormat);
        System.out.println("format = " + format);
    }


    @Test
    public void test2() throws ParseException {

        String createDate = "";
        Date date = new Date();//emailTime
        long time = date.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatDate = sdf.format(date);
        if (date != null) {
            if (time < 3600000) {
                createDate = (time / 1000 / 60) + "分钟前";
            } else if (time > 3600000) {
                if (time < 86400000) {
                    createDate = (time / 1000 / 60 / 60) + "小时前";
                } else {
                    createDate = formatDate;
                }
            }
//            out.print(createDate);
        } else {
//            out.print(createDate);
        }
    }
}
