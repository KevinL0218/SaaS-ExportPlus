package cn.itcast.service.company.impl;

import cn.itcast.dao.company.CompanyDao;
import cn.itcast.domain.company.Company;
import cn.itcast.service.company.CompanyService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

// import com.alibaba.dubbo.config.annotation.Service;
@Service
public class CompanyServiceImpl implements CompanyService {

    // 从容器中找CompanyDao类型的对象注入进来
    @Autowired
    private CompanyDao companyDao;

    @Override
    public PageInfo<Company> findByPage(int pageNum, int pageSize) {
        // 开启分页支持，自动对其后的第一条查询进行分页
        // 参数1：当前页； 参数2：页大小
        PageHelper.startPage(pageNum,pageSize);
        List<Company> list = companyDao.findAll();
        // 封装分页参数
        PageInfo<Company> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public List<Company> findAll() {
        return companyDao.findAll();
    }

    @Override
    public void save(Company company) {
        // 设置uuid作为主键
        company.setId(UUID.randomUUID().toString());
        companyDao.save(company);
    }

    @Override
    public void update(Company company) {
        companyDao.update(company);
    }

    @Override
    public Company findById(String id) {
        return companyDao.findById(id);
    }

    @Override
    public void delete(String id) {
        companyDao.delete(id);
    }
}
