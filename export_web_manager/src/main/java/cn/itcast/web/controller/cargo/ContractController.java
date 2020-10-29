package cn.itcast.web.controller.cargo;

import cn.itcast.domain.cargo.Contract;
import cn.itcast.domain.cargo.ContractExample;
import cn.itcast.domain.system.User;
import cn.itcast.service.cargo.ContractService;
import cn.itcast.web.controller.BaseController;
import cn.itcast.web.util.FileUploadUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/cargo/contract")
@Log4j
public class ContractController extends BaseController {

    // com.alibaba.dubbo.config.annotation.Reference
    @Reference
    private ContractService contractService;

    /**
     * 列表查询
     */
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5") Integer pageSize){
        log.info("执行购销合同列表查询开始....");

        // 创建查询对象
        ContractExample example = new ContractExample();
        // 根据创建时间倒序排序..
        example.setOrderByClause("create_time desc");
        ContractExample.Criteria criteria = example.createCriteria();
        // 添加查询条件: companyId
        criteria.andCompanyIdEqualTo(getLoginCompanyId());

        PageInfo<Contract> pageInfo = null;
        /**
         * 细粒度权限控制
         */
        // 获取登陆用户
        User user = getLoginUser();
        // 获取用户等级
        Integer degree = user.getDegree();
        // 判断用户等级
        if (degree == 4){
            //1、普通员工登陆，degree=4,只能查看自己创建的购销合同
            //SELECT * FROM co_contract WHERE create_by='登陆用户的id'
            criteria.andCreateByEqualTo(user.getId());
            pageInfo = contractService.findByPage(example, pageNum, pageSize);
        }
        else if (degree == 3){
            //2、部门经理登陆，degree=3,可以查看自己部门下所有员工创建的购销合同
            //SELECT * FROM co_contract WHERE create_dept='部门id(登陆用户)'
            criteria.andCreateDeptEqualTo(user.getDeptId());
            pageInfo = contractService.findByPage(example, pageNum, pageSize);
        }
        else if (degree == 2) {
            // 3. 大部门经理登陆，degree=2，可以查看自己部门及其所有子部门创建的购销合同
            //SELECT * FROM co_contract WHERE FIND_IN_SET(create_dept,getDeptChild('登陆用户的部门id'))
            pageInfo = contractService.findContractByDeptId(user.getDeptId(),pageNum,pageSize);
        }

        request.setAttribute("pageInfo",pageInfo);
        return "cargo/contract/contract-list";
    }

    /**
     * 进入添加页面
     */
    @RequestMapping("/toAdd")
    public String toAdd(Model model) {
        return "cargo/contract/contract-add";
    }

    /**
     * 添加或修改
     */
    @RequestMapping("/edit")
    public String edit(Contract contract) throws Exception {
        // 设置企业信息
        contract.setCompanyId(getLoginCompanyId());
        contract.setCompanyName(getLoginCompanyName());
        if(StringUtils.isEmpty(contract.getId())){
            /*记录创建人、创建人的部门(后期列表数据权限控制使用)*/
            contract.setCreateBy(getLoginUser().getId());
            contract.setCreateDept(getLoginUser().getDeptId());
            contractService.save(contract);
        } else {
            contractService.update(contract);
        }
        // 重定向到列表
        return "redirect:/cargo/contract/list";
    }

    /**
     * 进入修改页面
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id,Model model){
        Contract contract = contractService.findById(id);
        model.addAttribute("contract",contract);
        return "cargo/contract/contract-update";
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public String delete(String id) {
        contractService.delete(id);
        return "redirect:/cargo/contract/list";
    }

    /**
     * 查看、提交、取消
     * http://localhost:8080/cargo/contract/toView.do?id=6
     * http://localhost:8080/cargo/contract/submit.do?id=6
     * http://localhost:8080/cargo/contract/cancel.do?id=6
     */
    @RequestMapping("/toView")
    public String toView(String id) {
        Contract contract = contractService.findById(id);
        request.setAttribute("contract",contract);
        return "cargo/contract/contract-view";
    }
    @RequestMapping("/submit")
    public String submit(String id) {
        /*提交：修改购销合同状态为1，已提交状态*/
        Contract contract = new Contract();
        // 修改条件
        contract.setId(id);
        // 修改值
        contract.setState(1);
        // 修改： 动态sql
        contractService.update(contract);
        // 重定向到列表
        return "redirect:/cargo/contract/list";
    }
    @RequestMapping("/cancel")
    public String cancel(String id) {
        /*提交：修改购销合同状态为0, 草稿*/
        Contract contract = new Contract();
        // 修改条件
        contract.setId(id);
        // 修改值
        contract.setState(0);
        // 修改： 动态sql
        contractService.update(contract);
        // 重定向到列表
        return "redirect:/cargo/contract/list";
    }
}
