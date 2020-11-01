package cn.itcast.service.cargo.impl;

import cn.itcast.dao.cargo.ShippingOrderDao;
import cn.itcast.domain.cargo.ShippingOrder;
import cn.itcast.domain.cargo.ShippingOrderExample;
import cn.itcast.service.cargo.ShippingOrderService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class ShippingOrderServiceImpl implements ShippingOrderService {

    @Autowired
    private ShippingOrderDao shippingOrderDao;

    /**
     * 分页查询
     *
     * @param shippingOrderExample
     * @param pageNum
     * @param pageSize
     */
    @Override
    public PageInfo<ShippingOrder> findByPage(ShippingOrderExample shippingOrderExample, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<ShippingOrder> list = shippingOrderDao.selectByExample(shippingOrderExample);
        PageInfo<ShippingOrder> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 查询所有
     *
     * @param shippingOrderExample
     */
    @Override
    public List<ShippingOrder> findAll(ShippingOrderExample shippingOrderExample) {
        return shippingOrderDao.selectByExample(shippingOrderExample);
    }

    /**
     * 根据id查询
     *
     * @param shippingOrderId
     */
    @Override
    public ShippingOrder findById(String shippingOrderId) {
        return shippingOrderDao.selectByPrimaryKey(shippingOrderId);
    }

    /**
     * 新增
     *
     * @param shippingOrder
     */
    @Override
    public void save(ShippingOrder shippingOrder) {
        shippingOrderDao.insert(shippingOrder);
    }

    /**
     * 删除
     *
     * @param shippingOrderId
     */
    @Override
    public void delete(String shippingOrderId) {
        shippingOrderDao.deleteByPrimaryKey(shippingOrderId);
    }
}
