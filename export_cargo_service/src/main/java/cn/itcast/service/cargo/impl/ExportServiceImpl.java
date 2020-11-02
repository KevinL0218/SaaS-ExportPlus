package cn.itcast.service.cargo.impl;

import cn.itcast.dao.cargo.*;
import cn.itcast.domain.cargo.*;
import cn.itcast.service.cargo.ExportService;
import cn.itcast.vo.ExportProductResult;
import cn.itcast.vo.ExportResult;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.*;

// com.alibaba.dubbo.config.annotation.Service
@Service
public class ExportServiceImpl implements ExportService{

    // 注入报运单信息
    @Autowired
    private ExportDao exportDao;
    @Autowired
    private ExportProductDao exportProductDao;
    @Autowired
    private ExtEproductDao extEproductDao;

    // 注入购销合同相关信息
    @Autowired
    private ContractDao contractDao;
    @Autowired
    private ContractProductDao contractProductDao;
    @Autowired
    private ExtCproductDao extCproductDao;

    @Override
    public PageInfo<Export> findByPage(ExportExample exportExample,
                                         int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Export> list = exportDao.selectByExample(exportExample);
        return new PageInfo<>(list);
    }

    @Override
    public List<Export> findAll(ExportExample exportExample) {
        return exportDao.selectByExample(exportExample);
    }

    @Override
    public Export findById(String id) {
        return exportDao.selectByPrimaryKey(id);
    }

    @Override
    public void save(Export export) {
        // 1. 设置报运单主键id (设置合同号、修改购销合同状态、设置货物数量、附件数量)
        export.setId(UUID.randomUUID().toString());
        //设置状态
        export.setState(0);

        // 2. 获取购销合同id，多个购销合同id用逗号隔开的
        String contractIds = export.getContractIds();
        String[] array = contractIds.split(",");
        List<String> idArray = Arrays.asList(array);

        // 2.1 设置报运单的合同号，多个合同号用逗号隔开
        // 2.1.1 根据购销合同id查询获取合同号
        String customerNos = "";
        ContractExample contractExample = new ContractExample();
        contractExample.createCriteria().andIdIn(idArray);
        List<Contract> contractList = contractDao.selectByExample(contractExample);
        for (Contract contract : contractList) {
            // 多个合同号用空格隔开
            customerNos += contract.getContractNo() + " ";

            // 2.2 修改购销合同的和报运单的state=, 已审核
            contract.setState(2);



            contractDao.updateByPrimaryKeySelective(contract);
        }
        // 2.1.2 设置合同号
        export.setCustomerContract(customerNos);


        // 定义map集合，保存货物id、商品id（给商品添加附件时候要用到）
        Map<String,String> map = new HashMap<>();

        // 3. 【往报运单的商品表保存数据: co_export_product】
        // 3.1 根据购销合同id，查询所有货物
        ContractProductExample cpExample = new ContractProductExample();
        cpExample.createCriteria().andContractIdIn(idArray);
        List<ContractProduct> cpList = contractProductDao.selectByExample(cpExample);
        // 3.2 遍历查询到的货物集合，封装商品对象，保存商品信息
        if (cpList != null && cpList.size() > 0){
            for (ContractProduct contractProduct : cpList) {
                // 创建商品对象
                ExportProduct exportProduct = new ExportProduct();
                // 封装商品对象 (contractProduct ----> exportProduct)
                // 导入的包：org.springframework.beans.BeanUtils;
                BeanUtils.copyProperties(contractProduct,exportProduct);

                // 设置商品id （uuid设置）
                exportProduct.setId(UUID.randomUUID().toString());
                // 设置商品关联的报运单id
                exportProduct.setExportId(export.getId());

                // 保存商品对象
                exportProductDao.insertSelective(exportProduct);

                // 保存货物id、商品id
                map.put(contractProduct.getId(),exportProduct.getId());
            }
        }

        // 4. 【往报运单的商品附件表保存数据: co_ext_eproduct】
        // 4.1 根据购销合同id，查询所有附件
        ExtCproductExample extCproductExample = new ExtCproductExample();
        extCproductExample.createCriteria().andContractIdIn(idArray);
        List<ExtCproduct> extCproductList = extCproductDao.selectByExample(extCproductExample);
        // 4.2 遍历查询到的附件集合，封装商品附件对象，保存商品附件信息
        if (extCproductList != null && extCproductList.size() > 0) {
            for (ExtCproduct extCproduct : extCproductList) {
                // 创建商品附件
                ExtEproduct extEproduct = new ExtEproduct();
                // 封装商品附件
                BeanUtils.copyProperties(extCproduct,extEproduct);

                // 设置商品附件id
                extEproduct.setId(UUID.randomUUID().toString());
                // 设置商品附件关联的报运单id
                extEproduct.setExportId(export.getId());

                /*设置商品附件关联的商品id*/
                // 获取货物id
                String contractProductId = extCproduct.getContractProductId();
                // 根据货物id，来获取商品id
                String exportProductId = map.get(contractProductId);
                // 设置商品id
                extEproduct.setExportProductId(exportProductId);

                // 保存商品附件
                extEproductDao.insertSelective(extEproduct);
            }
        }

        // 5. 【往报运单表保存数据 co_export】
        export.setExtNum(extCproductList.size());
        export.setProNum(cpList.size());
        exportDao.insertSelective(export);
    }

    @Override
    public void update(Export export) {
        // 1. 修改报运单
        exportDao.updateByPrimaryKeySelective(export);

        // 2. 修改报运单的商品
        List<ExportProduct> exportProducts = export.getExportProducts();
        if (exportProducts != null && exportProducts.size() > 0) {
            for (ExportProduct exportProduct : exportProducts) {
                exportProductDao.updateByPrimaryKeySelective(exportProduct);
            }
        }
    }

    @Override
    public void delete(String id) {
        exportDao.deleteByPrimaryKey(id);
    }

    @Override
    public void updatExport(ExportResult exportResult) {
        //1. 修改报运单：状态、备注
        Export export = new Export();
        export.setId(exportResult.getExportId());
        export.setRemark(exportResult.getRemark());
        export.setState(exportResult.getState());
        // 修改数据库
        exportDao.updateByPrimaryKeySelective(export);

        //2. 修改商品交税金额
        Set<ExportProductResult> products = exportResult.getProducts();
        if (products != null && products.size() > 0) {
            for (ExportProductResult product : products) {
                // 创建商品对象
                ExportProduct exportProduct = new ExportProduct();
                exportProduct.setId(product.getExportProductId());
                exportProduct.setTax(product.getTax());
                // 修改
                exportProductDao.updateByPrimaryKeySelective(exportProduct);
            }
        }
    }
}
