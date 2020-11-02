package cn.itcast.service.cargo.impl;

import cn.itcast.dao.cargo.FactoryDao;
import cn.itcast.domain.cargo.Factory;
import cn.itcast.domain.cargo.FactoryExample;
import cn.itcast.service.cargo.FactoryService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class FactoryServiceImpl implements FactoryService {

    @Autowired
    private FactoryDao factoryDao;

    /**
     * 分页查询
     * @param factoryExample
     * @param pageNum
     * @param pageSize
     */
    @Override
    public PageInfo<Factory> findByPage(FactoryExample factoryExample, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Factory> list = factoryDao.selectByExample(factoryExample);
        return new PageInfo<>(list);
    }

    /**
     * 查询所有
     * @param factoryExample
     */
    @Override
    public List<Factory> findAll(FactoryExample factoryExample) {
        return factoryDao.selectByExample(factoryExample);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public Factory findById(String id) {
        return factoryDao.selectByPrimaryKey(id);
    }

    /**
     * 新增
     * @param factory
     */
    @Override
    public void save(Factory factory) {
        factory.setId(UUID.randomUUID().toString());
        factory.setCreateTime(new Date());
        factory.setUpdateTime(new Date());
        factoryDao.insertSelective(factory);
    }

    /**
     * 修改
     * @param factory
     */
    @Override
    public void update(Factory factory) {
        factory.setUpdateTime(new Date());
        factoryDao.updateByPrimaryKeySelective(factory);
    }

    /**
     * 删除部门
     * @param id
     */
    @Override
    public boolean delete(String id) {
        return factoryDao.deleteByPrimaryKey(id) == 1;
    }

}
