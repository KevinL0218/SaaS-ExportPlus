package cn.itcast.dao.stat.test;

import cn.itcast.dao.cargo.FactoryDao;
import cn.itcast.dao.stat.StatDao;
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
public class StatDaoTest {

    @Autowired
    private StatDao statDao;

    @Test
    public void findById() {
        //[{name=众鑫, value=489.00}, {name=会龙, value=26201.40}]
        System.out.println(statDao.factorySale("1"));
    }
}
