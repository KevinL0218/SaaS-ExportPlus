package cn.itcast.web.controller.cargo;

import cn.itcast.domain.cargo.*;
import cn.itcast.domain.system.User;
import cn.itcast.service.cargo.ContractService;
import cn.itcast.service.cargo.ExportProductService;
import cn.itcast.service.cargo.ExportService;
import cn.itcast.vo.ExportProductVo;
import cn.itcast.vo.ExportResult;
import cn.itcast.vo.ExportVo;
import cn.itcast.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import lombok.extern.log4j.Log4j;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/cargo/export")
@Log4j
public class ExportController extends BaseController {

    @Reference
    private ContractService contractService;
    @Reference
    private ExportService exportService;
    @Reference
    private ExportProductService exportProductService;

    /**
     * 合同管理，显示状态为1的购销合同
     */
    @RequestMapping("/contractList")
    public String list(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5") Integer pageSize){
        log.info("执行合同管理查询开始....");

        // 创建查询对象
        ContractExample example = new ContractExample();
        // 根据创建时间倒序排序..
        example.setOrderByClause("create_time desc");
        ContractExample.Criteria criteria = example.createCriteria();
        // 添加查询条件: companyId
        criteria.andCompanyIdEqualTo(getLoginCompanyId());
        // 【添加查询条件：state=1】
        criteria.andStateEqualTo(1);
        // 分页查询
        PageInfo<Contract> pageInfo =
                contractService.findByPage(example,pageNum,pageSize);
        request.setAttribute("pageInfo",pageInfo);
        // 合同管理列表页面，显示状态为1的购销合同
        return "cargo/export/export-contractList";
    }




    /**
     * 报运单列表分页
     */
    @RequestMapping("/list")
    public String exportList(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5") Integer pageSize){
        log.info("执行合同管理查询开始....");

        // 创建查询对象
        ExportExample example = new ExportExample();
        // 根据创建时间倒序排序..
        example.setOrderByClause("create_time desc");
        ExportExample.Criteria criteria = example.createCriteria();
        // 添加查询条件: companyId
        criteria.andCompanyIdEqualTo(getLoginCompanyId());
        // 分页查询
        PageInfo<Export> pageInfo =
                exportService.findByPage(example,pageNum,pageSize);
        request.setAttribute("pageInfo",pageInfo);
        // 合同管理列表页面，显示状态为1的购销合同
        return "cargo/export/export-list";
    }

    /**
     * 报运单，进入添加页面
     * 请求参数：
     *      id  多个购销合同的id，自动以逗号隔开
     */
    @RequestMapping("/toExport")
    public String toExport(String id){
        request.setAttribute("id",id);
        return "cargo/export/export-toExport";
    }


    /**
     * 报运单添加或修改
     */
    @RequestMapping("/edit")
    public String edit(Export export) throws Exception {
        // 设置企业信息
        export.setCompanyId(getLoginCompanyId());
        export.setCompanyName(getLoginCompanyName());
        if(StringUtils.isEmpty(export.getId())){
            export.setCreateBy(getLoginUser().getId());
            export.setCreateDept(getLoginUser().getDeptId());
            export.setCreateTime(new Date());
            exportService.save(export);
        } else {
            exportService.update(export);
        }
        // 重定向到列表
        return "redirect:/cargo/export/list";
    }

    /**
     * 报运单修改，进入修改页面
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id){
        // 1. 根据报运单的id查询报运单
        Export export = exportService.findById(id);

        // 2. 根据报运单的id查询报运的商品
        ExportProductExample example = new ExportProductExample();
        example.createCriteria().andExportIdEqualTo(id);
        List<ExportProduct> eps = exportProductService.findAll(example);

        // 3. 保存
        request.setAttribute("export",export);
        request.setAttribute("eps",eps);

        // 4. 跳转到修改页面
        return "cargo/export/export-update";
    }

    /**
     * 报运单的提交、取消
     */
    @RequestMapping("/submit")
    public String submit(String id) {
        Export export = new Export();
        export.setId(id);
        // 修改状态：已提交的购销合同才可以电子报运
        export.setState(1);
        exportService.update(export);
        return "redirect:/cargo/export/list";
    }
    @RequestMapping("/cancel")
    public String cancel(String id) {
        Export export = new Export();
        export.setId(id);
        // 修改状态：设置状态为0草稿
        export.setState(0);
        exportService.update(export);
        return "redirect:/cargo/export/list";
    }

    /**
     * 电子报运, 远程访问海关报运平台。
     * jk_export： 这个项目就是我们模拟的海关报运平台，必须要先启动
     *
     * 电子报运接口地址：http://localhost:9001/ws/export/user
     * 查看报运结果地址：http://localhost:9001/ws/export/user/100
     */
    @RequestMapping("/exportE")
    public String exportE(String id){
        // 封装webservice请求的参数：ExportVo
        ExportVo exportVo = new ExportVo();
        // 根据报运单id查询
        Export export = exportService.findById(id);
        // 对象拷贝
        BeanUtils.copyProperties(export,exportVo);
        // 设置报运单id
        exportVo.setExportId(id);

        // 根据报运单id，查询商品
        ExportProductExample example = new ExportProductExample();
        example.createCriteria().andExportIdEqualTo(id);
        List<ExportProduct> productList = exportProductService.findAll(example);
        // 获取exportVo中商品的集合
        List<ExportProductVo> products = exportVo.getProducts();
        // 遍历查询的结果
        if (productList != null && productList.size() > 0){
            for (ExportProduct exportProduct : productList) {
                ExportProductVo vo = new ExportProductVo();
                BeanUtils.copyProperties(exportProduct,vo);
                // 设置报运单id、商品id
                vo.setExportId(id);
                vo.setExportProductId(exportProduct.getId());
                // 添加到集合
                products.add(vo);
            }
        }
        //exportVo.setProducts(products);


        //电子报运(1) 录入报运单数据到海关报运平台的数据库
        WebClient.create("http://localhost:9001/ws/export/user").post(exportVo);
        //电子报运(2) 查询报运的结果
        ExportResult exportResult =
                WebClient.create("http://localhost:9001/ws/export/user/" + id)
                        .get(ExportResult.class);
        // 修改数据库，更新报运单的状态、商品交税金额
        exportService.updatExport(exportResult);
        return "redirect:/cargo/export/list";
    }
}
