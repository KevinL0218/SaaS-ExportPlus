package cn.itcast.web.controller.company;

import cn.itcast.domain.company.Company;
import cn.itcast.service.company.CompanyService;
import com.alibaba.dubbo.config.annotation.Reference;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/company")
@Log4j
public class CompanyController {

    // 注入dubbo接口代理对象
    // com.alibaba.dubbo.config.annotation.Reference;
    @Reference
    private CompanyService companyService;

    // 自己创建输出日志的工具类
    //private Logger log = LoggerFactory.getLogger(CompanyController.class);

    /**
     * 列表查询
     * 请求路径：http://localhost:8080/company/list
     * 响应路径：/WEB-INF/pages/company/company-list.jsp
     */
    @RequestMapping("/list")
    public String list(Model model){
        try {
            log.info("执行企业列表查询的开始....");
            List<Company> list = companyService.findAll();
            model.addAttribute("list",list);
            log.info("执行企业列表查询的结束....");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("执行企业列表查询出现异常!",e);
        }
        return "company/company-list";
    }

    /**
     * 点击新建，进入添加页面
     *
     * 功能入口：在company-list.jsp点击新建
     * 请求地址：http://localhost:8080/company/toAdd.o
     * 请求参数：无
     * 响应地址：/WEB-INF/pages/company/company-add.jsp
     */
    @RequestMapping("/toAdd")
    public String toAdd(){
        return "company/company-add";
    }

    /**
     * 添加
     * 功能入口：在company-add.jsp点击保存
     * 请求地址：http://localhost:8080/company/edit
     * 请求参数：name,licenseId,city...
     * 响应地址：
     *    去到列表重新查询显示添加的数据；
     *    最好用重定向
     *
     * 小结：添加、修改、删除操作成功后最好用重定向到列表；其他都用转发。
     */
    @RequestMapping("/edit")
    public String edit(Company company){
        // 如何区分添加或修改? 根据主键id，如果有值是修改；没有主键值是添加
        if(StringUtils.isEmpty(company.getId())){
            // 添加
            companyService.save(company);
        } else {
            // 修改
            companyService.update(company);
        }
        // 重定向到列表
        return "redirect:/company/list";
    }

    /**
     * 进入修改页面
     * http://localhost:8080/company/toUpdate.do?id=0
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id,Model model){
        // 根据id查询
        Company company = companyService.findById(id);
        // 存储查询结果
        model.addAttribute("company",company);
        return "company/company-update";
    }

    /**
     * 删除
     * http://localhost:8080/company/delete.do?id=0
     */
    @RequestMapping("/delete")
    public String delete(String id){
        companyService.delete(id);
        // 重定向到列表
        return "redirect:/company/list";
    }

}
