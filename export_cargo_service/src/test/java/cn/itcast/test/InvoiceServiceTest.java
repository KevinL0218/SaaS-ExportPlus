package cn.itcast.test;

import cn.itcast.domain.cargo.Invoice;
import cn.itcast.domain.cargo.InvoiceExample;
import cn.itcast.service.cargo.InvoiceService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring/applicationContext-*.xml")
public class InvoiceServiceTest {

    @Reference
    private InvoiceService invoiceService;

    @Test
    public void test() {
        InvoiceExample example = new InvoiceExample();
        example.createCriteria().andIdEqualTo("1001");
        PageInfo<Invoice> pageInfo = invoiceService.findByPage(example, 1, 5);
        System.out.println("pageInfo = " + pageInfo);
    }
}