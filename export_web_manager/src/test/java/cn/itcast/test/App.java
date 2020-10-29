package cn.itcast.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext ac =
                new ClassPathXmlApplicationContext("classpath:bean.xml");
    }
}
