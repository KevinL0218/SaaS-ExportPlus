package cn.itcast.web.controller.cargo;

import cn.itcast.domain.cargo.*;
import cn.itcast.service.cargo.ContractProductService;
import cn.itcast.service.cargo.ExtCproductService;
import cn.itcast.service.cargo.FactoryService;
import cn.itcast.web.controller.BaseController;
import cn.itcast.web.util.FileUploadUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/cargo/extCproduct")
@Log4j
public class ExtCproductController extends BaseController {

    // com.alibaba.dubbo.config.annotation.Reference
    @Reference
    private ExtCproductService extCproductService;
    @Reference
    private FactoryService factoryService;

    /**
     * 1、附件列表
     * 功能入口：购销合同列表点击货物后，进入货物列表再点击附件
     * 请求参数：
     * contractId 表示购销合同id  （页面存储，添加附件时候使用到）
     * contractProductId  货物id （页面存储，添加附件时候使用到）
     * 还需要根据货物查询附件
     */
    @RequestMapping("/list")
    public String list(String contractId, String contractProductId,
                       @RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5") Integer pageSize) {
        log.info("执行附件列表查询开始....");

        //1.根据货物id查询附件
        ExtCproductExample cpExample = new ExtCproductExample();
        // 查询条件：contractProductId
        cpExample.createCriteria().andContractProductIdEqualTo(contractProductId);
        PageInfo<ExtCproduct> pageInfo =
                extCproductService.findByPage(cpExample, pageNum, pageSize);

        //2. 查询货物的厂家
        FactoryExample factoryExample = new FactoryExample();
        factoryExample.createCriteria().andCtypeEqualTo("附件");
        List<Factory> factoryList = factoryService.findAll(factoryExample);

        //3. 保存
        request.setAttribute("pageInfo", pageInfo);
        request.setAttribute("factoryList", factoryList);
        request.setAttribute("contractId", contractId);
        request.setAttribute("contractProductId", contractProductId);

        return "cargo/extc/extc-list";
    }

    /**
     * 添加或修改
     */
    @RequestMapping("/edit")
    public String edit(ExtCproduct extCproduct) throws Exception {
        // 设置企业信息
        extCproduct.setCompanyId(getLoginCompanyId());
        extCproduct.setCompanyName(getLoginCompanyName());
        if (StringUtils.isEmpty(extCproduct.getId())) {
            extCproductService.save(extCproduct);
        } else {
            extCproductService.update(extCproduct);
        }
        // 重定向到列表
        return "redirect:/cargo/extCproduct/list?contractId=" + extCproduct.getContractId()
                + "&contractProductId=" + extCproduct.getContractProductId();
    }

    /**
     * 进入修改页面
     * 地址：http://localhost:8080/cargo/extCproduct/toUpdate.do
     * 参数：
     * id                  附件id，根据附件id查询
     * contractId          合同id（后面添加附件使用）
     * contractProductId   货物id（后面添加附件使用）
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id, String contractId, String contractProductId) {
        //1. 根据附件id查询
        ExtCproduct extCproduct = extCproductService.findById(id);

        //2. 查询附件厂家
        FactoryExample factoryExample = new FactoryExample();
        factoryExample.createCriteria().andCtypeEqualTo("附件");
        List<Factory> factoryList = factoryService.findAll(factoryExample);

        //3. 保存
        request.setAttribute("extCproduct", extCproduct);
        request.setAttribute("factoryList", factoryList);
        request.setAttribute("contractId", contractId);
        request.setAttribute("contractProductId", contractProductId);

        return "cargo/extc/extc-update";
    }

    /**
     * 删除附件
     */
    @RequestMapping("/delete")
    public String delete(String id, String contractId, String contractProductId) {
        extCproductService.delete(id);
        return "redirect:/cargo/extCproduct/list?contractId=" + contractId
                + "&contractProductId=" + contractProductId;
    }
}














