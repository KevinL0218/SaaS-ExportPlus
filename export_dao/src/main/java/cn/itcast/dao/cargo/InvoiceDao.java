package cn.itcast.dao.cargo;

import cn.itcast.domain.cargo.Invoice;
import cn.itcast.domain.cargo.InvoiceExample;

import java.util.List;

public interface InvoiceDao {
    int deleteByPrimaryKey(String id);

    int insert(Invoice record);

    int insertSelective(Invoice record);

    List<Invoice> selectByExample(InvoiceExample example);

    Invoice selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Invoice record);

    int updateByPrimaryKey(Invoice record);
}