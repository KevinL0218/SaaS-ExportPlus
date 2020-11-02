package cn.itcast.service.stat;

import java.util.List;
import java.util.Map;

public interface StatService {
    /**
     * 需求1：生产厂家销售统计
     * @param companyId 企业id，查询条件
     * @return
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

    /**
     * 需求4：查看地区的厂家数量
     */
    List<Map<String, Object>> address();
}
