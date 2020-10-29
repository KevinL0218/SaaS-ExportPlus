package cn.itcast.test;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class Person {
    public Person(){
        System.out.println("create user");
    }
}
