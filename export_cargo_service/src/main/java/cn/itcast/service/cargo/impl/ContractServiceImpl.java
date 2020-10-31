package cn.itcast.service.cargo.impl;

import cn.itcast.dao.cargo.ContractDao;
import cn.itcast.dao.cargo.ContractProductDao;
import cn.itcast.dao.cargo.ExtCproductDao;
import cn.itcast.domain.cargo.*;
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
    @Autowired
    private ContractProductDao contractProductDao;
    @Autowired
    ExtCproductDao extCproductDao;

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
    public Boolean delete(String id) {
        //已经提交（state 为1，2）的合同不能删除
        //根据id查询购销合同信息
        Contract contract = contractDao.selectByPrimaryKey(id);
        Integer state = contract.getState();
        if (state==1 || state==2){
            //不能删除
            return false;
        }else {
            //删除其购销合同下所有的货物和附件
            //通过购销合同id 删除货物
            ContractProductExample pExample = new ContractProductExample();
            pExample.createCriteria().andContractIdEqualTo(id);
            List<ContractProduct> contractProducts = contractProductDao.selectByExample(pExample);
            for (ContractProduct product : contractProducts) {
                contractProductDao.deleteByPrimaryKey(product.getId());
            }

            //通过购销合同id 删除对应下的附件
            ExtCproductExample eExample = new ExtCproductExample();
            eExample.createCriteria().andContractIdEqualTo(id);
            List<ExtCproduct> extCProducts = extCproductDao.selectByExample(eExample);
            for (ExtCproduct extCProduct : extCProducts) {
                extCproductDao.deleteByPrimaryKey(extCProduct.getId());
            }
            //删除合同
            contractDao.deleteByPrimaryKey(id);
            return true;
        }
    }

    @Override
    public PageInfo<Contract> findContractByDeptId(String deptId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Contract> list = contractDao.findContractByDeptId(deptId);
        return new PageInfo<>(list);
    }
}












