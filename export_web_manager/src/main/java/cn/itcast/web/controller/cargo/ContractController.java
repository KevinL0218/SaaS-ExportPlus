package cn.itcast.web.controller.cargo;

import cn.itcast.domain.cargo.Contract;
import cn.itcast.domain.cargo.ContractExample;
import cn.itcast.domain.system.User;
import cn.itcast.service.cargo.ContractService;
import cn.itcast.service.system.UserService;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/cargo/contract")
@Log4j
public class ContractController extends BaseController {

    // com.alibaba.dubbo.config.annotation.Reference
    @Reference
    private ContractService contractService;
    @Autowired
    private UserService userService;

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
        //审单人下拉列表显示（本企业部门管理者，上级部门管理者）
        List<User> userList = userService.findUserByDeptId(getLoginUser().getDeptId(), getLoginCompanyId());
        model.addAttribute("userList", userList);
        return "cargo/contract/contract-add";
    }

    /**
     * 添加或修改
     */
    @RequestMapping("/edit")
    public String edit(Contract contract, String userId) throws Exception {

        // 设置企业信息
        contract.setCompanyId(getLoginCompanyId());
        contract.setCompanyName(getLoginCompanyName());
        if (StringUtils.isEmpty(contract.getId())) {
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
    public String toUpdate(String id, Model model) {
        Contract contract = contractService.findById(id);
        //审单人下拉列表显示（本企业部门管理者，上级部门管理者）
        List<User> userList = userService.findUserByDeptId(getLoginUser().getDeptId(), getLoginCompanyId());
        model.addAttribute("userList", userList);
        model.addAttribute("contract", contract);
        return "cargo/contract/contract-update";
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Boolean delete(String id) {
        Boolean result = contractService.delete(id);
        return result;
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
        request.setAttribute("contract", contract);
        return "cargo/contract/contract-view";
    }

    /**
     * 点击提交按钮，进行判断是否时当前合同的审单人
     */
    @RequestMapping("/submit")
    @ResponseBody
    public Integer submit(String id) {
        //通过id查询合同信息
        Contract contract = contractService.findById(id);
        //定义一个标志
        Integer message = 0;
        //获取合同的状态
        Integer state = contract.getState();
        if (state == 1 || state == 2) {
            //已经提交过
            return message = 1;
        }
        //获取合同的审单人
        String checkBy = contract.getCheckBy();
        //判断当前用户degree是否等于2或3并且userName==checkBy?
        Integer degree = getLoginUser().getDegree();
        String userName = getLoginUser().getUserName();
        if ((degree == 2 || degree == 3) && userName.equals(checkBy)) {
            //管理者，有权提交
            /*提交：修改购销合同状态为1，已提交状态*/
            // 修改值

            contract.setState(1);
            // 修改： 动态sql
            contractService.update(contract);
            message = 2;
            return message;
        }

            return message;
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
