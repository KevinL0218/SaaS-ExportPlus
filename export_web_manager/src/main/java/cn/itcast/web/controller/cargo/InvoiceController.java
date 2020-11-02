package cn.itcast.web.controller.cargo;

import cn.itcast.domain.cargo.Invoice;
import cn.itcast.domain.cargo.InvoiceExample;
import cn.itcast.service.cargo.InvoiceService;
import cn.itcast.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Log4j
@RequestMapping("/cargo/invoice")
public class InvoiceController extends BaseController {

    @Reference
    private InvoiceService invoiceService;

    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5") Integer pageSize) {
        log.info("执行发票管理列表查询开始....");

        // 创建查询对象
        InvoiceExample invoiceExample = new InvoiceExample();
        InvoiceExample.Criteria criteria = invoiceExample.createCriteria();

        // 添加查询条件
        criteria.andIdEqualTo("1101");

        PageInfo<Invoice> pageInfo = invoiceService.findByPage(invoiceExample, pageNum, pageSize);

        request.setAttribute("pageInfo", pageInfo);
        return "/cargo/invoice/invoice-list";
    }

    @RequestMapping("/toAdd")
    public String toAdd() {
        return "/cargo/invoice/invoice-add";
    }
}