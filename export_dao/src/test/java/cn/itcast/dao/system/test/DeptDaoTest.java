package cn.itcast.dao.system.test;

import cn.itcast.dao.system.DeptDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-dao.xml")
public class DeptDaoTest {

    @Autowired
    private DeptDao deptDao;

    @Test
    public void findAll() {
        System.out.println(deptDao.findAll("1"));
    }
}
