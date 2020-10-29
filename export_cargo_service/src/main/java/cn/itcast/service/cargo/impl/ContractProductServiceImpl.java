package cn.itcast.service.cargo.impl;

import cn.itcast.dao.cargo.ContractDao;
import cn.itcast.dao.cargo.ContractProductDao;
import cn.itcast.dao.cargo.ExtCproductDao;
import cn.itcast.domain.cargo.*;
import cn.itcast.service.cargo.ContractProductService;
import cn.itcast.vo.ContractProductVo;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;

// com.alibaba.dubbo.config.annotation.Service
@Service(timeout = 100000)
public class ContractProductServiceImpl implements ContractProductService{

    @Autowired
    private ContractProductDao contractProductDao;
    @Autowired
    private ContractDao contractDao;
    @Autowired
    private ExtCproductDao extCproductDao;

    @Override
    public PageInfo<ContractProduct> findByPage(ContractProductExample contractProductExample,
                                         int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<ContractProduct> list = contractProductDao.selectByExample(contractProductExample);
        return new PageInfo<>(list);
    }

    @Override
    public List<ContractProduct> findAll(ContractProductExample contractProductExample) {
        return contractProductDao.selectByExample(contractProductExample);
    }

    @Override
    public ContractProduct findById(String id) {
        return contractProductDao.selectByPrimaryKey(id);
    }

    // 添加货物
    @Override
    public void save(ContractProduct contractProduct) {
        // 1. 设置id、计算用户输入的货物金额
        contractProduct.setId(UUID.randomUUID().toString());
        Double amount = 0d;
        if (contractProduct.getCnumber()!= null && contractProduct.getPrice()!=null) {
            amount = contractProduct.getCnumber() * contractProduct.getPrice();
        }
        // 设置金额
        contractProduct.setAmount(amount);

        // 2. 修改购销合同
        Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());
        // 2.1 总金额 = 总金额 + 货物金额
        contract.setTotalAmount(contract.getTotalAmount() + amount);
        // 2.2 货物数量 = 货物数量 + 1
        contract.setProNum(contract.getProNum() + 1);
        // 2.3 修改
        contractDao.updateByPrimaryKeySelective(contract);

        // 3. 添加货物
        contractProductDao.insertSelective(contractProduct);
    }

    // 修改货物
    @Override
    public void update(ContractProduct contractProduct) {
        // 1、计算修改后的货物金额
        Double amount = 0d;
        if (contractProduct.getCnumber()!= null && contractProduct.getPrice()!=null) {
            amount = contractProduct.getCnumber() * contractProduct.getPrice();
        }
        // 设置金额
        contractProduct.setAmount(amount);

        // 2、获取修改前的货物金额，查询数据库
        ContractProduct cp = contractProductDao.selectByPrimaryKey(contractProduct.getId());
        Double oldAmount = cp.getAmount();

        // 3、修改购销合同总金额 = 总金额 + 修改后 - 修改前
        Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());
        contract.setTotalAmount(contract.getTotalAmount() + amount - oldAmount);
        contractDao.updateByPrimaryKeySelective(contract);

        // 4、修改货物
        contractProductDao.updateByPrimaryKeySelective(contractProduct);
    }

    @Override
    public void delete(String id) {
        //A. 根据货物id，查询货物
        ContractProduct contractProduct = contractProductDao.selectByPrimaryKey(id);
        Double cpAmount = contractProduct.getAmount();

        //B. 根据货物id，查询附件，累加附件金额、附件数量; 删除附件
        ExtCproductExample extExample = new ExtCproductExample();
        // 查询条件：ContractProductId 货物id
        extExample.createCriteria().andContractProductIdEqualTo(id);
        List<ExtCproduct> extCproductList = extCproductDao.selectByExample(extExample);
        // 保存附件金额
        Double extAmount = 0d;
        if (extCproductList != null && extCproductList.size()>0){
            for (ExtCproduct extCproduct : extCproductList) {
                // 累计附件金额
                extAmount += extCproduct.getAmount();
                // 删除附件
                extCproductDao.deleteByPrimaryKey(extCproduct.getId());
            }
        }

        //C. 修改购销合同
        Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());
        //C1.总金额 = 总金额 - 货物金额 - 附件金额
        contract.setTotalAmount(contract.getTotalAmount() - cpAmount - extAmount);
        //C2.货物数量 = 货物数量 - 1
        contract.setProNum(contract.getProNum()-1);
        //C3.附件数量 = 附件数量 - 当前删除的货物中附件数量
        contract.setExtNum(contract.getExtNum() - extCproductList.size());
        //C4.修改购销合同
        contractDao.updateByPrimaryKeySelective(contract);

        //D. 删除：货物
        contractProductDao.deleteByPrimaryKey(id);
    }

    @Override
    public List<ContractProductVo> findByShipTime(String shipTime) {
        return contractProductDao.findByShipTime(shipTime);
    }
}
