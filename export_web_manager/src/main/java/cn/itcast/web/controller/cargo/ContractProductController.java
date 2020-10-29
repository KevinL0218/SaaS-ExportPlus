package cn.itcast.web.controller.cargo;

import cn.itcast.domain.cargo.*;
import cn.itcast.service.cargo.ContractProductService;
import cn.itcast.service.cargo.ContractService;
import cn.itcast.service.cargo.FactoryService;
import cn.itcast.web.controller.BaseController;
import cn.itcast.web.util.FileUploadUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import lombok.extern.log4j.Log4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/cargo/contractProduct")
@Log4j
public class ContractProductController extends BaseController {

    // com.alibaba.dubbo.config.annotation.Reference
    @Reference
    private ContractProductService contractProductService;
    @Reference
    private FactoryService factoryService;

    /**
     * 1、货物列表
     * 功能入口：购销合同列表点击货物
     * 请求参数：contractId 表示购销合同id
     */
    @RequestMapping("/list")
    public String list(String contractId,
                       @RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5") Integer pageSize){
        log.info("执行货物列表查询开始....");

        //1.根据购销合同id，查询当前购销合同的货物
        ContractProductExample cpExample = new ContractProductExample();
        // 查询条件：contractId
        cpExample.createCriteria().andContractIdEqualTo(contractId);
        PageInfo<ContractProduct> pageInfo =
                contractProductService.findByPage(cpExample, pageNum, pageSize);

        //2. 查询货物的厂家
        FactoryExample factoryExample = new FactoryExample();
        factoryExample.createCriteria().andCtypeEqualTo("货物");
        List<Factory> factoryList = factoryService.findAll(factoryExample);

        //3. 保存
        request.setAttribute("pageInfo",pageInfo);
        request.setAttribute("factoryList",factoryList);
        // 注意：保存购销合同id，原因：货物添加时候货物表需要存储购销合同id
        // co_contract_product货物表(id, contract_id....)
        request.setAttribute("contractId",contractId);

        return "cargo/product/product-list";
    }

    @Autowired
    private FileUploadUtil fileUploadUtil;

    /**
     * 2、货物添加或修改
     * 功能入口：project-list.jsp 点击保存
     * 文件上传参数： <input type="file"  name="productPhoto" >
     */
    @RequestMapping("/edit")
    public String edit(ContractProduct contractProduct,MultipartFile productPhoto) throws Exception {
        // 设置企业信息
        contractProduct.setCompanyId(getLoginCompanyId());
        contractProduct.setCompanyName(getLoginCompanyName());
        if(StringUtils.isEmpty(contractProduct.getId())){

            /*处理文件上传*/
            if (productPhoto != null) {
                // 处理文件上传
                String fileUrl = "http://"+fileUploadUtil.upload(productPhoto);
                // 文件路径保存到数据库
                contractProduct.setProductImage(fileUrl);
            }
            contractProductService.save(contractProduct);
        } else {
            contractProductService.update(contractProduct);
        }

        // 重定向到列表
        return "redirect:/cargo/contractProduct/list?contractId="+contractProduct.getContractId();
    }


    /**
     * 3、进入修改页面
     * http://localhost:8080/cargo/contractProduct/toUpdate.do?id=4
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id){
        //1. 根据货物id查询
        ContractProduct contractProduct= contractProductService.findById(id);
        request.setAttribute("contractProduct",contractProduct);

        //2. 查询货物厂家
        FactoryExample factoryExample = new FactoryExample();
        factoryExample.createCriteria().andCtypeEqualTo("货物");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("factoryList",factoryList);

        return "cargo/product/product-update";
    }

    /**
     * 4、删除货物
     * 请求地址：http://localhost:8080/cargo/contractProduct/delete.do
     * 请求参数：
     *    id                货物id
     *    contractId        购销合同id
     */
    @RequestMapping("/delete")
    public String delete(String id,String contractId){
        // 根据货物id删除货物
        contractProductService.delete(id);
        // 重定向到列表
        return "redirect:/cargo/contractProduct/list?contractId="+contractId;
    }


    /**
     * 货物上传 (1) 进入上传页面
     */
    @RequestMapping("/toImport")
    public String toImport(String contractId){
        request.setAttribute("contractId",contractId);
        return "cargo/product/product-import";
    }

    /**
     * 货物上传 (2) 再货物上传页面（product-import.jsp）点击保存
     * 参数：
     *      contractId 购销合同id，给某个购销合同添加货物
     *      file       上传的文件
     */
    @RequestMapping("/import")
    public String importExcel(String contractId,MultipartFile file) throws Exception {
        /*ApachePOI解析上传的Excel数据，实现添加货物*/
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        // 获取总行数
        int rowCount = sheet.getPhysicalNumberOfRows();

        // 遍历: 从第二行开始遍历
        Row row = null;
        for (int i = 1; i < rowCount; i++) {
            // 先获取第二行数据...
            row = sheet.getRow(i);

            // 创建货物对象并封装
            ContractProduct cp = new ContractProduct();
            cp.setFactoryName(row.getCell(1).getStringCellValue());
            cp.setProductNo(row.getCell(2).getStringCellValue());
            // 注意：如果excel中内容数据是数值类型，获取数值要用getNumericCellValue()
            cp.setCnumber((int)row.getCell(3).getNumericCellValue());
            cp.setPackingUnit(row.getCell(4).getStringCellValue());
            cp.setLoadingRate(String.valueOf(row.getCell(5).getNumericCellValue()));
            cp.setBoxNum((int) row.getCell(6).getNumericCellValue());
            cp.setPrice(row.getCell(7).getNumericCellValue());
            cp.setProductDesc(row.getCell(8).getStringCellValue());
            cp.setProductRequest(row.getCell(9).getStringCellValue());
            // 注意： 必须要设置购销合同ID  【不要忘记， 查看表...】
            cp.setContractId(contractId);
            // 设置厂家id
            Factory factory = factoryService.findByName(cp.getFactoryName());
            if (factory != null){
                cp.setFactoryId(factory.getId());
            }
            // 设置企业信息
            cp.setCompanyId(getLoginCompanyId());
            cp.setCompanyName(getLoginCompanyName());

            // 添加货物
            contractProductService.save(cp);
        }

        // 购销合同添加货物完成后，跳转到购销合同列表
        return "redirect:/cargo/contract/list";
    }
}
