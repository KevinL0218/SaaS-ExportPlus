package cn.itcast.web.controller.system;

import cn.itcast.domain.system.Module;
import cn.itcast.domain.system.Role;
import cn.itcast.service.system.ModuleService;
import cn.itcast.service.system.RoleService;
import cn.itcast.web.controller.BaseController;
import com.github.pagehelper.PageInfo;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/role")
@Log4j
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    /**
     * 列表查询
     */
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5") Integer pageSize){
        log.info("执行角色列表查询开始....");

        String companyId = getLoginCompanyId();
        PageInfo<Role> pageInfo = roleService.findByPage(companyId, pageNum, pageSize);
        request.setAttribute("pageInfo",pageInfo);
        return "system/role/role-list";
    }

    /**
     * 进入添加页面
     */
    @RequestMapping("/toAdd")
    public String toAdd(Model model) {
        return "system/role/role-add";
    }

    /**
     * 添加或修改
     */
    @RequestMapping("/edit")
    public String edit(Role role){
        // 设置企业信息
        role.setCompanyId(getLoginCompanyId());
        role.setCompanyName(getLoginCompanyName());
        if(StringUtils.isEmpty(role.getId())){
            roleService.save(role);
        } else {
            roleService.update(role);
        }
        // 重定向到列表
        return "redirect:/system/role/list";
    }

    /**
     * 进入修改页面
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id,Model model){
        Role role = roleService.findById(id);
        model.addAttribute("role",role);
        return "system/role/role-update";
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public String delete(String id) {
        roleService.delete(id);
        return "redirect:/system/role/list";
    }

    /**
     * 角色分配权限（1）在role-list.jsp列表点击权限，进入角色权限页面
     * http://localhost:8080/system/role/roleModule.do?roleId=4
     */
    @RequestMapping("/roleModule")
    public String roleModule(String roleId) {
        Role role = roleService.findById(roleId);
        request.setAttribute("role",role);
        return "system/role/role-module";
    }

    @Autowired
    private ModuleService moduleService;
    /**
     * 角色分配权限（2）role-module.jsp页面异步请求，返回ztree需要的json数据
     * 返回json数据 result = [
     *     { id:2, pId:0, name:"随意勾选 2", checked:true, open:true}
     * ]
     */
    @RequestMapping("/getZtreeNode")
    @ResponseBody
    public List<Map<String,Object>> getZtreeNode(String roleId) {
        //1.定义返回的集合
        List<Map<String,Object>> result = new ArrayList<>();

        //2. 查询数据： 查询所有的权限
        List<Module> moduleList = moduleService.findAll();

        //3. 查询角色已经拥有的权限
        List<Module> roleModuleList =
                moduleService.findModuleByRoleId(roleId);

        //4. 封装结果
        //4.1 遍历所有权限
        for (Module module : moduleList) {
            //4.2 创建map集合，封装权限数据: module--->map
            Map<String,Object> map = new HashMap<>();
            // 注意：map的key一定要与ztree需要的json格式一致
            map.put("id",module.getId());
            map.put("pId",module.getParentId());
            map.put("name",module.getName());
            map.put("open",true);
            // 设置选中
            if (roleModuleList.contains(module)) {
                map.put("checked",true);
            }
            //4.3 添加到返回的集合
            result.add(map);
        }
        return result;
    }

    /**
     * 角色分配权限（3）role-module.jsp点击保存
     * 请求参数：
     *    roleId    角色id（一个）
     *    moduleIds 权限id（多个，逗号隔开）
     */
    @RequestMapping("/updateRoleModule")
    public String updateRoleModule(String roleId,String moduleIds) {
        moduleService.updateRoleModule(roleId,moduleIds);
        return "redirect:/system/role/list";
    }
}
