package cn.itcast.service.cargo.impl;

import cn.itcast.dao.cargo.InvoiceDao;
import cn.itcast.domain.cargo.Invoice;
import cn.itcast.domain.cargo.InvoiceExample;
import cn.itcast.service.cargo.InvoiceService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceDao invoiceDao;

    /**
     * 分页查询
     */
    @Override
    public PageInfo<Invoice> findByPage(InvoiceExample invoiceExample, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Invoice> list = invoiceDao.selectByExample(invoiceExample);
        return new PageInfo<>(list);
    }

}