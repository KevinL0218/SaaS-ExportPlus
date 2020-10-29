package cn.itcast.provider;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class CompanyProvider {
    public static void main(String[] args) throws IOException {
        // 注意：必须要写classpath* ,因为要加载applicationContexgt-dao.xml
        ApplicationContext ac =
                new ClassPathXmlApplicationContext("classpath*:spring/applicationContext-*.xml");
        System.in.read();
    }
}
