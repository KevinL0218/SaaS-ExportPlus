package cn.itcast.dao.stat;

import java.util.List;
import java.util.Map;

public interface StatDao {
    /**
     * 需求1：生产厂家销售统计
     * @param companyId 企业id，查询条件
     * @return 1.mybatis自动把每一行数据封装为map，map的key就是查询的列名称，value就是对应的列值；
     *         2.再把map添加到list集合返回
     */
    List<Map<String,Object>> factorySale(String companyId);

    /**
     * 需求2：产品销售"量"要求按前5名统计
     */
    List<Map<String,Object>> productSale(String companyId,int top);

    /**
     * 需求3：按小时统计访问次数
     */
    List<Map<String,Object>> online();
}
