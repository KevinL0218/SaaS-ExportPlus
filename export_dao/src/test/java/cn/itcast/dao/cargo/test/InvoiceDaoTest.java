package cn.itcast.dao.cargo.test;

import cn.itcast.dao.cargo.InvoiceDao;
import cn.itcast.domain.cargo.Invoice;
import cn.itcast.domain.cargo.InvoiceExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-dao.xml")
public class InvoiceDaoTest {

    @Autowired
    private InvoiceDao invoiceDao;

    @Test
    public void test() {
        Invoice invoice = invoiceDao.selectByPrimaryKey("1001");
        String scNo = invoice.getScNo();
        System.out.println("scNo = " + scNo);
    }

    @Test
    public void test1() {
        InvoiceExample example = new InvoiceExample();
        example.setOrderByClause("create_time desc");
        example.createCriteria();
        List<Invoice> list = invoiceDao.selectByExample(example);
        System.out.println(list.size());
    }

}