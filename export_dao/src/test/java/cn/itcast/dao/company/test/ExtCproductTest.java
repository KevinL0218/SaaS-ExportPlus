package cn.itcast.dao.company.test;

import cn.itcast.dao.cargo.ExtCproductDao;
import cn.itcast.domain.cargo.ExtCproduct;
import cn.itcast.domain.cargo.ExtCproductExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-dao.xml")
public class ExtCproductTest {

    @Autowired
    private ExtCproductDao extCproductDao;

    @Test
    public void findById() {
        String id = "280a2f9d-ab51-4a49-b02a-53446d038746";
        ExtCproductExample extCproductExample = new ExtCproductExample();
        extCproductExample.createCriteria().andCompanyIdLike(id);
        List<ExtCproduct> extCproductList = extCproductDao.selectByExample(extCproductExample);
        System.out.println("-------------" + extCproductList.size());
    }
}