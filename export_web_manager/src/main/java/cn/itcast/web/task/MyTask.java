package cn.itcast.web.task;

import cn.itcast.domain.cargo.Contract;
import cn.itcast.domain.cargo.ContractExample;
import cn.itcast.domain.system.User;
import cn.itcast.service.cargo.ContractService;
import cn.itcast.service.system.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 定时执行的任务类
 */
public class MyTask {
    @Reference
    private ContractService contractService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private UserService userService;

    /**
     * 定时执行的任务方法
     */
    public void execute() {
        System.out.println("定时执行发送邮件：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        ContractExample example = new ContractExample();
        if (contractService.findAll(example) == null || contractService.findAll(example).size() == 0) {
            return;
        }
        List<Contract> contractList = contractService.findAll(example);
        String createBy;
        User user;
        String email;
        for (Contract contract : contractList) {
            if (contract.getCreateBy() == null) {
                continue;
            }
            createBy = contract.getCreateBy();
            if (userService.findById(createBy) == null) {
                continue;
            }
            user = userService.findById(createBy);
            email = user.getEmail();
            if (contract.getDeliveryPeriod() != null) {
                Date deliveryPeriod = contract.getDeliveryPeriod();
                long deliveryPeriodTime = deliveryPeriod.getTime();
                long threeDaysAgo = new Date().getTime() - 259200000L;
                if (threeDaysAgo < deliveryPeriodTime) {
                    // 发送消息
                    Map<String, String> map = new HashMap<>();
                    map.put("email", "17806707053@163.com");//先用自己邮箱测试
                    map.put("title", "交货期限临近提醒");
                    map.put("content", "交货期限将至！");
                    // 发送消息
                    rabbitTemplate.convertAndSend("myExchange", "msg.email", map);
                }
            }

        }
    }
}
