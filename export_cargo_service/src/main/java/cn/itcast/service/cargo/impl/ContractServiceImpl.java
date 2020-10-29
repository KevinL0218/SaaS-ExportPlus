package cn.itcast.service.cargo.impl;

import cn.itcast.dao.cargo.ContractDao;
import cn.itcast.domain.cargo.Contract;
import cn.itcast.domain.cargo.ContractExample;
import cn.itcast.service.cargo.ContractService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;

// com.alibaba.dubbo.config.annotation.Service
@Service
public class ContractServiceImpl implements ContractService{

    @Autowired
    private ContractDao contractDao;

    @Override
    public PageInfo<Contract> findByPage(ContractExample contractExample,
                                         int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Contract> list = contractDao.selectByExample(contractExample);
        return new PageInfo<>(list);
    }

    @Override
    public List<Contract> findAll(ContractExample contractExample) {
        return contractDao.selectByExample(contractExample);
    }

    @Override
    public Contract findById(String id) {
        return contractDao.selectByPrimaryKey(id);
    }

    @Override
    public void save(Contract contract) {
        // 设置id
        contract.setId(UUID.randomUUID().toString());
        // 初始化一些数据
        contract.setTotalAmount(0d);//总金额
        contract.setProNum(0);//货物数量
        contract.setExtNum(0);//附件数量
        contract.setState(0);//状态
        contract.setCreateTime(new Date());//数据添加时间
        contractDao.insertSelective(contract);
    }

    @Override
    public void update(Contract contract) {
        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public void delete(String id) {
        contractDao.deleteByPrimaryKey(id);
    }

    @Override
    public PageInfo<Contract> findContractByDeptId(String deptId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Contract> list = contractDao.findContractByDeptId(deptId);
        return new PageInfo<>(list);
    }
}












