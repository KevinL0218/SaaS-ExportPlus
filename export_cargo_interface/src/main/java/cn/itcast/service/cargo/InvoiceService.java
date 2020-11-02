package cn.itcast.service.cargo;

import cn.itcast.domain.cargo.Invoice;
import cn.itcast.domain.cargo.InvoiceExample;
import com.github.pagehelper.PageInfo;

/**
 * 发票管理的接口
 */
public interface InvoiceService {

    /**
     * 分页查询
     */
    PageInfo<Invoice> findByPage(InvoiceExample invoiceExample, int pageNum, int pageSize);

    /**
     * 删除
     */
    // void delete(String id);


}
