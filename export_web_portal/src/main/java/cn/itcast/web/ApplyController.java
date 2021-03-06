package cn.itcast.web;

import cn.itcast.domain.company.Company;
import cn.itcast.service.company.CompanyService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplyController {

    // 注入dubbo服务接口的代理对象
    // com.alibaba.dubbo.config.annotation.Reference
    @Reference(protocol = "")
    private CompanyService companyService;

    @RequestMapping("/apply1")
    public String apply(Company company){
        try {
            // 远程调用企业管理服务; 注意7：dubbo客户端与服务端通讯传递的对象必须要实现可序列化接口
            companyService.save(company);
            return "1";
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }
}











