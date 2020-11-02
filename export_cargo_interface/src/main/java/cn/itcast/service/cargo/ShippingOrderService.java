package cn.itcast.service.cargo;

import cn.itcast.domain.cargo.FactoryExample;
import cn.itcast.domain.cargo.ShippingOrder;
import cn.itcast.domain.cargo.ShippingOrderExample;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 委托管理模块
 */
public interface ShippingOrderService {
    /**
     * 分页查询
     */
    PageInfo<ShippingOrder> findByPage(
            ShippingOrderExample shippingOrderExample, int pageNum, int pageSize);

    /**
     * 查询所有
     */
    List<ShippingOrder> findAll(ShippingOrderExample shippingOrderExample);

    /**
     * 根据id查询
     */
    ShippingOrder findById(String shippingOrderId);

    /**
     * 新增
     */
    void save(ShippingOrder shippingOrder);


    /**
     * 删除
     */
    Boolean delete(String shippingOrderId);

}
