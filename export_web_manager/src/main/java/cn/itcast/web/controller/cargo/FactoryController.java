package cn.itcast.web.controller.cargo;

import cn.itcast.domain.cargo.Factory;
import cn.itcast.domain.cargo.FactoryExample;
import cn.itcast.service.cargo.FactoryService;
import cn.itcast.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@Log4j
@RequestMapping("/cargo/factory")
public class FactoryController extends BaseController {

    @Reference
    private FactoryService factoryService;

    /**
     * 1.厂家列表分页
     */
    @RequestMapping("/list")
    public ModelAndView list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "5") Integer pageSize) {
        ModelAndView model = new ModelAndView();
        try {
            log.info("执行厂家列表分页查询开始...");
            // 创建查询对象
            FactoryExample example = new FactoryExample();
            PageInfo<Factory> pageInfo = factoryService.findByPage(example, pageNum, pageSize);
            model.addObject("pageInfo", pageInfo);
            model.setViewName("cargo/factory/factory-list");
            log.info("执行厂家列表分页查询结束...");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("执行厂家列表分页查询出现异常！", e);
        }
        return model;
    }

    /**
     * 2.进入添加厂家页面
     */
    @RequestMapping("toAdd")
    public ModelAndView toAdd() {
        ModelAndView model = new ModelAndView();
        try {
            log.info("执行添加厂家页面开始...");
            model.setViewName("cargo/factory/factory-add");
            log.info("执行添加厂家页面结束...");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("执行添加厂家页面出现异常！", e);
        }
        return model;
    }

    /**
     * 3.厂家的添加或修改
     */
    @RequestMapping("edit")
    public ModelAndView edit(Factory factory) {
        ModelAndView model = new ModelAndView();
        try {
            if (StringUtils.isEmpty(factory.getId())) {
                log.info("执行厂家的添加开始...");
                // id为空就是添加厂家
                factory.setCreateBy(getLoginUser().getId());
                factory.setCreateDept(getLoginUser().getDeptId());
                factoryService.save(factory);
                log.info("执行厂家的添加结束...");
            } else {
                log.info("执行厂家的修改开始...");
                // 否则就是更新厂家
                factory.setUpdateBy(getLoginUser().getId());
                factoryService.update(factory);
                log.info("执行厂家的修改结束...");
            }
            model.setViewName("redirect:/cargo/factory/list");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("执行厂家的添加或修改出现异常！", e);
        }
        return model;
    }

    /**
     * 4.修改厂家信息数据回显
     */
    @RequestMapping("toUpdate")
    public ModelAndView toUpdate(String factoryId) {
        ModelAndView model = new ModelAndView();
        try {
            log.info("执行修改厂家信息开始...");
            Factory factory = factoryService.findById(factoryId);
            model.addObject("factory",factory);
            model.setViewName("/cargo/factory/factory-update");
            log.info("执行修改厂家信息结束...");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("执行修改厂家信息出现异常！", e);
        }
        return model;
    }

    /**
     * 5.删除厂家
     */
    @RequestMapping("delete")
    @ResponseBody
    public Map<String, String> delete(String factoryId) {
        Map<String, String> map = new HashMap<>();
        try {
            log.info("执行删除厂家的开始...");
            boolean flag = factoryService.delete(factoryId);
            if (flag){
                map.put("msg", "删除成功！");
            } else {
                map.put("msg", "删除失败！");
            }
            log.info("执行删除厂家的结束...");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("执行删除厂家的出现异常！", e);
        }
        return map;
    }
}
