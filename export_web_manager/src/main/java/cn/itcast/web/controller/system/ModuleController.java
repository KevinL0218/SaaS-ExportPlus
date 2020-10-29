package cn.itcast.web.controller.system;

import cn.itcast.domain.system.Module;
import cn.itcast.service.system.ModuleService;
import cn.itcast.web.controller.BaseController;
import com.github.pagehelper.PageInfo;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/system/module")
@Log4j
public class ModuleController extends BaseController {

    @Autowired
    private ModuleService moduleService;

    /**
     * 列表查询
     */
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5") Integer pageSize){
        log.info("执行角色列表查询开始....");

        String companyId = getLoginCompanyId();
        PageInfo<Module> pageInfo = moduleService.findByPage(pageNum, pageSize);
        request.setAttribute("pageInfo",pageInfo);
        return "system/module/module-list";
    }

    /**
     * 进入添加页面
     */
    @RequestMapping("/toAdd")
    public String toAdd(Model model) {
        // 查询所有权限，作为页面上级菜单下拉列表显示
        List<Module> moduleList = moduleService.findAll();
        model.addAttribute("menus",moduleList);
        return "system/module/module-add";
    }

    /**
     * 添加或修改
     */
    @RequestMapping("/edit")
    public String edit(Module module){
        if(StringUtils.isEmpty(module.getId())){
            moduleService.save(module);
        } else {
            moduleService.update(module);
        }
        // 重定向到列表
        return "redirect:/system/module/list";
    }

    /**
     * 进入修改页面
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id,Model model){
        Module module = moduleService.findById(id);
        model.addAttribute("module",module);

        // 查询所有权限，作为页面上级菜单下拉列表显示
        List<Module> moduleList = moduleService.findAll();
        model.addAttribute("menus",moduleList);
        return "system/module/module-update";
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public String delete(String id) {
        moduleService.delete(id);
        return "redirect:/system/module/list";
    }

}
