package cn.itcast.web.controller.system;

import cn.itcast.domain.system.Dept;
import cn.itcast.domain.system.Role;
import cn.itcast.domain.system.User;
import cn.itcast.service.system.DeptService;
import cn.itcast.service.system.RoleService;
import cn.itcast.service.system.UserService;
import cn.itcast.web.controller.BaseController;
import com.github.pagehelper.PageInfo;
import lombok.extern.log4j.Log4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/user")
@Log4j
public class UserController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private DeptService deptService;

    /**
     * 列表查询
     */
    @RequestMapping("/list")
    @RequiresPermissions("用户管理")
    public String list(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5") Integer pageSize){


        /* 权限校验*/
        //Subject subject = SecurityUtils.getSubject();
        //subject.checkPermission("用户管理");


       Subject subject = SecurityUtils.getSubject();
       subject.checkPermission("用户管理");


        log.info("执行用户列表查询开始....");
        String companyId = getLoginCompanyId();
        PageInfo<User> pageInfo = userService.findByPage(companyId, pageNum, pageSize);
        request.setAttribute("pageInfo",pageInfo);
        return "system/user/user-list";
    }

    /**
     * 进入添加页面
     */
    @RequestMapping("/toAdd")
    public String toAdd(Model model) {
        // 根据企业id查询
        List<User> userList = userService.findAll(getLoginCompanyId());
        model.addAttribute("userList",userList);

        // 查询所有部门，页面下拉列表显示
        List<Dept> deptList = deptService.findAll(getLoginCompanyId());
        model.addAttribute("deptList",deptList);

        return "system/user/user-add";
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;
    /**
     * 添加或修改
     */
    @RequestMapping("/edit")
    public String edit(User user){
        //设置密码加盐加密
        String password = user.getPassword();
        if (password.length() != 32) {
            String email = user.getEmail();
            String encodePwd = new Md5Hash(password, email).toString();
            user.setPassword(encodePwd);
        }
        // 设置企业信息
        user.setCompanyId(getLoginCompanyId());
        user.setCompanyName(getLoginCompanyName());
        if(StringUtils.isEmpty(user.getId())){
            userService.save(user);

            /* 发送消息到消息容器（中指定的队列）*/
            // 发送消息
            Map<String,String> map = new HashMap<>();
            map.put("email",user.getEmail());
            map.put("title","新员工入职提醒");
            map.put("content","欢迎你来到SaasExport大家庭，我们是一个有激情的团队！");
            // 发送消息
            rabbitTemplate.convertAndSend("myExchange","msg.email",map);
        } else {
            userService.update(user);
        }
        // 重定向到列表
        return "redirect:/system/user/list";
    }

    /**
     * 进入修改页面
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id,Model model){
        //1. 根据id查询
        User user = userService.findById(id);
        //2.查询所有部门，页面下拉列表显示
        List<Dept> deptList = deptService.findAll(getLoginCompanyId());
        model.addAttribute("deptList",deptList);

        //3. 保存
        model.addAttribute("user",user);
        model.addAttribute("deptList",deptList);
        return "system/user/user-update";
    }

    /**
     * 删除
     * 返回的数据：{message : 1}  删除成功
     * 返回的数据：{message : 0}  删除失败
     */
    @RequestMapping("/delete")
    @ResponseBody   // 自动把方法返回的结果转json
    public Map<String,Integer> delete(String id) {
        Map<String,Integer> map = new HashMap<>();
        // 调用service删除
        boolean flag = userService.delete(id);
        if (flag) {
            map.put("message",1);
        } else {
            map.put("message",0);
        }
        return map;
    }

    @Autowired
    private RoleService roleService;
    /**
     * 角色分配权限：
     * 功能入口：user-list.jsp点击角色
     * 请求地址：http://localhost:8080/system/user/roleList.do
     * 请求参数：id  用户id
     * 响应地址：user-role.jsp
     */
    @RequestMapping("/roleList")
    public String roleList(String id){
        //1. 查询用户
        User user = userService.findById(id);

        //2. 查询所有角色 (为了页面复选框显示)
        List<Role> roleList = roleService.findAll(getLoginCompanyId());

        //3. 查询用户已经拥有的角色（为了页面复选框选中)
        List<Role> userRoleList = roleService.findUserRoleByUserId(id);
        // 保存用户拥有的所有的角色id
        String userRoleIds = "";// "1,2,3," 包含 遍历的角色id ? 选中 : ''
        if (userRoleList!=null && userRoleList.size()>0){
            for (Role role : userRoleList) {
                userRoleIds += role.getId() + ",";
            }
        }

        //4. 保存
        request.setAttribute("user",user);
        request.setAttribute("roleList",roleList);
        request.setAttribute("userRoleIds",userRoleIds);
        return "system/user/user-role";
    }

    /**
     * 角色分配权限：
     * 功能入口：角色分配权限：在user-role.jsp 点击保存
     * 请求地址：http://localhost:8080/system/user/changeRole
     * 请求参数：
     *      userId   用户id
     *      roleIds  多个角色id
     * 响应地址：重定向到列表
     */
    @RequestMapping("/changeRole")
    public String changeRole(String userId,String[] roleIds){
        userService.changeRole(userId,roleIds);
        return "redirect:/system/user/list";
    }

}
