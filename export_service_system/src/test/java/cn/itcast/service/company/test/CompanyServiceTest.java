package cn.itcast.service.company.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * classpath   加载当前项目类路径下的配置文件
 * classpath*  加载所有项目类路径下的配置文件(包含当前项目依赖的项目)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring/applicationContext-*.xml")
public class CompanyServiceTest {

//    @Autowired
//    private CompanyService companyService;
//
//    @Test
//    public void findAll() {
//        System.out.println(companyService.findAll());
//    }
//
//    @Test
//    public void findByPage() {
//        PageInfo<Company> pageInfo = companyService.findByPage(1, 2);
//        System.out.println("当前页：" + pageInfo.getPageNum());
//        System.out.println("页大小：" + pageInfo.getPageSize());
//        System.out.println("总页数：" + pageInfo.getPages());
//        System.out.println("总记录数：" + pageInfo.getTotal());
//        System.out.println("下一页：" + pageInfo.getNextPage());
//        System.out.println("当前页数据：" + pageInfo.getList());
//    }
}
