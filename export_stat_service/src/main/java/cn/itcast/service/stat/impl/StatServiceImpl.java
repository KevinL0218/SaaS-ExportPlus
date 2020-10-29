package cn.itcast.service.stat.impl;

import cn.itcast.dao.stat.StatDao;
import cn.itcast.service.stat.StatService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

// com.alibaba.dubbo.config.annotation.Service
@Service
public class StatServiceImpl implements StatService {
    @Autowired
    private StatDao statDao;
    @Override
    public List<Map<String, Object>> factorySale(String companyId) {
        return statDao.factorySale(companyId);
    }

    @Override
    public List<Map<String, Object>> productSale(String companyId, int top) {
        return statDao.productSale(companyId,top);
    }

    @Override
    public List<Map<String, Object>> online() {
        return statDao.online();
    }
}
