package cn.itcast.dao.cargo.test;

import cn.itcast.dao.cargo.FactoryDao;
import cn.itcast.dao.company.CompanyDao;
import cn.itcast.domain.cargo.Factory;
import cn.itcast.domain.cargo.FactoryExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-dao.xml")
public class FactoryDaoTest {

    @Autowired
    private FactoryDao factoryDao;

    @Test
    public void findById() {
        Factory factory = factoryDao.selectByPrimaryKey("1");
        System.out.println(factory);
    }

    /**
     * updateByPrimaryKey() 全字段更新
     *
     * update co_factory set ctype = ?, full_name = ?, factory_name = ?, contacts = ?,
     * phone = ?, mobile = ?, fax = ?, address = ?, inspector = ?, remark = ?,
     * order_no = ?, state = ?, create_by = ?, create_dept = ?, create_time = ?,
     * update_by = ?, update_time = ? where id = ?
     */
    @Test
    public void update() {
        Factory factory = new Factory();
        factory.setId("726619a1-777e-47a2-9bed-cb08431c867e");
        factory.setCtype("test 货物...");
        // Column 'create_time' cannot be null
        factory.setCreateTime(new Date());
        // Column 'update_time' cannot be null
        factory.setUpdateTime(new Date());

    }

    /**
     * 有选择的更新：对象属性有值，才更新改字段
     * update co_factory SET ctype = ? where id = ?
     * update co_factory SET ctype = ?, address = ? where id = ?
     */
    @Test
    public void updateSelective() {
        Factory factory = new Factory();
        factory.setId("726619a1-777e-47a2-9bed-cb08431c867e");
        factory.setCtype("test...");
        factory.setAddress("草原倒立");
        factoryDao.updateByPrimaryKeySelective(factory);
    }

    /**
     * 条件查询
     * select * from co_factory WHERE ( factory_name = ? and full_name like ? )
     */
    @Test
    public void findAll() {
        // 1. 构造条件对象
        FactoryExample example = new FactoryExample();
        // 2. 添加条件
        FactoryExample.Criteria criteria = example.createCriteria();
        criteria.andFactoryNameEqualTo("草原三剑客");
        criteria.andFullNameLike("%绿色%");

        List<Factory> factoryList = factoryDao.selectByExample(example);
        System.out.println(factoryList);
    }
}
