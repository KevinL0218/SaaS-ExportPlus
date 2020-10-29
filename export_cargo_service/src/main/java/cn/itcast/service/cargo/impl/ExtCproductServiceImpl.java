package cn.itcast.service.cargo.impl;

import cn.itcast.dao.cargo.ContractDao;
import cn.itcast.dao.cargo.ExtCproductDao;
import cn.itcast.domain.cargo.Contract;
import cn.itcast.domain.cargo.ExtCproduct;
import cn.itcast.domain.cargo.ExtCproductExample;
import cn.itcast.service.cargo.ExtCproductService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

// com.alibaba.dubbo.config.annotation.Service
@Service(timeout = 100000)
public class ExtCproductServiceImpl implements ExtCproductService{

    @Autowired
    private ExtCproductDao extCproductDao;
    @Autowired
    private ContractDao contractDao;

    @Override
    public PageInfo<ExtCproduct> findByPage(ExtCproductExample extCproductExample,
                                         int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<ExtCproduct> list = extCproductDao.selectByExample(extCproductExample);
        return new PageInfo<>(list);
    }

    @Override
    public List<ExtCproduct> findAll(ExtCproductExample extCproductExample) {
        return extCproductDao.selectByExample(extCproductExample);
    }

    @Override
    public ExtCproduct findById(String id) {
        return extCproductDao.selectByPrimaryKey(id);
    }

    @Override
    public void save(ExtCproduct extCproduct) {
        // 1、设置id、计算附件金额
        extCproduct.setId(UUID.randomUUID().toString());
        Double amount = 0d;
        if (extCproduct.getCnumber()!= null && extCproduct.getPrice()!=null) {
            amount = extCproduct.getCnumber() * extCproduct.getPrice();
        }
        // 设置附件金额
        extCproduct.setAmount(amount);

        // 2. 修改购销合同
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());
        // 2.1 总金额 = 总金额 + 货物金额
        contract.setTotalAmount(contract.getTotalAmount() + amount);
        // 2.2 附件数量 = 附件数量 + 1
        contract.setExtNum(contract.getExtNum() + 1);
        // 2.3 修改
        contractDao.updateByPrimaryKeySelective(contract);

        // 3. 添加附件
        extCproductDao.insertSelective(extCproduct);
    }

    @Override
    public void update(ExtCproduct extCproduct) {
        //0、计算修改后的附件金额
        Double amount = 0d;
        if (extCproduct.getCnumber()!= null && extCproduct.getPrice()!=null) {
            amount = extCproduct.getCnumber() * extCproduct.getPrice();
        }
        extCproduct.setAmount(amount);

        //1、查询修改前的附件金额
        ExtCproduct extc = extCproductDao.selectByPrimaryKey(extCproduct.getId());
        Double oldAmount = extc.getAmount();

        //2、修改购销合同  总金额 =  总金额 + 修改后 - 修改前
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());
        contract.setTotalAmount(contract.getTotalAmount() + amount - oldAmount);
        contractDao.updateByPrimaryKeySelective(contract);

        //3、修改附件
        extCproductDao.updateByPrimaryKeySelective(extCproduct);
    }

    @Override
    public void delete(String id) {
        // 1. 根据附件id查询
        ExtCproduct extc = extCproductDao.selectByPrimaryKey(id);
        Double amount = extc.getAmount();

        // 2. 修改购销合同
        Contract contract = contractDao.selectByPrimaryKey(extc.getContractId());
        // 总金额 = 总金额 - 附件金额
        contract.setTotalAmount(contract.getTotalAmount() - amount);
        // 附件数量 = 附件数量 - 1
        contract.setExtNum(contract.getExtNum() - 1);
        // 修改
        contractDao.updateByPrimaryKeySelective(contract);

        // 3. 删除附件
        extCproductDao.deleteByPrimaryKey(id);
    }
}
