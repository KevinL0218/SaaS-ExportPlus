package cn.itcast.web.task;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定时执行的任务类
 */
public class MyTask {

    /**
     * 定时执行的任务方法：每5秒执行一次（测试）
     */
    public void execute(){
        /* 业务处理.... */
        // 测试
        System.out.println("定时执行：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
}
