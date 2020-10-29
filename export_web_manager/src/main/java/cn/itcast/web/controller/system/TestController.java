package cn.itcast.web.controller.system;

import org.apache.http.io.SessionOutputBuffer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
public class TestController  {

    /**
     * 1. http://localhost:8080/aa/bb
     * 2. http://localhost:8080/aa/bb?birth=1998-09-09
     *    出现类型转换异常！
     */
    @RequestMapping("/aa/bb")
    public String list(Date birth){  // import java.util.Date;
        System.out.println("birth = " + birth);
        // 转发
        return "forward:/url.html";
    }
}
