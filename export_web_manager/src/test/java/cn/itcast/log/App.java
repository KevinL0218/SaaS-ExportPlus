package cn.itcast.log;


import cn.itcast.domain.company.Company;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class App {
    // 创建输出的日志类
    private Log logger = LogFactory.getLog(App.class);

    // 日志输出的优先级:debug<info<warn<error
    @Test
    public void test() {
        logger.debug("输出调试信息...");
        logger.info("输出提示信息...");
        logger.warn("输出警告信息...");
        logger.error("输出错误信息...");
    }

    @Test
    public void build() {
//        Company company = new Company();
//        company.setId("");
//        company.setName("");

        Company company = Company.builder().id("100").name("xxx").build();
        System.out.println("company = " + company);
    }
}
